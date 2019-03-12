package com.oocl.todolist.entity;


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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "INITIATOR", referencedColumnName = "userName", nullable = false)
  private User initiator;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "USER_TASK_REL", joinColumns = {@JoinColumn(name = "TASK_ID", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "userName")})
  private List<User> executors;

  @Column(name = "CREATE_DATE")
  private Date createDate = new Date();

  @Column(name = "EXPIRE_DATE")
  private Date expireDate;

  @Column(name = "TITLE", length = 100)
  private String title;

  @Column(name = "CONTENT", length = 500)
  private String content;

  @Column(name = "STATUS", length = 4)
  private int status = 0;

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

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public List<User> getExecutors() {
    return executors;
  }

  public void setExecutors(List<User> executors) {
    this.executors = executors;
  }
}
