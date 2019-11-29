package cn.jian.mapper;

import cn.jian.entity.Favorite;
import cn.jian.entity.FavoriteExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface FavoriteMapper extends Mapper<Favorite> {

    //查询我关注的人中也收藏这篇文章的人
    @Select("SELECT a.user_id,a.real_name FROM userinfo a, " +
            "(SELECT * FROM favorite where a_id=#{aid}  " +
            "and u_id in (SELECT user_focus_id FROM userfocus where user_id=#{uid})) b " +
            "WHERE " +
            "a.user_id=b.u_id")
    List<Map<String,Object>> selectUsersFocusFavorite(@Param("aid")Integer aid,@Param("uid")Integer uid);

    //查询收藏数
    @Select("SELECT count(id) count FROM favorite where a_id=#{aid}")
    Integer selectFavoriteCount(@Param("aid")Integer aid);

    @Select("SELECT * FROM favorite  " +
            "where u_id=#{uid} and a_id=#{aid} ")
    Favorite selectByAidAndUid(@Param("aid")Integer aid,@Param("uid")Integer uid);

    //取消收藏
    @Delete("DELETE FROM favorite WHERE a_id=#{aid} and u_id=#{uid} ")
    int cancel(@Param("aid")Integer aid,@Param("uid")Integer uid);
}