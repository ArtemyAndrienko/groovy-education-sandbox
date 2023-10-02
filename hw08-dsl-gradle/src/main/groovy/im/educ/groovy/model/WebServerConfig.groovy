package im.educ.groovy.model

import groovy.transform.Canonical

@Canonical
class WebServerConfig extends RedefinableConfiguration {
    String name
    String description

    Endpoint http
    Endpoint https

    List<Mapping> mappings

    def http(@DelegatesTo(value = Endpoint, strategy = Closure.DELEGATE_FIRST) Closure closure) {
        http = new Endpoint()
        closure.delegate = http
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
    }


    def https(@DelegatesTo(value = Endpoint, strategy = Closure.DELEGATE_FIRST) Closure closure) {
        https = new Endpoint()
        closure.delegate = https
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
    }

//    def mappings(@DelegatesTo(value = Mapping, strategy = Closure.DELEGATE_FIRST) Closure closure) {
//        mappings = new Mapping()
//        closure.delegate = mappings
//        closure.resolveStrategy = Closure.DELEGATE_FIRST
//        closure.call()
//    }


    @Override
    String toString() {
        return "WebServerConfig{" +
                "name='${this.name}'" +
                ", description='${this.description}'" +
                ", http=${this.http}" +
                ", https=${this.https}" +
                ", mappings=${this.mappings} " +
                "}";
    }
}