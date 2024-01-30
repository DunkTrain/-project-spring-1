package com.javarush.shevchenko.controller;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import static java.util.Objects.isNull;
import com.javarush.shevchenko.domain.Task;
import com.javarush.shevchenko.dto.TaskDTO;
import org.springframework.stereotype.Controller;
import com.javarush.shevchenko.service.TaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для управления задачами.
 */

@Getter
@Setter
@Controller
@RequestMapping("/")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    /**
     * Конструктор контроллера задач.
     *
     * @param taskService сервис для работы с задачами
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Обработка GET запроса для получения списка задач.
     * @param model объект модели
     * @param page номер текущей страницы
     * @param limit количество задач на странице
     * @return имя представления для отображения списка задач
     */
    //    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);

        int pagesTotal = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if (pagesTotal > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pagesTotal).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    /**
     * Обработка POST запроса для редактирования задачи.
     * @param model объект модели
     * @param id идентификатор задачи
     * @param taskDTO данные задачи для редактирования
     * @return имя представления для отображения списка задач
     */
    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestBody TaskDTO taskDTO) {
        if (isNull(id) || id <= 0) {
            logger.error("An invalid task ID was provided: " + id);
            throw new RuntimeException("Invalid ID.");
        }

        Task task = taskService.edit(id, taskDTO.getDescription(), taskDTO.getStatus());
        return tasks(model, 1, 10);
    }

    /**
     * Обработка POST запроса для добавления новой задачи.
     * @param model объект модели
     * @param taskDTO данные новой задачи
     * @return имя представления для отображения списка задач
     */
    @PostMapping("/")
    public String add(Model model,
                      @RequestBody TaskDTO taskDTO) {
        Task task = taskService.create(taskDTO.getDescription(), taskDTO.getStatus());
        return tasks(model, 1, 10);
    }

    /**
     * Обработка DELETE запроса для удаления задачи.
     * @param model объект модели
     * @param id идентификатор удаляемой задачи
     * @return имя представления для отображения списка задач
     */
    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable Integer id) {
        if (isNull(id) || id <= 0) {
            logger.error("An incorrect task ID was provided: " + id);
            throw new RuntimeException("Invalid ID.");
        }

        taskService.delete(id);
        return tasks(model, 1, 10);
    }
}

