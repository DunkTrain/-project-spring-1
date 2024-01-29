package com.javarush.shevchenko.domain;

// Перечисление, представляющее возможные статусы задачи
public enum Status {
    // Задача находится в процессе выполнения
    IN_PROGRESS,
    // Задача выполнена
    DONE,
    // Выполнение задачи приостановлено
    PAUSED
}
