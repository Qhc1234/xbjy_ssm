package cn.jian.service.impl;

import cn.jian.entity.User;
import cn.jian.entity.Userinfo;
import cn.jian.mapper.UserMapper;
import cn.jian.mapper.UserinfoMapper;
import cn.jian.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Transactional
public class UserinfoServiceImpl extends ServiceImpl<Userinfo> implements UserinfoService {

    @Autowired
    UserinfoMapper userinfoMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String, Object> selectDetailById(Integer id) {
        return userinfoMapper.selectDetailById(id);
    }

    @Override
    public int updateUser(Map<String, Object> params) {
        //手动封装
        Userinfo userinfo = new Userinfo();
        User user  = new User();
        if(params.containsKey("user_id")&&! StringUtils.isEmpty(params.get("user_id"))){
            userinfo.setUserId(((Integer) params.get("user_id")));
            user.setId(((Integer) params.get("user_id")));
        }
        if(params.containsKey("age")&&! StringUtils.isEmpty(params.get("age"))){
            if (params.get("age") instanceof Integer){
                userinfo.setAge(((Integer) params.get("age")));
            }else {
                Integer age=Integer.parseInt((String) params.get("age"));
                userinfo.setAge(age);
            }
        }
        if(params.containsKey("desc")&&! StringUtils.isEmpty(params.get("desc"))){
            userinfo.setDesc((String) params.get("desc"));
        }
        if(params.containsKey("gender")&&! StringUtils.isEmpty(params.get("gender"))){
            userinfo.setGender((String) params.get("gender"));
        }
        if(params.containsKey("phone")&&! StringUtils.isEmpty(params.get("phone"))){
            userinfo.setPhone((String) params.get("phone"));
        }
        if(params.containsKey("real_name")&&! StringUtils.isEmpty(params.get("real_name"))){
            userinfo.setRealName((String) params.get("real_name"));
        }
        if(params.containsKey("is_secret")&&! StringUtils.isEmpty(params.get("is_secret"))){
            if ((Boolean) params.get("is_secret")){
                user.setIsSecret("1");//私密
            }else {
                user.setIsSecret("0");
            }
        }
        if(params.containsKey("dept_id")&&! StringUtils.isEmpty(params.get("dept_id"))){
            user.setDeptId((Integer) params.get("dept_id"));
        }

        //跟新userinfo
        int i = userinfoMapper.updateByPrimaryKeySelective(userinfo);
        //更新user
        i += userMapper.updateByPrimaryKeySelective(user);

        return i;
    }


    @Override
    public void addLookCount(Integer id){
        //搜索查看数
        Userinfo userinfo = userinfoMapper.selectByPrimaryKey(id);
        userinfo.setLook(userinfo.getLook()+1);
        //更新查看数
        int i = userinfoMapper.updateByPrimaryKey(userinfo);
        System.out.println(i);
    }
}
