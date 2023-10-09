package im.educ.groovy

import im.educ.groovy.model.Action
import im.educ.groovy.model.Task
import im.educ.groovy.task.TaskManager

import java.time.LocalDate
import java.time.LocalDateTime as ldt


static void main(String[] args) {

    TaskManager workout = new TaskManager()
    Task task = workout.addTask(
            new Task("Комплекс упражнений СБУ",
                    "Комплекс специальных беговых упражнений поможет исправить неточные движения или технику бега",
                    ldt.now(),
                    ldt.now().plusMinutes(30)
            )
    )

    List<Action> actionList = []

    Action action = new Action("Подскоки с постановкой шага",
            "Упражнения Комплекс упражнений СБУ",
            ldt.now(), ldt.now().plusMinutes(1),
            task.id)
    actionList << workout.addAction(task, action)

    Action action1 = new Action("Бег с высоким подниманием бедра",
            "Упражнения Комплекс упражнений СБУ",
            ldt.now().plusMinutes(1), ldt.now().plusMinutes(2),
            task.id)
    actionList << workout.addAction(task, action1)

    Action action2 = new Action("Приставные шаги",
            "Упражнения Комплекс упражнений СБУ",
            ldt.now().plusMinutes(2), ldt.now().plusMinutes(3),
            task.id)
    actionList << workout.addAction(task, action2)

    println "${'>' * 10}Задачи${'>' * 40}"
    workout.tasks.each { entry ->
        println "$entry.key = $entry.value"
    }
    println "${'>' * 10}Действия${'>' * 40}"
    workout.actions.each { entry ->
        println "$entry.key = $entry.value"
    }
//    println "---Удаляем 1-й шаг${'>'*40}"
//    workout.deleteAction(task, action.id)
//
//    println "${'>'*10}Задачи${'>'*40}"
//    workout.tasks.each { entry ->
//        println "$entry.key = $entry.value"
//    }
//    println "${'>'*10}Действия${'>'*40}"
//    workout.actions.each { entry ->
//        println "$entry.key = $entry.value"
//    }

//
    println "----------Информация о tasks${'>' * 40}"
    List<Task> tasks = workout.findTasksByDate(LocalDate.now())
    def countTask = workout.countTasksByDate(LocalDate.now())
    if (tasks.size() != 0) {
        println "Найденные задачи:"
        tasks.each { it ->
            println it.toString()
        }
    }
    println "Суммарное количество задач: ${countTask}"


    println "----------Информация об actions${'>' * 40}"
    List<Action> actions = workout.findActionsByDate(LocalDate.now())
    def countAction = workout.countActionsByDate(LocalDate.now())
    if (actions.size() != 0) {
        println "Найденные действия:"
        actions.each { it ->
            println it.toString()
        }
    }
    println "Суммарное количество действий: ${countAction}"

    println "${'>' * 10}Запуск событий${'>' * 40}"
    println("Ожидание наступления событий...")
    actions.each { it ->
        it.value.execute()
    }


}


