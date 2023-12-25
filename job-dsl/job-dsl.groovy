pipelineJob('sample-job-test') {
    definition {
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