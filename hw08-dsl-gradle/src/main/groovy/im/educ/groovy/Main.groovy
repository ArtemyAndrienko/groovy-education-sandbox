package im.educ.groovy

import im.educ.groovy.model.WebServerConfig
import org.codehaus.groovy.control.CompilerConfiguration

static void main(String[] args) {
    def compilerConfiguration = new CompilerConfiguration()
    compilerConfiguration.scriptBaseClass = DelegatingScript.class.name
    GroovyShell shell = new GroovyShell(this.class.classLoader, new Binding(), compilerConfiguration)

    DelegatingScript script = shell.parse(new File("dev.groovy"))
    WebServerConfig config = new WebServerConfig()
    script.setDelegate(config)

    script.run()
    config.postProcess()
    println(config.toString())
}