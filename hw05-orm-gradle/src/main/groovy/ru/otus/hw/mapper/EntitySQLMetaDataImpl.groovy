package ru.otus.hw.mapper

class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    private final EntityClassMetaData<T> entityClassMetaData

    EntitySQLMetaDataImpl(entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData
    }

    @Override
    String getSelectAllSql() {
        return "SELECT * FROM ${entityClassMetaData.getName()}"
    }

    @Override
    String getSelectByIdSql(long id) {
        def selectByIdQuery = "SELECT * FROM ${entityClassMetaData.getName()} WHERE ${entityClassMetaData.getIdField().name} = ${id}"
        return selectByIdQuery
    }

    @Override
    String getInsertSql(Object object) {
        def properties = object.properties

        def keys = []
        def values = []
        properties.each {ent ->
            if (entityClassMetaData.getFieldsWithoutId()
                    .collect {it.name}
                    .contains(ent.key)) {
                keys << ent.key
                values << ent.value
            }
        }
        def insertQuery = "INSERT INTO ${entityClassMetaData.getName()} (${keys.join(", ")}) VALUES(${values.collect {'?'}.join(", ")})"
        return insertQuery
    }

    @Override
    String getUpdateSql(Object object) {
        def properties = object.properties

        def fieldsUpdate = properties
                            .findAll {ent -> entityClassMetaData.getFieldsWithoutId().collect {it.name}.contains(ent.key)}
                            .collect {ent -> "${ent.key} = ?"}.join(",")
        def updateQuery = "UPDATE ${entityClassMetaData.getName()} SET ${fieldsUpdate} WHERE ${entityClassMetaData.getIdField().name} = ${properties.get(entityClassMetaData.getIdField().name)}"
        return updateQuery
    }
}
