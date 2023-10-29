package im.educ.groovy.model

import java.time.Duration
import java.time.LocalDateTime as ldt
import groovy.transform.Canonical


@Canonical
class Action implements Executable{
    String id
    String name
    String description

    ldt start
    ldt finish
    String taskId


    Action(String name, String description, ldt start, ldt finish, String taskId) {
        this.id = UUID.randomUUID().toString()
        this.name = name
        this.description = description
        this.start = start
        this.finish = finish
        this.taskId = taskId
    }

    @Override
    void execute() {
        ldt currentTime = ldt.now()
        Long between = Duration.between(ldt.now(), start).getSeconds()
        final int waitInterval = 5

        synchronized (start) {
            while (between > waitInterval) {
                start.wait(waitInterval * 1000)
                between -= waitInterval
            }
        }

        if (start.isAfter(currentTime)) {
            println "Событие ${new Event(name, start)} выполнено."
        }
    }


}
