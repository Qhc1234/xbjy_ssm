package cn.jian.mapper;

import cn.jian.entity.Userinfo;
import cn.jian.entity.UserinfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface UserinfoMapper extends Mapper<Userinfo> {

    @SelectProvider(type=UserinfoProvider.class,method = "selectUserinfoByCondition")
    List<Userinfo> selectUserinfoByCondition(Map<String, Object> params);


    @Select("SELECT a.*,b.is_secret,b.dept_id FROM userinfo a,`user` b " +
            "WHERE a.user_id=b.id " +
            "AND a.user_id=#{id} ")
    Map<String,Object> selectDetailById(@Param("id") Integer id);
}