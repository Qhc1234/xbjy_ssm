package cn.jian.mapper;

import cn.jian.entity.Dept;
import cn.jian.entity.DeptExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface DeptMapper extends Mapper<Dept> {


    @Select("SELECT a.* FROM dept a,`user` b " +
            "WHERE a.id=b.dept_id " +
            "AND b.id=#{uid} ")
    Dept selectByUId(int uid);


    @Select("SELECT a.*,b.dept_id,COUNT(b.dept_id) count FROM dept a " +
            "LEFT JOIN `user` b ON  " +
            "a.id=b.dept_id " +
            "GROUP BY a.id")
    List<Map<String,Object>> selectDeptAndUserCount();
}