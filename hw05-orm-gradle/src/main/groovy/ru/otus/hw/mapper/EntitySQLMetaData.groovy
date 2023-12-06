package ru.otus.hw.mapper

/**
 * Создает SQL - запросы
 */
interface EntitySQLMetaData<T> {
    String getSelectAllSql()

    String getSelectByIdSql(long id)

    String getInsertSql(Object object)

    String getUpdateSql(Object object)
}
