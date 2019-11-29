package cn.jian.entity;

import javax.persistence.*;

@Table(name = "user")
public class User {
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 0：公开(默认值) 1：私密
     */
    @Column(name = "is_secret")
    private String isSecret;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门Id
     */
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取0：公开(默认值) 1：私密
     *
     * @return is_secret - 0：公开(默认值) 1：私密
     */
    public String getIsSecret() {
        return isSecret;
    }

    /**
     * 设置0：公开(默认值) 1：私密
     *
     * @param isSecret 0：公开(默认值) 1：私密
     */
    public void setIsSecret(String isSecret) {
        this.isSecret = isSecret == null ? null : isSecret.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取部门Id
     *
     * @return dept_id - 部门Id
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置部门Id
     *
     * @param deptId 部门Id
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}