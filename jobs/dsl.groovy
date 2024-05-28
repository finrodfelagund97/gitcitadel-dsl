import org.yaml.snakeyaml.Yaml

// Function to read YAML file
def readYamlFile(String filePath) {
    def yaml = new Yaml()
    String fileContents = readFile(filePath)
    def data = yaml.load(fileContents)
    return data
}

def yamlFilePath = 'jobs/dsl.yaml'
def yamlData = readYamlFile(yamlFilePath)

yamlData.each { key, value ->
    println("${key}: ${value}")
}

job('example') {
  steps {
    shell('echo Hello World!')
  }
}