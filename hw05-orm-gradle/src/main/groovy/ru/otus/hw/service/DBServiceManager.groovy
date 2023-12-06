package ru.otus.hw.service

import ru.otus.hw.model.Manager


interface DBServiceManager {
    Manager saveManager(Manager manager)
    Manager getManager(long no)
    List<Manager> findAll()
}
