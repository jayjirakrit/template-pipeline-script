pipeline {
    agent any
    stages {
        stage('Prepare Job Definition') {
            steps {
                script {
                    cleanWs()
                    // clone template-parameter.yaml file
                    dir('params') {
                        checkout([$class: 'GitSCM', branches: [[name: "main"]],
                                  userRemoteConfigs: [[url: "${pipeline_parameter_file_git}",
                                  credentialsId: "jenkins-user"]]])
                    }

                    // clone job-dsl-template file
                    dir('job-dsl-templates') {
                        checkout([$class: 'GitSCM', branches: [[name: "main"]],
                                  userRemoteConfigs: [[url: "${pipeline_parameter_file_git}",
                                  credentialsId: "jenkins-user"]]])
                        def pipelineConfigPath = "${WORKSPACE}/params/${pipeline_parameter_file_path}"
                        def pipelineConfig = readYaml file: "${pipelineConfigPath}"
                        echo "${pipelineConfig}"
                        def jenkinsJobTemplatePath = "${WORKSPACE}/job-dsl-templates/${pipelineConfig.parameters.build.scriptPath}" as java.lang.Object
                        sh "cat \"${pipelineConfigPath}\" > template.yaml"
                        sh "cat \"${jenkinsJobTemplatePath}\" > jobDsl.groovy"
                        stash includes: 'template.yaml', name: 'template-params'
                        stash includes: 'jobDsl.groovy', name: 'job-dsl'
                    }
                }
            }
        }
        stage('Apply Job Definition') {
            steps {
                script {
                    echo "Start apply job .."
                    unstash 'template-params'
                    unstash 'job-dsl'
                    // Read Yaml file
                    def pipelineConfigPath = "${WORKSPACE}/template.yaml"
                    // Run Job Dsl
                    sh "ls"
                    jobDsl targets: './jobDsl.groovy',
                            removedJobAction: 'DELETE',
                            removedViewAction: 'DELETE',
                            lookupStrategy: 'SEED_JOB',
                            additionalParameters: [pipelineFile: "${pipelineConfigPath}", credentials: 'SECRET']
                }
            }
        }
    }
}