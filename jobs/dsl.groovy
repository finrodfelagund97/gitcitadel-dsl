import groovy.yaml.YamlSlurper

def yaml_script = "jobs/dsl.yaml"
def ys = new YamlSlurper()
yamlData = ys.parseText(new File(yaml_script).text)


yamlData.each { key, value ->
    println("${key}: ${value}")
}
