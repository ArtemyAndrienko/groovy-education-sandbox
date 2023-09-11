package im.educ.groovy

import im.educ.groovy.model.Action

interface ActionService {
    Action createService(Action action)
    void deleteTaskById(UUID id)

}
