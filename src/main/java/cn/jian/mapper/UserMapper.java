package cn.jian.mapper;

import cn.jian.entity.User;
import cn.jian.entity.UserExample;
import java.util.List;
import java.util.Map;

import cn.jian.entity.Userinfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    @Select("SELECT `id`, `username`, `password`, `is_secret`, `email`, `dept_id` FROM `user` WHERE dept_id=#{id}")
    List<User> selectByDeptId(int id);

    @Insert("INSERT INTO `user` (`username`, `password`, `is_secret`, `email`, `dept_id`) VALUES ( #{username}, #{password}, #{isSecret}, #{email}, #{deptId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);
}