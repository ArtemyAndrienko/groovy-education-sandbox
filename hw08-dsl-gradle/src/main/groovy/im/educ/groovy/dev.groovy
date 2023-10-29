package im.educ.groovy

include "default.groovy"

def postfix = "server"
name = "MyTest ${postfix}"
//name = "MyTest2"

https {
    port = 4443
}


mappings = [
        {
            url = "/"
            active = false
        },
        {
            url = "/login2"
            active = false
        }
]