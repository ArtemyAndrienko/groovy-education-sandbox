package im.educ.groovy.model

import groovy.transform.Canonical

import java.time.LocalDateTime

@Canonical
class Event {
    String name
//    String description
    LocalDateTime triggered


    Event(String name, LocalDateTime dat) {
        this.name = name
        this.triggered = dat
    }
}
