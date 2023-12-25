def jobName = "sample-job-test"
def project = "${credentials}"
def branchName = "${credentials}"
job(jobName) {
    scm {
        git("git://github.com/${project}.git", ${branchName})
    }
    steps {
        maven("test -Dproject.name=${project}/${branchName}")
    }
}