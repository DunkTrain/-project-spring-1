package com.javarush.shevchenko.service;

import java.util.List;
import org.slf4j.Logger;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import com.javarush.shevchenko.domain.Task;
import com.javarush.shevchenko.dao.TaskDAO;
import com.javarush.shevchenko.domain.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskDAO taskDAO;

    // Конструктор, внедряющий зависимость TaskDAO
    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    // Метод для получения списка задач с пагинацией
    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    // Метод для получения общего количества задач
    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    // Метод для редактирования задачи
    @Transactional
    public Task edit(int id, String description, Status status) {
        // Проверяем, существует ли задача с указанным идентификатором
        if (taskDAO.getById(id).isEmpty()) {
            logger.error("Failed to edit task with ID: " + id + " . Task not found.");
            throw new RuntimeException("Task not found");
        }

        // Получаем задачу по идентификатору и обновляем ее описание и статус
        Task task = taskDAO.getById(id).get();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        logger.info("Task with ID: " + id + " successfully edited.");
        return task;
    }

    // Метод для создания новой задачи
    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        logger.info("New task successfully created with ID: " + task.getId());
        return task;
    }

    // Метод для удаления задачи
    @Transactional
    public void delete(int id) {
        Optional<Task> optionalTask = taskDAO.getById(id);
        if (optionalTask.isEmpty()) {
            logger.error("Failed to delete task with ID: " + id + ". Task not found.");
            throw new RuntimeException("Task not found");
        }

        // Удаляем задачу
        taskDAO.delete(optionalTask.get());
        logger.info("Task with ID: " + id + " successfully deleted.");
    }
}
