import im.educ.groovy.App

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals


class AppTest{
    @Test
    void testDisplayEncoded() {
        def test = new App()
        def expected = "Hello Test".bytes.encodeBase64().toString()
        assertEquals(test.Hello("Test", true), expected)
    }

    @Test
    void testDisplayDecoded() {
        def test = new App()
        def expected = "Hello Test"
        assertEquals(test.Hello("Test", false), expected)
    }

}