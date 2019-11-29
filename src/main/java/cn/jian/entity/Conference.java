package cn.jian.entity;



import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "conference")
public class Conference {
    @Id
    private Integer id;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 部门id
     */
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 会议标题
     */
    private String title;

    /**
     * 会议内容
     */
    private String content;

    /**
     * 发布日期
     */
    @Column(name = "publish_date")
    private Date publishDate;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 会议状态 0:未开始 1:进行中 2:已结束
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
     * 获取部门名称
     *
     * @return dept_name - 部门名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * 获取部门id
     *
     * @return dept_id - 部门id
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置部门id
     *
     * @param deptId 部门id
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取会议标题
     *
     * @return title - 会议标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置会议标题
     *
     * @param title 会议标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取会议内容
     *
     * @return content - 会议内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置会议内容
     *
     * @param content 会议内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取发布日期
     *
     * @return publish_date - 发布日期
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * 设置发布日期
     *
     * @param publishDate 发布日期
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取会议状态 0:未开始 1:进行中 2:已结束
     *
     * @return status - 会议状态 0:未开始 1:进行中 2:已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置会议状态 0:未开始 1:进行中 2:已结束
     *
     * @param status 会议状态 0:未开始 1:进行中 2:已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}