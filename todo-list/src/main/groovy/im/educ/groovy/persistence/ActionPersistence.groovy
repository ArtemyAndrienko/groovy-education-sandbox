package im.educ.groovy.persistence

trait ActionPersistence {
    static def actions = [:]

    void clearActions() {
        actions.clear()
    }
}

