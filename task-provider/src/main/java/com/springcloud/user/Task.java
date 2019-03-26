package com.springcloud.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "TASK")
@Entity
public class Task implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
  private List<TaskAssign> taskAssigns;

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

  public List<TaskAssign> getTaskAssigns() {
    return taskAssigns;
  }

  public void setTaskAssigns(List<TaskAssign> taskAssigns) {
    this.taskAssigns = taskAssigns;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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


}
