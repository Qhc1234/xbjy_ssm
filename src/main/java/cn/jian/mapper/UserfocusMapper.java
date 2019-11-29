package cn.jian.mapper;

import cn.jian.entity.Userfocus;
import cn.jian.entity.UserfocusExample;
import java.util.List;
import java.util.Map;

import cn.jian.entity.Userinfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface UserfocusMapper extends Mapper<Userfocus> {

    @Delete("DELETE FROM userfocus WHERE user_id=#{uid} AND user_focus_id=#{fid} ")
    int deleteByUidAndFid(@Param("uid")Integer uid,@Param("fid")Integer fid);


    @Select("SELECT user_focus_id FROM userfocus WHERE user_id=#{uid} ")
    List<Integer> selectFocus(@Param("uid")Integer uid);

    @SelectProvider(type=UserfocusProvider.class,method = "selectUserinfoByConditionFocus")
    List<Userinfo> selectUserinfoByConditionFocus(Map<String, Object> params,@Param("uid") Integer uid);


    @Select("Select COUNT(user_focus_id) count FROM userfocus WHERE user_focus_id=#{id}")
    Integer selectFocusCount(@Param("id")Integer id);
}