def jobName=${parameters.job}
def url=${parameters.url}
def branch=${parameters.branch}

pipelineJob(${jobName}) {
    definition {
            scm {c
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