package me.wbprime.springmvctesting.common.dao;


import me.wbprime.springmvctesting.common.models.Todo;
import org.springframework.data.repository.CrudRepository;

/**
 * Managed by Spring Data JPA framework
 *
 * Interface: TodoDAO
 * Date: 2016/04/13 17:29
 *
 * @author Elvis Wang [bo.wang35@renren-inc.com]
 */
public interface TodoDAO extends CrudRepository<Todo, Long> {
}
