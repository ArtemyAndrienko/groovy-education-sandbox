package im.educ.groovy.persistence

import java.util.concurrent.atomic.AtomicLong


trait ActionPersistence {
    static def actions = [:]

    void clearActions() {
        actions.clear()
    }
}

