package im.educ.groovy.todo

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity("todo")
class TodoEntity {

    @Id
    @GeneratedValue
    private Long id
    private String title
    private boolean completed = false

}
