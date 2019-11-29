package cn.jian.service;

import cn.jian.entity.Conference;
import cn.jian.entity.User;
import cn.jian.entity.Userinfo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    User doLogin(User checkUser);

    List<User> selectByDeptId(int id);

    PageInfo<Userinfo> selectUserinfoByCondition(Map<String, Object> params);

    Map<String,Object> selectDetailByUid(int id);

    Integer doRegister(Map<String, Object> params);

    int sendEmail(String code, String to);

}
