package com.springcloud.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskVo {

  private long taskId;
  private String title;
  private String content;
  private Date expireDate;
  private long initiatorId;
  private List<Long> executorsId;
  private String initiatorName;
  private List<String> executorsName;
  private Map<String, Boolean> executorCompleteMap;

  public long getTaskId() {
    return taskId;
  }

  public void setTaskId(long taskId) {
    this.taskId = taskId;
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

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public long getInitiatorId() {
    return initiatorId;
  }

  public void setInitiatorId(long initiatorId) {
    this.initiatorId = initiatorId;
  }

  public List<Long> getExecutorsId() {
    return executorsId;
  }

  public void setExecutorsId(List<Long> executorsId) {
    this.executorsId = executorsId;
  }

  public String getInitiatorName() {
    return initiatorName;
  }

  public void setInitiatorName(String initiatorName) {
    this.initiatorName = initiatorName;
  }

  public List<String> getExecutorsName() {
    return executorsName;
  }

  public void setExecutorsName(List<String> executorsName) {
    this.executorsName = executorsName;
  }

  public Map<String, Boolean> getExecutorCompleteMap() {
    return executorCompleteMap;
  }

  public void setExecutorCompleteMap(Map<String, Boolean> executorCompleteMap) {
    this.executorCompleteMap = executorCompleteMap;
  }
}
