package cn.jian.mapper;

import cn.jian.entity.Conference;
import cn.jian.entity.ConferenceExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface ConferenceMapper extends Mapper<Conference> {


    @SelectProvider(type=ConferenceProvider.class,method = "selectConferenceByCondition")
    List<Conference> selectConferenceByCondition(Map<String,Object> params);


    @Insert("INSERT INTO `xiaobiao`.`conference` (`dept_name`, `dept_id`, `title`, `content`, `publish_date`, `start_time`, `status`) VALUES ( #{deptName}, #{deptId}, #{title}, #{content}, #{publishDate}, #{startTime}, #{status}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertConference(Conference conference);

    //根据传递的抄送人id集合信息，进行对con_join批量插入
    @InsertProvider(type = ConferenceProvider.class,method = "insertBatchConferenceUser")
    int insertBatchConferenceUser(@Param("cId") Integer id, @Param("ids") List<Object> userIds);


}