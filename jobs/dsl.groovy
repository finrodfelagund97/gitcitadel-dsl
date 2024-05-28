import org.yaml.snakeyaml.Yaml

// Function to read YAML file
def readYamlFile(String filePath) {
    def yaml = new Yaml()
    String fileContents = readFileFromWorkspace(filePath)
    def data = yaml.load(fileContents)
    return data
}

String yamlFilePath = 'jobs/dsl.yaml'
yamlData = readYamlFile(yamlFilePath)

yamlData.each { key, value ->
    println("${key}: ${value}")
}

job('example') {
  steps {
    shell('echo Hello World!')
  }
}