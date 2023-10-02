package im.educ.groovy.model

import groovy.transform.Canonical

@Canonical
class Endpoint extends RedefinableConfiguration {
    Integer port
    Boolean secure


    Endpoint() {
        this.port = 80
        this.secure = false
    }


    @Override
    String toString() {
        return "Endpoint{" +
                "port=${this.port}" +
                ", secure=${this.secure}" +
                "}";
    }
}