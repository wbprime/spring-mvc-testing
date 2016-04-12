package me.wbprime.springmvctesting.common.dao;


import me.wbprime.springmvctesting.common.models.Todo;

import java.util.List;

/**
 * Interface: TodoDAO
 * Date: 2016/04/10 20:49
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public interface TodoDAO {
    Todo save(Todo model);

    void delete(final Todo deleted);

    List<Todo> findAll();

    Todo findOne(final Long id);
}
