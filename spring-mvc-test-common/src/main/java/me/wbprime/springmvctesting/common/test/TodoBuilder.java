package me.wbprime.springmvctesting.common.test;


import me.wbprime.springmvctesting.common.models.Todo;

/**
 * Class: TodoBuilder
 * Date: 2016/04/14 12:09
 *
 * @author Elvis Wang [bo.wang35@renren-inc.com]
 */
public final class TodoBuilder {
    private Todo result;

    public TodoBuilder() {
        result = new Todo();
    }

    public TodoBuilder id(final Long val) {
        ReflectionHelper.setField(result, "id", val);
        return this;
    }

    public TodoBuilder title(final String val) {
        ReflectionHelper.setField(result, "title", val);
        return this;
    }

    public TodoBuilder description(final String val) {
        ReflectionHelper.setField(result, "description", val);
        return this;
    }

    public Todo build() {
        return result;
    }
}
