package cn.jian.mapper;

import cn.jian.entity.Good;
import cn.jian.entity.GoodExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface GoodMapper extends Mapper<Good> {

    //点赞
    @Update("UPDATE `con_join` SET `status`='1' WHERE (`u_id`=#{uid} and c_id=#{cid}) ")
    int join(@Param("uid")Integer uid,@Param("cid")Integer cid);
    
    //根据文章id查看点赞用户
    @Select("SELECT " +
            "b.user_id,b.real_name,b.pic " +
            "FROM " +
            "good a " +
            "LEFT JOIN userinfo b " +
            "ON a.u_id=b.user_id " +
            "WHERE a.a_id=#{aid}")
    List<Map<String,Object>> selectUsersByAid(@Param("aid")Integer aid);

    @Select("SELECT * FROM good  " +
            "where u_id=#{uid} and a_id=#{aid} ")
    Good selectByAidAndUid(@Param("aid")Integer aid,@Param("uid")Integer uid);

    //取消点赞
    @Delete("DELETE FROM good WHERE a_id=#{aid} and u_id=#{uid} ")
    int cancel(@Param("aid")Integer aid,@Param("uid")Integer uid);
}