package com.springcloud.task.entity;

import javax.persistence.*;
import java.io.Serializable;

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

}
