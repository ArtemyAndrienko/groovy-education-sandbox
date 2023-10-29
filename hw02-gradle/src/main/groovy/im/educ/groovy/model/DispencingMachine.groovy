package im.educ.groovy.model

enum DispencingMachine {
    mod5(5000, 100,
            1000, 25,
            500, 50,
            200, 100,
            100, 100),
    mod3(1000, 50,
            500, 100,
            200, 100),
    mod1(1000, 50)

    Map<String, String> map

    DispencingMachine(Integer... keysAndValues) {
        if ((keysAndValues.length & 1) != 0)
            throw new IllegalArgumentException("keysAndValues has odd size: " + Arrays.toString(keysAndValues))
        this.map = new HashMap<>()
        for (int i = 0; i < keysAndValues.length; i += 2) {
            if (keysAndValues[i] == null || keysAndValues[i + 1] == null)
                throw new NullPointerException()
            if (this.map.put(keysAndValues[i], keysAndValues[i + 1]) != null)
                throw new IllegalArgumentException("keysAndValues has duplicate key named '" + keysAndValues[i] + "': " + Arrays.toString(keysAndValues))
        }

        Map<Integer, Integer> sortedMap = new TreeMap<>(Collections.reverseOrder())
        sortedMap.putAll(this.map)

        this.map = sortedMap

    }

    Map<Integer, Integer> getMap() {
        return this.map
    }


}



