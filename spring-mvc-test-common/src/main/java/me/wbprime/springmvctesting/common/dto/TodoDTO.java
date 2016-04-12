package me.wbprime.springmvctesting.common.dto;


import me.wbprime.springmvctesting.common.models.Todo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class: TodoDTO
 * Date: 2016/04/10 20:23
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public class TodoDTO {

    private Long id;

    @Length(max = Todo.MAX_LENGTH_DESCRIPTION)
    private String description;

    @NotEmpty
    @Length(max = Todo.MAX_LENGTH_TITLE)
    private String title;

    public TodoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TodoDTO{");
        sb.append("id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
