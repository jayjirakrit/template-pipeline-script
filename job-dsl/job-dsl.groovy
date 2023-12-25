#!/usr/bin/groovy
@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

def pipelineFile=${pipelineFile}
print("Loading config from ${pipelineFile}")
def config = new Yaml().load(("${pipelineFile}" as File).text)

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