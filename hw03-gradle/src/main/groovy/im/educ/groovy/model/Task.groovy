package im.educ.groovy.model

import groovy.transform.Canonical
import java.time.LocalDateTime

@Canonical
class Task {
    LocalDateTime createdAt
    String notificationMessage
}


