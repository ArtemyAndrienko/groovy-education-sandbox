package ru.otus.hw.exception

class DataBaseOperationException extends RuntimeException {
    DataBaseOperationException(String message, Throwable cause) {
        super(message, cause)
    }
}
