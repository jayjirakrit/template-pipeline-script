@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

def config = new Yaml().load(("${pipelineFile}" as File).text)
def jobName = "${config..build.jobName}"
def url = "${config.parameters.url}"
def branch = "${config.parameters.branch}"
job(jobName) {
    scm {
        github("${url}", "${branch}", 'ssh')
    }
    steps {
        shell("echo Credential ${config}")
    }
}