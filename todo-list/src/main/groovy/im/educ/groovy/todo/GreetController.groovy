package im.educ.groovy.todo

import groovyjarjarantlr4.v4.runtime.dfa.SingletonEdgeMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

import java.net.http.HttpRequest


@Controller("/sample")
class GreetController {

    MyImportantService service

    @Get("/greet/{name}")
        def greet(HttpRequest request, @PathVariable("name") String name){
            def greet = service.constructorGreet(name)
            return greet as String
        }

}

@Singleton
class MyImportantService {

    def constructorGreet(String name){
        return "Hello ${name}"
    }
}