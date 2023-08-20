package im.educ.groovy

class Utils {

     static void overrideMethods(){
        ArrayList.metaClass.plus << { Collection b ->
            [delegate, b].transpose().collect { x, y -> x + y }
        }

        ArrayList.metaClass.minus << { Collection b ->
            [delegate, b].transpose().collect { x, y -> x - y }
        }
    }
}
