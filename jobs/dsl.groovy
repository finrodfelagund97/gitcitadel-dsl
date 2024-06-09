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
            def jobnames = value.tokenize(" ")
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

def addView(_context, _name, _content) {
    println("***** Adding single view: ${_name} *****")
    _context.listView(_name) {
        description(_name) // TODO read description from YAML file
        filterBuildQueue()
        filterExecutors()
        jobs {
            // include all jobs who names end with "_name"
            regex(".*${_name}")
        }
        columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
        }
    }
}

def addNestedView(_context, _name, _content) {
    println("***** Adding nested view: ${_name} *****");
    String __defaultView = "";
    _context.nestedView(_name) {
        views {
            _content.each { key, value ->
                // get name of default view
                if (__defaultView == "")
                {
                    println("***** Default view ${key} for nested view: ${_name} *****");
                    __defaultView = key;

                }

                // single view
                if (value.getClass() == String) {
                    addView(delegate, key, value);
                }
                // nested view
                else {
                    addNestedView(delegate, key, value);
                }
            }
        }
        configure { nview ->
            nview / defaultView(__defaultView); //
        }
    }
}

String yamlFilePath = "jobs/dsl.yaml";
yamlData = readYamlFile(yamlFilePath);

def yamlType = yamlData.getClass();
createJobs(yamlData);


yamlData.each { key, value ->
    if (value.getClass() == String)
        addView(delegate, key, value);
    else
        addNestedView(delegate, key, value);
}
