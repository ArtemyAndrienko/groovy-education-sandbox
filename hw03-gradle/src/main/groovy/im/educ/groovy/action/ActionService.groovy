package im.educ.groovy.action

import im.educ.groovy.model.Action
import im.educ.groovy.model.Task

import java.time.LocalDate

interface ActionService {
    Action addAction(Task task, Action action)
    void deleteAction(Task task, String id)
    Action findActionById(String id)
    List<Action> findActionsByDate(LocalDate date)
    int countActionsByDate(LocalDate date)
}