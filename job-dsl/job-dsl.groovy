pipelineJob('sample-job-test') {
    definition {
        def config = readYaml file: "${pipelineFile}"
            scm {
                git {
                    remote {
                        url(${config.parameters.url})
                    }
                    branch("*/${config.parameters.branch}")
                }
            }
            lightweight()
    }
}