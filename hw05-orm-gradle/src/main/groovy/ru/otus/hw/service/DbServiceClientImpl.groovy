package ru.otus.hw.service


import org.slf4j.LoggerFactory
import ru.otus.hw.model.Client
import ru.otus.hw.repository.DataTemplate
import ru.otus.hw.sessionmanager.TransactionRunner

import java.util.logging.Logger


class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class)

    private final DataTemplate<Client> dataTemplate
    private final TransactionRunner transactionRunner

    DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> dataTemplate) {
        this.transactionRunner = transactionRunner
        this.dataTemplate = dataTemplate
    }

    @Override
    Client saveClient(Client client) {
        transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                def clientId = dataTemplate.insert(connection, client)
                def createdClient = new Client(clientId, client.getName())
                log.info("created client: {}", createdClient)
                client = createdClient
            }
            dataTemplate.update(connection, client)
            log.info("updated client: {}", client)
            client
        })
    }

    @Override
    Client getClient(long id) {
        transactionRunner.doInTransaction(connection -> {
            def clientOptional = dataTemplate.findById(connection, id)
            log.info("client: {}", clientOptional)
            clientOptional.get().first()
        })
    }

    @Override
    List<Client> findAll() {
        transactionRunner.doInTransaction(connection -> {
            def clientList = dataTemplate.findAll(connection)
            log.info("clientList:{}", clientList)
            clientList.get()
        })
    }
}