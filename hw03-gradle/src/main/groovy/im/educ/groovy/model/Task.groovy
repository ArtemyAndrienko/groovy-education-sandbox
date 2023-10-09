package im.educ.groovy.model

import groovy.transform.Canonical
import java.time.LocalDateTime

@Canonical
class Task {

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


}


