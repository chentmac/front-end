package com.oocl.todolist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "TASK")
@javax.persistence.Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INITIATOR", referencedColumnName = "userName", nullable = false)
    private User initiator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_TASK_REL", joinColumns = {@JoinColumn(name = "TASK_ID", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "userName")})
    private List<User> executors;

    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "EXPIRE_DAY")
    private int expireDay;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "CONTENT", length = 500)
    private String content;

    @Column(name = "STATUS", length = 4)
    private long status = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(int expireDay) {
        this.expireDay = expireDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public List<User> getExecutors() {
        return executors;
    }

    public void setExecutors(List<User> executors) {
        this.executors = executors;
    }
}