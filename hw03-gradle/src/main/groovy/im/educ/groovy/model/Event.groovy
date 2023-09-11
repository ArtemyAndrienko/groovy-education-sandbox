package im.educ.groovy.model

import java.time.LocalDateTime

class Event {
    UUID id
    UUID taskId
    String name
    String description
    LocalDateTime start
    LocalDateTime finish
}
