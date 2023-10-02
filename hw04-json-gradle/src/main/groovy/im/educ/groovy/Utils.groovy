package im.educ.groovy

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

class Utils {
    def static printElem(LinkedHashMap m, String indent) {
        println "${indent}>>>${m.key}>>>${m.value}"
    }

    def static printArray(LinkedHashMap m, String ind) {
        def className = { Object s -> s.getClass().toString().tokenize(' ')[1] }
        def indent = ind
        for (LinkedHashMap kv : m) {
            if (className(kv.value).equals('java.lang.String')) {
                printElem(kv, indent)
            } else if (className(kv.value).equals('java.util.ArrayList')) {
                indent << "  "
                printArray(kv, indent)
            }
        }
    }


    static transformToXML(String url, String filename) {

        def json = new JsonSlurper()
        def data = json.parseText(new URL(url).getText())
        def writer = new FileWriter(filename)
        def builder = new MarkupBuilder(writer)

        builder.components {
            for (LinkedHashMap k : data.components) {
                builder.component {
                    builder.type(k.type)
                    builder."bom-ref"(k."bom-ref")
                    builder.name(k.name)
                    builder.version(k.version)
                    builder.description(k.description)
                    builder.hashes {
                        for (LinkedHashMap h : (k.hashes as List<LinkedHashMap>)) {
                            builder.alg(name: h.alg)
                            builder.content(h.content)
                        }
                    }
                    builder.licenses {
                        for (LinkedHashMap lic : (k.licenses as List<LinkedHashMap>)) {
                            builder.license {
                                builder.id(lic.license.id)
                            }
                        }
                    }
                    builder.purl(k.purl)
                    builder.externalReferences {
                        for (def ex : k.externalReferences) {
                            builder.reference {
                                builder.type(ex.type)
                                builder.url(ex.url)
                            }

                        }
                    }
                }
            }
        }
    }


    static transformToHTML(String url, String filename) {

        def json = new JsonSlurper()
        def data = json.parseText(new URL(url).getText())

        def writer = new FileWriter(filename)
        def html = new MarkupBuilder(writer)

        html.doubleQuotes = true
        html.expandEmptyElements = true
        html.omitEmptyAttributes = false
        html.omitNullAttributes = false

        html.html {
            html.head {
                title('HTML Example')
                link(href: "./styles.css", type: "text/css", rel: "stylesheet")
            }
            html.body {
                html.div(id: "components") {
                    for (LinkedHashMap k : data.components) {
                        html.ul(id: 'components') {
                            html.li(id: "component") {
                                html.p(k.type)
                                html.p(k."bom-ref")
                                html.p(k.name)
                                html.p(k.version)
                                html.p(k.description)
                                html.ul(id: "hashes") {
                                    for (LinkedHashMap h : (k.hashes as List<LinkedHashMap>)) {
                                        html.li {
                                            html.p(h.alg)
                                            html.p(h.content)
                                        }
                                    }
                                }
                                html.div(id: "licenses") {
                                    for (LinkedHashMap lic : (k.licenses as List<LinkedHashMap>)) {
                                        html.ul(id: "licenses") {
                                            html.li(lic.license.id)
                                        }
                                    }
                                }
                                html.purl(k.purl)
                                html.div(id: "externalReferences") {
                                    html.ul(id: "references") {
                                        for (def ex : k.externalReferences) {
                                            html.li(id: "reference") {
                                                html.p(ex.type)
                                                html.p(ex.url)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
