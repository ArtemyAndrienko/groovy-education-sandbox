package im.educ.groovy.model

import groovy.transform.Canonical

@Canonical
class Mapping extends RedefinableConfiguration {
    String url
    Boolean active

    @Override
    String toString() {
        return "Mapping{" +
                "url='${this.url}'" +
                ", active=${this.active}" +
                '}';
    }
}