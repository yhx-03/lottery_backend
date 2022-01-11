package com.demo.spring_redis.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @Author yhx
 * @Description 总用户
 * @Date 16:09 2022/1/6
 **/
@TableName("user")
public class User {

    private Long id;
    private String username;
    private String password;
    private Integer status;
    private Integer createTime;
    private Integer loginTime;

    public User(){}

    public User(Long id, String username, String password, Integer status, Integer createTime, Integer loginTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(status, user.status) && Objects.equals(createTime, user.createTime) && Objects.equals(loginTime, user.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, status, createTime, loginTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id:" + id +
                ", username:'" + username + '\'' +
                ", password:'" + password + '\'' +
                ", status:" + status +
                ", createTime:" + createTime +
                ", loginTime:" + loginTime +
                '}';
    }
}
