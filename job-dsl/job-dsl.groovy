def jobName = "sample-job-test"
job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        shell("echo Create Job ${jobName}")
    }
}