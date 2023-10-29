package im.educ.groovy.model

enum Currency {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR")
    private String isoCode = "";

    Currency(String code) {
        this.isoCode = code;
    }

    @Override
    String toString() {
        return isoCode;
    }
}