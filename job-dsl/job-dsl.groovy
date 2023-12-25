def jobName = "sample-job-test"
job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        maven("test -Dproject.name=${project}/${branchName}")
    }
}