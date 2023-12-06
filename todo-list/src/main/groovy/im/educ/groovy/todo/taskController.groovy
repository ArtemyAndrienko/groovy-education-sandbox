package im.educ.groovy.todo

import im.educ.groovy.model.Task
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.LocalDateTime

@Controller("/task")
class taskController {

    private static final Logger LOG = LoggerFactory.getLogger(taskController.class)

    @Inject
    private final HashMap<String, Task> tasks = new HashMap<String, Task>(Map.ofEntries(
            Map.entry(UUID.randomUUID().toString(), new Task("task1", "desc1", LocalDateTime.now(), LocalDateTime.now().plusMinutes(25))),
            Map.entry(UUID.randomUUID().toString(), new Task("task2", "desc2", LocalDateTime.now(), LocalDateTime.now().plusMinutes(35))),
            Map.entry(UUID.randomUUID().toString(), new Task("task3", "desc3", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45))),
            Map.entry(UUID.randomUUID().toString(), new Task("task4", "desc4", LocalDateTime.now(), LocalDateTime.now().plusMinutes(55)))
            ))


    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    List<Task> findAll() {
        return this.tasks.findAll()
    }
//
    @Get("/{id}")
    Task findById(@PathVariable String id) {
        return this.tasks.findTaskById(id)
    }
////
////    @Put
////    void update(@Body TodoEntity entity) {
////        todoRepository.update(entity)
////    }
////
    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    Task create(@Body Task task) {
        LOG.info("msg: ${task}")
        return task.addTask(task)
    }
////
    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    void delete(String id) {
        this.tasks.remove(id)
    }
}
