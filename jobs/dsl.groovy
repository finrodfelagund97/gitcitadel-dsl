import org.yaml.snakeyaml.Yaml

// Function to read YAML file
def readYamlFile(String filePath) {
    def yaml = new Yaml()
    String fileContents = readFileFromWorkspace(filePath)
    def data = yaml.load(fileContents)
    return data
}

def createJobs(yamlData) {
    yamlData.each { key, value ->
        if (value.getClass() == String) {
            //  this value of type String represents job names separated by whitespace
            def jobnames = value.tokenize(' ')
            for (jobname in jobnames) {
                println("inside createJobs function: creating ${jobname} job")
                job(jobname) {
                    steps {
                        shell("exec jobs/scripts/${jobname}/build.sh")
                    }
                }
            }
        }
        else if (value.getClass() == yamlData.getClass()) {
            createJobs(value)
        }
    }
}

String yamlFilePath = 'jobs/dsl.yaml'
yamlData = readYamlFile(yamlFilePath)

def yamlType = yamlData.getClass()
createJobs(yamlData)
