package im.educ.groovy.model

import groovy.transform.Canonical
import im.educ.groovy.persistence.TaskPersistence
import io.micronaut.data.annotation.MappedEntity
import jakarta.inject.Singleton

import java.time.LocalDate
import java.time.LocalDateTime

@Canonical
class Task implements TaskPersistence{

    String id
    String name
    String description
    List<Action> taskActions

    LocalDateTime start
    LocalDateTime finish


    Task(String name, String description, LocalDateTime start, LocalDateTime finish) {
        this.id = UUID.randomUUID().toString()
        this.name = name
        this.description = description
        this.start = start
        this.finish = finish
        this.taskActions = []
    }


    Task addTask(String name, String description, LocalDateTime start, LocalDateTime finish) {
        //validateTask(task)
        def id = UUID.randomUUID()
        if (!tasks[id]) {
            tasks[id].name = name
            tasks[id].description = description
            tasks[id].start = start
            tasks[id].finish = finish
        }
        return tasks[id]
    }
//
///**/
    void deleteTask(String id) {
        if (tasks[id]) {
            tasks.remove(id)
        } else {
            throw new RuntimeException("Task ${id} отсутствует в списке")
        }
    }
//
     Task findTaskById(String id) {
        if (tasks[id]) {
            return tasks[id].findTaskById(id)
        }
        throw new RuntimeException("Task с ${id} отсутствует в списке")
    }
//
    List<Task> findAll() {
        return tasks
    }
//
//    List<Task> findTasksByDate(LocalDate date) {
//        return tasks.findAll {
//            it.value.start.toLocalDate().isEqual(date)
//        }.collect()
//    }
//
//
//    int countTasksByDate(LocalDate date) {
//        return findTasksByDate(date).size()
//    }
//
//
    static void validateTask(Task task) {
        tasks.each {
            if ((task.start.isAfter(it.value.start) && task.start.isBefore(it.value.finish)) ||
                    (task.finish.isBefore(it.value.finish) && task.finish.isAfter(it.value.start))) {
                throw new RuntimeException("""
                Task ${it.value.id} пересекается по времени с task ${task.id}\n
                task1: ${it.value.toString()}\n
                task2: ${task.toString()}
                """)
            }
        }
    }
}


