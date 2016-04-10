package me.wbprime.springmvctesting.unittest.normal.java.services.impl;


import me.wbprime.springmvctesting.unittest.normal.java.dao.TodoDAO;
import me.wbprime.springmvctesting.unittest.normal.java.dto.TodoDTO;
import me.wbprime.springmvctesting.unittest.normal.java.exceptions.TodoNotFoundException;
import me.wbprime.springmvctesting.unittest.normal.java.models.Todo;
import me.wbprime.springmvctesting.unittest.normal.java.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TodoDAO todoDao;

    @Transactional
    @Override
    public Todo add(TodoDTO added) {
        logger.debug("Adding a new to-do entry with information: {}", added);

        Todo model = Todo.getBuilder(added.getTitle())
                .description(added.getDescription())
                .build();

        return todoDao.save(model);
    }

    @Transactional(rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo deleteById(Long id) throws TodoNotFoundException {
        logger.debug("Deleting a to-do entry with id: {}", id);

        Todo deleted = findById(id);
        logger.debug("Deleting to-do entry: {}", deleted);

        todoDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> findAll() {
        logger.debug("Finding all to-do entries");
        return todoDao.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo findById(Long id) throws TodoNotFoundException {
        logger.debug("Finding a to-do entry with id: {}", id);

        Todo found = todoDao.findOne(id);
        logger.debug("Found to-do entry: {}", found);

        if (found == null) {
            throw new TodoNotFoundException("No to-entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo update(TodoDTO updated) throws TodoNotFoundException {
        logger.debug("Updating contact with information: {}", updated);

        Todo model = findById(updated.getId());
        logger.debug("Found a to-do entry: {}", model);

        model.update(updated.getDescription(), updated.getTitle());

        return model;
    }
}
