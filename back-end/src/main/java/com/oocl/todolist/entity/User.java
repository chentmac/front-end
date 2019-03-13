package com.oocl.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "USER")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "USERNAME", length = 10, unique = true)
    private String userName;

    @Column(name = "PASSWORD", length = 10)
    private String password;

    @Column(name = "DEPARTMENT")
    private String department;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "initiator", cascade = CascadeType.ALL)
    private List<Task> initialedTasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Task> getInitialedTasks() {
        return initialedTasks;
    }

    public void setInitialedTasks(List<Task> initialedTasks) {
        this.initialedTasks = initialedTasks;
    }
}
