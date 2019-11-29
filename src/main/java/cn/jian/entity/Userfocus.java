package cn.jian.entity;

import javax.persistence.*;

@Table(name = "userfocus")
public class Userfocus {
    @Id
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户关注的朋友id
     */
    @Column(name = "user_focus_id")
    private Integer userFocusId;

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
     * 获取用户关注的朋友id
     *
     * @return user_focus_id - 用户关注的朋友id
     */
    public Integer getUserFocusId() {
        return userFocusId;
    }

    /**
     * 设置用户关注的朋友id
     *
     * @param userFocusId 用户关注的朋友id
     */
    public void setUserFocusId(Integer userFocusId) {
        this.userFocusId = userFocusId;
    }
}