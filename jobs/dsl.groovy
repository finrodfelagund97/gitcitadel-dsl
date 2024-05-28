import utilities.configuration.ReadYaml

def yaml_script = "jobs/dsl.yaml"
ReadYaml readYaml = new ReadYaml()
def projectConfigList = readYaml.readJenkinsYaml(yaml_script)


projectConfigList.each { key, value ->
    println("${key}: ${value}")
}