package im.educ.groovy.model

import org.codehaus.groovy.control.CompilerConfiguration

import java.lang.reflect.ParameterizedType
import java.nio.file.Paths

class RedefinableConfiguration {
    URI configPath


    void include(String path) {
        URI configUri = Paths.get(path).toAbsolutePath().toUri()
        this.configPath = configUri

        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = DelegatingScript.class.name
        GroovyShell shell = new GroovyShell(this.class.classLoader, new Binding(), compilerConfiguration)

        DelegatingScript script = shell.parse(configUri) //custom config - fullPath
        script.setDelegate(this)
        script.run()
    }


//    void methodMissing(String name, Object args) {
//        MetaProperty metaProperty = getMetaClass().getMetaProperty(name)
//        if (metaProperty) {
//            Closure closure = (Closure) ((Object[]) args)[0]
//            Object value = getProperty(name) == null ?
//                    metaProperty.type.getConstructor().newInstance() :
//                    getProperty(name)
//            closure.delegate = value
//            closure.resolveStrategy = Closure.DELEGATE_FIRST
//            closure()
//
//            setProperty(name, value)
//        } else {
//            throw new Exception("Не найдено поле: " + name)
//        }
//    }


    void postProcess() {
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            Object value = getProperty(metaProperty.getName())
            if (Collection.class.isAssignableFrom(metaProperty.getType()) &&
                    value instanceof Collection) { // составной тип?

                ParameterizedType collectionType = (ParameterizedType) getClass().getDeclaredField(metaProperty.getName()).getGenericType()
                Class itemClass = (Class) collectionType.getActualTypeArguments()[0]

                // работаем только с RedefinableConfiguration
                if (RedefinableConfiguration.class.isAssignableFrom(itemClass)) {

                    Collection collection = (Collection) value
                    Collection newValue = collection.getClass().newInstance()

                    for (Object o : collection) {
                        if (o in Closure) {
                            Object item = itemClass.getConstructor().newInstance()
                            ((RedefinableConfiguration) item).setProperty("configPath", configPath)
                            ((Closure) o).setDelegate(item)
                            ((Closure) o).setResolveStrategy(Closure.DELEGATE_FIRST)
                            ((Closure) o).call()
                            ((RedefinableConfiguration) item).postProcess() // еще раз смотрим является ли коллекцией
                            newValue.add(item)
                        } else {
                            newValue.add(o)
                        }
                    }
                    setProperty(metaProperty.getName(), newValue)
                }
            }
        }
    }


}
