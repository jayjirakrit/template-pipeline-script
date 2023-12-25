@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

def pipeline=${pipelineFile}
print("Loading config from ${pipeline}")
def config = new Yaml().load(("${pipeline}" as File).text)

pipelineJob(${config.parameters.jobName}) {
    definition {
            scm {
                git {
                    remote {
                        url(${config.parameters.url})
                    }
                    branch("*/${config.parameters.branch}")
                }
            }
            lightweight()
    }
}