package ru.otus.hw.repository

import groovy.transform.Canonical
import ru.otus.hw.mapper.EntityClassMetaData
import ru.otus.hw.mapper.EntitySQLMetaData
import ru.otus.hw.mapper.ResultListMapper

@Canonical
class DataTemplateJdbc<T> implements DataTemplate<T> {
    private DbExecutor dbExecutor
    private EntitySQLMetaData entitySQLMetaData
    private EntityClassMetaData entityClassMetaData

    @Override
    def findById(connection, id) {
        return dbExecutor.executeSelect(connection,
                                        entitySQLMetaData.getSelectByIdSql(id),
                                        { new ResultListMapper<>(entityClassMetaData).apply(it) })
    }

    @Override
    def findAll(connection) {
        return dbExecutor.executeSelect(connection,
                                        entitySQLMetaData.getSelectAllSql(),
                                        { new ResultListMapper<>(entityClassMetaData).apply(it) })
    }

    @Override
    long insert(connection, object) {
        return dbExecutor.executeStatement(connection,
                                            entitySQLMetaData.getInsertSql(object),
                                            getListParams(object, entityClassMetaData))
    }

    @Override
    void update(connection, object) {
        dbExecutor.executeStatement(connection,
                                    entitySQLMetaData.getUpdateSql(object),
                                    getListParams(object, entityClassMetaData))
    }


    static Object getListParams(Object object, EntityClassMetaData entityClassMetaData) {
        def params = []
        object.properties.each { e ->
            if (entityClassMetaData.getFieldsWithoutId()
                    .collect { it.name }
                    .contains(e.key)) {
                params << e.value
            }
        }
        return params
    }
}