package im.educ.groovy


class App {

    static void main(String[] args) {
        def text = "Hello world!"

        def encoded = App.Hello("world!", true)
        println "[ENCODED]: ${encoded}"

        byte[] decoded = encoded.decodeBase64()
        println "[DECODED]: ${new String(decoded)}"
    }

    static String Hello(String str, Boolean encoded) {
        switch (encoded) {
            case true:
                def text = "Hello " + str
                return text.bytes.encodeBase64().toString()
            case false:
                return "Hello " + str
        }
    }

}