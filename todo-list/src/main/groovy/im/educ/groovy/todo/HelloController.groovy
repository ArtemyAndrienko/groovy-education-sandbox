package im.educ.groovy.todo

import im.educ.groovy.model.Hello
import im.educ.groovy.service.HelloService

import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import jakarta.inject.Inject


@Controller("/todos")
class HelloController {

    private final HelloService helloService

    @Inject
    HelloController(HelloService helloService) {
        this.helloService = helloService
    }

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
//    List<Hello> listTodos() {
//        return this.helloService.findAll()
//    }
    String getMsg()
    {
        return "yes"
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    Hello addMsg(String msg) {
        return this.helloService.addMsg(msg)
    }
//
//    @Put("/{id}")
//    public Maybe<Todo> complete(@Nullable Principal principal, @Parameter Long id) {
//        return this.todoService.complete(id);
//    }
//
//    @Get(uri = "/watch")
//    public Flowable<Event<TodoEvent>> watch(@Nullable Principal principal) {
//        return Flowable.generate(eventEmitter -> eventEmitter.onNext(Event.of(todoService.getEvents(principal.getName()).take())));
//    }

}