package ru.otus.hw

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import ru.otus.hw.model.Client
import ru.otus.hw.mapper.EntitySQLMetaDataImpl
import ru.otus.hw.mapper.EntityClassMetaDataImpl

class EntitySQLMetaDataImplTest {

    Client client
    EntitySQLMetaDataImpl entitySQLMetaData

    @BeforeEach
    void init() {
        client = new Client(1, 'Andrey')

        def entityClassMetaData = new EntityClassMetaDataImpl<>(Client.class)
        entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData)
    }

    @AfterEach
    void tearDown() {
        client = null
        entitySQLMetaData = null
    }

    @Test
    void getSelectAllSql() {
        def sql = entitySQLMetaData.getSelectAllSql()

        assert sql == 'SELECT * FROM Client'
    }

    @Test
    void getSelectByIdSql() {
        def sql = entitySQLMetaData.getSelectByIdSql(client.id)

        assert sql == 'SELECT * FROM Client WHERE id = 1'
    }

    @Test
    void getInsertSql() {
        def sql = entitySQLMetaData.getInsertSql(client)

        assert sql == 'INSERT INTO Client (name) VALUES(?)'
    }

    @Test
    void getUpdateSql() {
        def sql = entitySQLMetaData.getUpdateSql(client)

        assert sql == 'UPDATE Client SET name = ? WHERE id = 1'
    }
}