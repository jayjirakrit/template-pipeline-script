pipeline {
    agent any
    stages {
        stage('Prepare Job Definition') {
            steps {
                script {
                    cleanWs()
                    // clone pipeline-parameter.yaml file
                    dir('params') {
                        checkout([$class           : 'GitSCM', branches: [[name: "main"]],
                                  userRemoteConfigs: [[url          : "git@github.com:jayjirakrit/JENKINS_DEVOPS.git",
                                                       credentialsId: "jenkins-git-key"]]])
                    }

                    // clone job-dsl-template file
                    dir('job-dsl-templates') {
                        checkout([$class           : 'GitSCM', branches: [[name: "main"]],
                                  userRemoteConfigs: [[url          : "git@github.com:jayjirakrit/JENKINS_DEVOPS.git",
                                                       credentialsId: "jenkins-git-key"]]])
                        def pipelineConfigPath = "${WORKSPACE}/params/pipeline-parameter/pipeline-parameter.yaml"
                        def pipelineConfig = readYaml file: "${pipelineConfigPath}"
                        echo "${pipelineConfig}"
                        def jenkinsJobTemplatePath = "${WORKSPACE}/job-dsl-templates/job-dsl/job-dsl.groovy" as java.lang.Object
                        sh "cat \"${pipelineConfigPath}\" > pipeline.yaml"
                        sh "cat \"${jenkinsJobTemplatePath}\" > jobDsl.groovy"
                        stash includes: 'pipeline.yaml', name: 'pipeline-params'
                        stash includes: 'jobDsl.groovy', name: 'job-dsl'
                    }
                }
            }
        }
        stage('Apply Job Definition') {
            steps {
                script {
                    echo "Start apply job .."
                    unstash 'pipeline-params'
                    unstash 'job-dsl'
                    // Read Yaml file
                    def pipelineConfigPath = "${WORKSPACE}/pipeline.yaml"
                    // Run Job Dsl
                    def file = sh "ls"
                    echo "${file}"
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