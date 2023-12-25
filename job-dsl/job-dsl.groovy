@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

def jobName = "sample-job-test"
def rawPipeline = readFileFromWorkspace("${pipelineFile}")
def config = new Yaml().load(("${pipelineFile}" as File).text)
job(jobName) {
    scm {
        github('jenkinsci/job-dsl-plugin', 'master', 'ssh')
    }
    steps {
        shell("echo Credential ${rawPipeline}")
        shell("echo Credential ${config.parameters.url}")
    }
}