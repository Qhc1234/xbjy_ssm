package cn.jian.service;

import cn.jian.entity.Userinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserinfoService extends IService<Userinfo> {

    Map<String,Object> selectDetailById(Integer id);

    int updateUser(Map<String, Object> params);

    void addLookCount(Integer id);
}
