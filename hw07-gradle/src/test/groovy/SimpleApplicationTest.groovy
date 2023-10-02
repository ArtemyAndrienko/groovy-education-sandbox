import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.otus.groovy.task07.SimpleApplication

import static org.junit.jupiter.api.Assertions.assertEquals

class SimpleApplicationTest {

    @Test
    @DisplayName("Simple constructor check that generated class works")
    void testSimpleAppCreate() {

        SimpleApplication app = new SimpleApplication()
        app.setField1("field1Value")
        def expected = app.getField1()
        assertEquals(app.field1, expected)

    }
}
