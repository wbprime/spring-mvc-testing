package me.wbprime.springmvctesting.common.restcontrollers;

import me.wbprime.springmvctesting.common.dto.TodoDTO;
import me.wbprime.springmvctesting.common.exceptions.TodoNotFoundException;
import me.wbprime.springmvctesting.common.models.Todo;
import me.wbprime.springmvctesting.common.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: TodoController
 * Date: 2016/04/10 12:18
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
@Controller
public class TodoController {
    private static Logger logger = LoggerFactory.getLogger(TodoController.class);

    private TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    @ResponseBody
    public TodoDTO add(@Valid @RequestBody TodoDTO dto) {
        logger.debug("Adding a new to-do entry with information: {}", dto);

        Todo added = service.add(dto);
        logger.debug("Added a to-do entry with information: {}", added);

        return createDTO(added);
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public TodoDTO deleteById(@PathVariable("id") Long id) throws TodoNotFoundException {
        logger.debug("Deleting a to-do entry with id: {}", id);

        Todo deleted = service.deleteById(id);
        logger.debug("Deleted to-do entry with information: {}", deleted);

        return createDTO(deleted);
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoDTO> findAll() {
        logger.debug("Finding all todo entries.");

        List<Todo> models = service.findAll();
        logger.debug("Found {} to-do entries.", models.size());

        return createDTOs(models);
    }

    private List<TodoDTO> createDTOs(List<Todo> models) {
        List<TodoDTO> dtos = new ArrayList<TodoDTO>();

        for (Todo model : models) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TodoDTO findById(@PathVariable("id") Long id) throws TodoNotFoundException {
        logger.debug("Finding to-do entry with id: {}", id);

        Todo found = service.findById(id);
        logger.debug("Found to-do entry with information: {}", found);

        return createDTO(found);
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public TodoDTO update(@Valid @RequestBody TodoDTO dto, @PathVariable("id") Long todoId) throws TodoNotFoundException {
        logger.debug("Updating a to-do entry with information: {}", dto);

        Todo updated = service.update(dto);
        logger.debug("Updated the information of a to-entry to: {}", updated);

        return createDTO(updated);
    }

    private TodoDTO createDTO(Todo model) {
        TodoDTO dto = new TodoDTO();

        dto.setId(model.getId());
        dto.setDescription(model.getDescription());
        dto.setTitle(model.getTitle());

        return dto;
    }
}
