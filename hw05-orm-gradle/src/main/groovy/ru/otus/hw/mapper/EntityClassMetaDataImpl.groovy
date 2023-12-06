package ru.otus.hw.mapper

import ru.otus.hw.annotation.Id
import ru.otus.hw.exception.EntityMetaDataException

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    Class<T> clazz

    Field idField
    List<Field> withoutIdFields
    List<Field> allFields
    Constructor<T> constructor

    EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz

        this.constructor = findFirstConstructorWithoutParameters()
        this.idField = findIdField()
        this.allFields = findAllFields()
        this.withoutIdFields = findWithoutIdFields()
    }

    def findIdField() {
        if (checkFieldId()) {
            def fields = findAllFields()
                    .findAll {it.isAnnotationPresent(Id.class)}
                    .collect()
            return fields[0]
        }
    }

    def findAllFields() {
        def fields = clazz.getDeclaredFields()
                .findAll { Modifier.isPrivate(it.getModifiers()) }
                .findAll { !Modifier.isStatic(it.getModifiers()) }
//                .findAll { !Modifier.isTransient(it.getModifiers()) }
                .collect()
        return fields
    }

    def findWithoutIdFields() {
        if (checkFieldId()) {
            def fields = findAllFields()
                    .findAll {!it.isAnnotationPresent(Id.class)}
                    .collect()
            return fields
        }
    }


    def checkFieldId() {
        for (def field in clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)) {
                return true
            }
        }
        throw new EntityMetaDataException("@id not found for class ${getName()}")
    }

    @Override
    Field getIdField() {
        return idField
    }

    def findFirstConstructorWithoutParameters() {
        return clazz.getConstructor()
    }

    @Override
    String getName() {
        return clazz.getSimpleName()
    }

    @Override
    List<Field> getAllFields() {
        return allFields
    }

    @Override
    List<Field> getFieldsWithoutId() {
        return withoutIdFields
    }
}
