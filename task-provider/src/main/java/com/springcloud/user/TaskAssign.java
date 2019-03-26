package com.springcloud.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "TASK_ASSIGN")
@javax.persistence.Entity
public class TaskAssign implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", referencedColumnName = "id", nullable = false)
    private Task task;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "IS_COMPLETED")
    private boolean completed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task getTask() {
      return task;
    }

    public void setTask(Task task) {
      this.task = task;
    }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
