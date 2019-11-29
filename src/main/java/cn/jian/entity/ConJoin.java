package cn.jian.entity;

import javax.persistence.*;

@Table(name = "con_join")
public class ConJoin {
    @Id
    private Integer id;

    /**
     * 需参加人id
     */
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 会议id
     */
    @Column(name = "c_id")
    private Integer cId;

    /**
     * 状态: 0:未参加 1:已参加
     */
    private Integer status;

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
     * 获取需参加人id
     *
     * @return u_id - 需参加人id
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 设置需参加人id
     *
     * @param uId 需参加人id
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * 获取会议id
     *
     * @return c_id - 会议id
     */
    public Integer getcId() {
        return cId;
    }

    /**
     * 设置会议id
     *
     * @param cId 会议id
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }

    /**
     * 获取状态: 0:未参加 1:已参加
     *
     * @return status - 状态: 0:未参加 1:已参加
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态: 0:未参加 1:已参加
     *
     * @param status 状态: 0:未参加 1:已参加
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}