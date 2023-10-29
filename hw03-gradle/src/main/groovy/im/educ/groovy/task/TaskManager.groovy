package im.educ.groovy.task

import im.educ.groovy.action.ActionService
import im.educ.groovy.model.Action
import im.educ.groovy.model.Task
import im.educ.groovy.persistence.ActionPersistence
import im.educ.groovy.persistence.TaskPersistence
import java.time.LocalDate
import java.time.LocalDateTime as ldt
import groovy.transform.Canonical

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@Canonical
class TaskManager implements TaskService, ActionService, TaskPersistence, ActionPersistence {

/**/
    @Override
    Task addTask(Task task) {
        validateTask(task)
        if (!tasks[task.id]) {
            tasks[task.id] = task
        }
        return task
    }

/**/
    @Override
    void deleteTask(String id) {
        if (tasks[id]) {
            tasks.remove(id)
        } else {
            throw new RuntimeException("Task ${task.toString()} отсутствует в списке")
        }
    }

    @Override
    Task findTask(String id) {
        if (tasks[id]) {
            return tasks[id]
        }
        throw new RuntimeException("Task с ${id} отсутствует в списке")
    }

    @Override
    List<Task> findTasksByDate(LocalDate date) {
        return tasks.findAll {
            it.value.start.toLocalDate().isEqual(date)
        }.collect()
    }

    @Override
    int countTasksByDate(LocalDate date) {
        return findTasksByDate(date).size()
    }


    @Override
    Action addAction(Task task, Action action) {
        validateAction(action)
        if (!actions[action.id]) {
            actions[action.id] = action
        }
        task.taskActions.with {
            it.add(action)
        }
        return action
    }

    @Override
    void deleteAction(Task task, String id) {
        if (actions[id]) {
            Action action = actions[id]
            task.taskActions.with {
                it.remove(action)
            }
            actions.remove(id)
        } else {
            throw new RuntimeException("Action с ${id} отсутствует в списке")
        }
    }

    @Override
    Action findActionById(String id) {
        if (actions[id]) {
            return actions[id]
        } else {
            throw new RuntimeException("Action с ${id} отсутствует в списке")
        }
    }

    @Override
    List<Action> findActionsByDate(LocalDate date) {
        return actions.findAll {
            it.value.start.toLocalDate().isEqual(date)
        }.collect()
    }

    @Override
    int countActionsByDate(LocalDate date) {
        return findActionsByDate(date).size()
    }

/**/
    static void validateTask(Task task) {
        tasks.each {
            if ((task.start.isAfter(it.value.start) && task.start.isBefore(it.value.finish)) ||
                    (task.finish.isBefore(it.value.finish) && task.finish.isAfter(it.value.start))) {
                throw new RuntimeException("""
                Task ${it.value.id} пересекается по времени с task ${task.id}\n
                task1: ${it.value.toString()}\n
                task2: ${task.toString()}
                """)
            }
        }
    }


    static Boolean dateBetween(ldt date1, ldt date2, ldt toCheck) {
        return toCheck.isAfter(date1) && toCheck.isBefore(date2)
    }

    static void validateAction(Action action) {
        Task task = tasks[action.taskId]
        actions.each { iter ->
            Boolean leftIntersection = dateBetween(iter.value.start, iter.value.finish, action.start)
            Boolean rightIntersection = dateBetween(iter.value.start, iter.value.finish, action.finish)

            if (rightIntersection || leftIntersection) {
                throw new RuntimeException("""
                Action(start=$iter.value.start,finish=$iter.value.finish) и Action(start=$action.start,finish=$action.finish) не должны пересекаться по времени.
                """)
            }
        }

        if (action.start > action.finish) {
            throw new RuntimeException("""
            Action(start=$task.start, finish=$task.finish) время start не может быть меньше времени finish
            """)
        }

        if (task.start.isAfter(action.start) || task.finish.isBefore(action.finish)) {
            throw new RuntimeException("""
            Action(start=$task.start, finish=$task.finish) должен попадать в интервал Task start=$action.start, end=$task.finish)
            """)
        }
    }


}
