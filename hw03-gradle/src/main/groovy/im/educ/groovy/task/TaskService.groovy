package im.educ.groovy.task

import im.educ.groovy.model.Task
import java.time.LocalDate

interface TaskService {
    Task addTask(Task task)
    void deleteTask(String id)
    Task findTask(String id)
    List<Task> findTasksByDate(LocalDate date)
    int countTasksByDate(LocalDate date)
}