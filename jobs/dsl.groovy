import groovy.yaml.YamlSlurper

def yaml_script = "jobs/dsl.yaml"
def ys = new YamlSlurper()
yamlData = ys.parseText(new File(yaml_script).text)


yamlData.each { key, value ->
    println("${key}: ${value}")
}


/////////////////////////////////////////////////////////////////////////////
// Creation of all views
/////////////////////////////////////////////////////////////////////////////

Boolean isKeyword(s) {
    return (s ==~ /__(?!seed).*/)
}


def addView(_context, _name, _content) {
    out.println(String.format("***** add view: %s", _name));
    _context.listView(_name) {
        description(_content.get('__description', _name));
        jobs {
            _content.each { k, v ->
                if (! isKeyword(k))
                    name(k);
            }
        }
        columns {
            status();
            weather();
            name();
            lastSuccess();
            lastFailure();
            lastDuration();
            buildButton();
        }
    }
}


def addNestedView(_context, _name, _content) {
    out.println(String.format("***** addNested: %s", _name));
    String dv = '';
    _context.nestedView(_name) {
        dv = '';
        views {
            _content.each { k, v ->
                if (! isKeyword(k)) {
                    if (dv == '')
                        dv = k;
                    if (isNested(v, k) == true)
                        addNestedView(delegate, k, v);
                    else
                        addView(delegate, k, v);
                }
            }
        }

        configure { nview ->
            nview / defaultView(dv); //
            out.println(String.format("***** default view for %s: %s", _name,
                        dv));
        }
    }
}

Boolean isNested(_level, _name) {
    if (isKeyword(_name))
        return false;

    rv = false;
    _level.each { k, v ->
        // is 'v' a view or just a simple job?
        if (! isKeyword(k) && v && v.containsKey('__jobparams')) {
            out.println(String.format("**** %s is nested", _name));
            rv = true;
            return;
        }
    }
    return rv;
}


// add an editable "All view"
listView("All") {
    description("List All (non-autogenerated) jobs");
    jobs {
        // autogenerated jobs (that we want to exclude) are prefixed by a '~'
        regex(/^(?!~).+/)

    }
    columns {
        status();
        weather();
        name();
        lastSuccess();
        lastFailure();
        lastDuration();
        buildButton();
    }
}

yamlData.each { k, v ->
    if (isNested(v, k) == true)
        addNestedView(delegate, k, v);
    else
        addView(delegate, k, v);
}


def addAutogenView(_context, view_name, jobnames) {
    _context.listView(view_name) {
        description(String.format("Autogenerated view %s", view_name));
        jobs {
            jobnames.each { jobname -> name(jobname) }
        }
        columns {
            status();
            weather();
            name();
            lastSuccess();
            lastFailure();
            lastDuration();
            buildButton();
        }
    }
}

autogen_jobs.each {view_name, jobnames ->
    addAutogenView(delegate, view_name, jobnames)
}