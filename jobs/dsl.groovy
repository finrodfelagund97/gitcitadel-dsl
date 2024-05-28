import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths

// Function to read YAML file
def readYamlFile(String filePath) {
    def yaml = new Yaml()
    def fileContent = new String(Files.readAllBytes(Paths.get(filePath)))
    def data = yaml.load(fileContent)
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