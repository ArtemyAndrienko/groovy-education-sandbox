package ru.otus.hw.repository

/**
 * CRUD operations with database (like JdbcTemplate)
 */
interface DataTemplate<T> {
    def findById(connection,  id)

    def findAll(connection)

    long insert(connection, object)

    void update(connection, object)
}
