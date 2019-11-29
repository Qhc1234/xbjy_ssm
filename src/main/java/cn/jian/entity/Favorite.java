package cn.jian.entity;

import javax.persistence.*;

@Table(name = "favorite")
public class Favorite {
    @Id
    private Integer id;

    /**
     * 收藏用户id
     */
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 文章id
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
     * 获取收藏用户id
     *
     * @return u_id - 收藏用户id
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 设置收藏用户id
     *
     * @param uId 收藏用户id
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * 获取文章id
     *
     * @return a_id - 文章id
     */
    public Integer getaId() {
        return aId;
    }

    /**
     * 设置文章id
     *
     * @param aId 文章id
     */
    public void setaId(Integer aId) {
        this.aId = aId;
    }
}