package me.wbprime.springmvctesting.unittest.normal.java.models;


import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Class: Todo
 * Date: 2016/04/10 19:16
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
@Entity
@Table(name="todos")
public class Todo {
    public static final int MAX_LENGTH_DESCRIPTION = 50;
    public static final int MAX_LENGTH_TITLE       = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @Column(name = "title", nullable = false, length = MAX_LENGTH_TITLE)
    private String title;

    @Version
    private long version;

    public Todo() {

    }

    public static Builder getBuilder(String title) {
        return new Builder(title);
    }

    public Long getId() {
        return id;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }

    public String getTitle() {
        return title;
    }

    public long getVersion() {
        return version;
    }

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
        modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = DateTime.now();
    }

    public void update(String description, String title) {
        this.description = description;
        this.title = title;
    }

    public static class Builder {

        private Todo built;

        public Builder(String title) {
            built = new Todo();
            built.title = title;
        }

        public Todo build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Todo{");
        sb.append("id=").append(id);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", description='").append(description).append('\'');
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", title='").append(title).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
