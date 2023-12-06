package im.educ.groovy.model

import groovy.transform.Canonical
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import jakarta.inject.Singleton
import jakarta.persistence.Entity
import jakarta.persistence.GenerationType


@Canonical
class Hello {


    String msg
}
