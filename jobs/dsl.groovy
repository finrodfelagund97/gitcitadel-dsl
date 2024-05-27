import org.yaml.snakeyaml.Yaml
import groovy.json.JsonBuilder

// recover env instead of calling variables as in shell, causing issues if variable is absent
build_env = binding.variables

// DSL_TEST is set on the test server to disable jobs
sandboxing = build_env.get("DSL_TEST", false).toBoolean()

// Parse yaml file with configuration
def yaml = new Yaml()
// Recover script name passed in environment (like jenkins parameter) or default to woody.yaml
def yaml_script = "jobs/dsl.yaml"
yamlData = yaml.load(readFileFromWorkspace(yaml_script))

println "${yamlData}"