import im.educ.groovy.model.Action
import im.educ.groovy.model.Task
import im.educ.groovy.task.TaskManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.LocalDateTime as ldt

import static org.junit.jupiter.api.Assertions.assertThrows

class TodoTest {
    private TaskManager service
    private Task task
    private Action action

    @BeforeEach
    void init() {
        service = new TaskManager()
        task = service.addTask(
                new Task("Комплекс упражнений СБУ",
                        "Комплекс специальных беговых упражнений поможет исправить неточные движения или технику бега",
                        ldt.now(),
                        ldt.now().plusMinutes(30)
                )
        )
        action = new Action("Подскоки с постановкой шага",
                "Упражнения Комплекс упражнений СБУ",
                ldt.now(), ldt.now().plusMinutes(1),
                task.id)
    }

    @AfterEach
    void swipeWs() {
        service.clearActions()
        service.clearTasks()
    }

    @Test
    void createTask() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
    }

    @Test
    void deleteTask() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
        def beforeDeleteId = task.id
        service.deleteTask(task.id)

        Exception ex = assertThrows(RuntimeException) {
            task = service.findTask(beforeDeleteId)
        }

        def message = ex.getMessage()
        assert message == "Task с ${beforeDeleteId} отсутствует в списке"
    }

    @Test
    void findTask() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
        task = service.findTask(task.id)

        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
        assert task.name == "Комплекс упражнений СБУ"
        assert task.description == "Комплекс специальных беговых упражнений поможет исправить неточные движения или технику бега"
    }

    @Test
    void findTaskNotValidId() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
        Exception ex = assertThrows(RuntimeException) {
            task = service.findTask('xxx-xxx')
        }

        def message = ex.getMessage()
        assert message == "Task с xxx-xxx отсутствует в списке"
    }

    @Test
    void findAllTaskByDate() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        def tasks = service.findTasksByDate(LocalDate.now())
        assert tasks.size() == 1
    }

    @Test
    void numbersOfTaskByDate() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        def count = service.countTasksByDate(LocalDate.now())
        assert count == 1
    }

    @Test
    void addAction() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        action = service.addAction(task, action)
        assert action.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
    }

    @Test
    void deleteAction() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        action = service.addAction(task, action)
        assert action.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
        service.deleteAction(task, action.id)

        Exception ex = assertThrows(RuntimeException) {
            action = service.findActionById('xxx')
        }

        def message = ex.getMessage()
        assert message == "Action с xxx отсутствует в списке"
    }

    @Test
    void findAction() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        action = service.addAction(task, action)
        assert action.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        action = service.addAction(task, action)

        assert action.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/
        assert action.name == "Подскоки с постановкой шага"
        assert action.description == "Упражнения Комплекс упражнений СБУ"
    }

    @Test
    void findActionNotValidId() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        Exception ex = assertThrows(RuntimeException) {
            action = service.findActionById('xxx')
        }

        def message = ex.getMessage()
        assert message == "Action с xxx отсутствует в списке"
    }

    @Test
    void findAllActionByDate() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        action = service.addAction(task, action)
        assert action.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        def actions = service.countActionsByDate(LocalDate.now())
        assert actions == 1
    }

    @Test
    void countActionsByDate() {
        assert task.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        action = service.addAction(task, action)
        assert action.id ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/

        def count = service.countActionsByDate(LocalDate.now())
        assert count == 1
    }
}