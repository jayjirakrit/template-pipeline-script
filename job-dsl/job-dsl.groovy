def jobName = "sample-job-test"
def credential = "${credentials}"

job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        shell("echo Create Job ${jobName}")
        shell("echo Credential ${credential}")
    }
}