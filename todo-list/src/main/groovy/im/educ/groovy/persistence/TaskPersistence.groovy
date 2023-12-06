package im.educ.groovy.persistence

import im.educ.groovy.model.Task

trait TaskPersistence {
    static HashMap<String, Task> tasks = [:]

    void clearTasks() {
        tasks.clear()
    }
}
