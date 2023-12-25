import groovy.util.ConfigSlurper

def jobName = "sample-job-test"
def credential = "${credentials}"
def pipelinePath = readFileFromWorkspace("${pipelineFile}")
def configSlurper = new ConfigSlurper().parse(new File(${pipelineFile}).text)

job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        shell("echo Credential ${pipelinePath}")
        shell("echo Credential ${configSlurper.parameters.url}")
    }
}