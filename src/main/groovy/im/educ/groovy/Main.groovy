package im.educ.groovy


class App {

    static void main(String[] args) {
        def text = "Hello world!"

        def encoded = text.bytes.encodeBase64().toString()
        println "[ENCODED]: ${ encoded }"


        byte[] decoded = encoded.decodeBase64()
        println "[DECODED]: ${ new String(decoded)}"
    }
}