package cn.jian.mapper;

import cn.jian.entity.ConJoin;
import cn.jian.entity.ConJoinExample;
import java.util.List;
import java.util.Map;

import cn.jian.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface ConJoinMapper extends Mapper<ConJoin> {

    //参加会议
    @Update("UPDATE `con_join` SET `status`='1' WHERE (`u_id`=#{uid} and c_id=#{cid}) ")
    int join(@Param("uid")Integer uid,@Param("cid")Integer cid);

    @Select("SELECT * FROM con_join a,  " +
            "userinfo b WHERE  " +
            "a.u_id=b.user_id  " +
            "AND c_id=#{id} ")
    List<Map<String,Object>> selectUsersByCId(@Param("id") Integer id);
}