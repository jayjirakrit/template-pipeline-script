def jobName=${parameters.job}
def url=${parameters.url}
def branch=${parameters.branch}

pipelineJob(${jobName}) {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(${url})
                    }
                    branch("*/${branch}")
                }
            }
            lightweight()
        }
    }
}