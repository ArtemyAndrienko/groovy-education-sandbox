package im.educ.groovy.persistence

import im.educ.groovy.model.Task

import java.util.concurrent.atomic.AtomicLong


trait TaskPersistence {
    static HashMap tasks = [:]

    void clearTasks() {
        tasks.clear()
    }
}
