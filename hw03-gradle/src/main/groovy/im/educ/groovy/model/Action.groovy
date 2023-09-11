package im.educ.groovy.model

import groovy.transform.Canonical
import im.educ.groovy.action.ActionExecutable

import java.time.LocalDateTime

@Canonical
class Action implements ActionExecutable {
    UUID id
    String name
    String description
    List<Action> actions
    LocalDateTime start
    LocalDateTime finish

    void execute(){
        println "action"
    }
}
