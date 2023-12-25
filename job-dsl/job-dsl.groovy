def jobName = "sample-job-test"
def credential = "${credentials}"
def pipelinePath = readFileFromWorkspace("${pipelineFile}")
job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        shell("echo Credential ${pipelinePath}")
    }
}