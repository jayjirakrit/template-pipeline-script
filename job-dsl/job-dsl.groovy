import groovy.yaml.YamlSlurper

def jobName = "sample-job-test"
def rawPipeline = readFileFromWorkspace("${pipelineFile}")
def config = new YamlSlurper().parseText(rawPipeline)

job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        shell("echo Credential ${rawPipeline}")
        shell("echo Credential ${config.parameters.url}")
    }
}