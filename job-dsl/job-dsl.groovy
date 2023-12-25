def jobName = "sample-job-test"
job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', null)
    }
    steps {
        maven("test -Dproject.name=${project}/${branchName}")
    }
}