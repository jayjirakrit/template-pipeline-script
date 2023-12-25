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
                                  userRemoteConfigs: [[url          : "https://github.com/jayjirakrit/JENKINS_DEVOPS.git",
                                                       credentialsId: "gihub-key"]]])
                    }

                    // clone job-dsl-template file
                    dir('job-dsl-templates') {
                        checkout([$class           : 'GitSCM', branches: [[name: "main"]],
                                  userRemoteConfigs: [[url          : "https://github.com/jayjirakrit/JENKINS_DEVOPS.git",
                                                       credentialsId: "gihub-key"]]])
                        pipelineConfig = readYaml file: "${WORKSPACE}/pipeline-parameter/pipeline-parameter.yaml"
                        echo "${pipelineConfig}"
                        jenkinsJobTemplate = "${WORKSPACE}/job-dsl/job-dsl.groovy"
                        sh "cat \"${WORKSPACE}/pipeline-parameter/pipeline-parameter.yaml\" > pipeline.yaml"
                        sh "cat \"${jenkinsJobTemplate}\" > job-dsl.groovy"
                        stash includes: 'pipeline.yaml', name: 'pipeline-params'
                        stash includes: 'job-dsl.groovy', name: 'job-dsl'
                    }
                }
            }
        }
        stage('Apply Job Definition') {
            steps {
                script {
                    unstash 'pipeline-params'
                    unstash 'job-dsl'
                    // Read Yaml file
                    pipelineConfig = readYaml file: "${WORKSPACE}/pipeline.yaml"
                    // Run Job Dsl
                    jobDsl targets: ['job-dsl.groovy'],
                           removedJobAction: 'DELETE',
                           removedViewAction: 'DELETE',
                           lookupStrategy: 'SEED_JOB',
                           additionalParameters: ${pipelineConfig}
                }
            }
        }
    }
}