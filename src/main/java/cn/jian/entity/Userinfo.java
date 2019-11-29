package cn.jian.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "userinfo")
public class Userinfo {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别: ‘0’-男   ‘1’-女
     */
    private String gender;

    /**
     * 注册时间
     */
    @Column(name = "register_time")
    private Date registerTime;

    /**
     * 上一次登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 头像地址
     */
    private String pic;

    /**
     * 查看数
     */
    private Integer look;

    /**
     * 自我介绍
     */
    @Column(name = "`desc`")
    private String desc;

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取性别: ‘0’-男   ‘1’-女
     *
     * @return gender - 性别: ‘0’-男   ‘1’-女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别: ‘0’-男   ‘1’-女
     *
     * @param gender 性别: ‘0’-男   ‘1’-女
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * 获取注册时间
     *
     * @return register_time - 注册时间
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 设置注册时间
     *
     * @param registerTime 注册时间
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 获取上一次登录时间
     *
     * @return login_time - 上一次登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置上一次登录时间
     *
     * @param loginTime 上一次登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取头像地址
     *
     * @return pic - 头像地址
     */
    public String getPic() {
        return pic;
    }

    /**
     * 设置头像地址
     *
     * @param pic 头像地址
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * 获取查看数
     *
     * @return look - 查看数
     */
    public Integer getLook() {
        return look;
    }

    /**
     * 设置查看数
     *
     * @param look 查看数
     */
    public void setLook(Integer look) {
        this.look = look;
    }

    /**
     * 获取自我介绍
     *
     * @return desc - 自我介绍
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置自我介绍
     *
     * @param desc 自我介绍
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}