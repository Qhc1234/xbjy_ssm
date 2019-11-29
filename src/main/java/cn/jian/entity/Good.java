package cn.jian.entity;

import javax.persistence.*;

@Table(name = "good")
public class Good {
    @Id
    private Integer id;

    /**
     * 点赞的用户id
     */
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 点赞的文章id
     */
    @Column(name = "a_id")
    private Integer aId;

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
     * 获取点赞的用户id
     *
     * @return u_id - 点赞的用户id
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 设置点赞的用户id
     *
     * @param uId 点赞的用户id
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * 获取点赞的文章id
     *
     * @return a_id - 点赞的文章id
     */
    public Integer getaId() {
        return aId;
    }

    /**
     * 设置点赞的文章id
     *
     * @param aId 点赞的文章id
     */
    public void setaId(Integer aId) {
        this.aId = aId;
    }
}