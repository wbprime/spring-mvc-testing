package me.wbprime.springmvctesting.common.services;


import me.wbprime.springmvctesting.common.dto.TodoDTO;
import me.wbprime.springmvctesting.common.exceptions.TodoNotFoundException;
import me.wbprime.springmvctesting.common.models.Todo;

import java.util.List;

/**
 * Interface: TodoService
 * Date: 2016/04/10 12:23
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public interface TodoService {

    /**
     * Adds a new to-do entry.
     * @param added The information of the added to-do entry.
     * @return  The added to-do entry.
     */
    Todo add(TodoDTO added);

    /**
     * Deletes a to-do entry.
     * @param id    The id of the deleted to-do entry.
     * @return  The deleted to-do entry.
     * @throws TodoNotFoundException    if no to-do entry is found with the given id.
     */
    Todo deleteById(Long id) throws TodoNotFoundException;

    /**
     * Returns a list of to-do entries.
     * @return
     */
    List<Todo> findAll();

    /**
     * Finds a to-do entry.
     * @param id    The id of the wanted to-do entry.
     * @return  The found to-entry.
     * @throws TodoNotFoundException    if no to-do entry is found with the given id.
     */
    Todo findById(Long id) throws TodoNotFoundException;

    /**
     * Updates the information of a to-do entry.
     * @param updated   The information of the updated to-do entry.
     * @return  The updated to-do entry.
     * @throws TodoNotFoundException    If no to-do entry is found with the given id.
     */
    Todo update(TodoDTO updated) throws TodoNotFoundException;
}
