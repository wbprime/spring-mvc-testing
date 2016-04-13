package me.wbprime.springmvctesting.common.services.impl;


import com.google.common.collect.Lists;
import me.wbprime.springmvctesting.common.dao.TodoDAO;
import me.wbprime.springmvctesting.common.dto.TodoDTO;
import me.wbprime.springmvctesting.common.exceptions.TodoNotFoundException;
import me.wbprime.springmvctesting.common.models.Todo;
import me.wbprime.springmvctesting.common.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Class: TodoServiceImpl
 * Date: 2016/04/10 20:52
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
@Service
public class TodoServiceImpl implements TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    @Autowired
    TodoDAO todoDAO;

    @Override
    public Todo add(TodoDTO added) {
        logger.debug("Adding a new to-do entry with information: {}", added);

        final Todo model = Todo.getBuilder(added.getTitle())
            .description(added.getDescription())
            .build();

        return todoDAO.save(model);
    }

    @Override
    public Todo deleteById(Long id) throws TodoNotFoundException {
        logger.debug("Deleting a to-do entry with id: {}", id);

        Todo deleted = findById(id);
        logger.debug("Deleting to-do entry: {}", deleted);

        todoDAO.delete(deleted);
        return deleted;
    }

    @Override
    public List<Todo> findAll() {
        logger.debug("Finding all to-do entries");

        final Iterable<Todo> result = todoDAO.findAll();
        return (null != result) ? Lists.newArrayList(result) : Collections.<Todo>emptyList();
    }

    @Override
    public Todo findById(Long id) throws TodoNotFoundException {
        logger.debug("Finding a to-do entry with id: {}", id);

        final Todo found = todoDAO.findOne(id);
        logger.debug("Found to-do entry: {}", found);

        if (null == found) {
            throw new TodoNotFoundException("No todo-entry found with id: " + id);
        }

        return found;
    }

    @Override
    public Todo update(TodoDTO updated) throws TodoNotFoundException {
        logger.debug("Updating contact with information: {}", updated);

        Todo model = findById(updated.getId());
        logger.debug("Found a to-do entry: {}", model);

        model.update(updated.getDescription(), updated.getTitle());

        return model;
    }
}
