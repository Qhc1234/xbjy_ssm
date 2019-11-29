package cn.jian.service.impl;

import cn.jian.entity.User;
import cn.jian.entity.Userinfo;
import cn.jian.mapper.UserMapper;
import cn.jian.mapper.UserinfoMapper;
import cn.jian.service.UserService;
import cn.jian.utils.MailUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserinfoMapper userinfoMapper;

    @Override
    public User doLogin(User checkUser) {
        return userMapper.selectOne(checkUser);
    }

    @Override
    public List<User> selectByDeptId(int id) {
        return userMapper.selectByDeptId(id);
    }

    @Override
    public PageInfo<Userinfo> selectUserinfoByCondition(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        return new PageInfo(userinfoMapper.selectUserinfoByCondition(params));
    }

    @Override
    public Map<String,Object> selectDetailByUid(int id){
        HashMap<String, Object> map = new HashMap<String, Object>();
        User user = userMapper.selectByPrimaryKey(id);
        Userinfo userinfo = userinfoMapper.selectByPrimaryKey(id);
        map.put("user",user);
        map.put("userinfo",userinfo);
        return map;
    }


    //注册
    @Override
    public Integer doRegister(Map<String, Object> params) {
        //添加user表
        User user=new User();
        user.setEmail((String) params.get("email"));
        user.setUsername((String) params.get("username"));
        user.setPassword((String) params.get("password"));
        user.setIsSecret("0");
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加userinfo表
        int i=0;
        if (user.getId()!=null){
            Userinfo userinfo = new Userinfo();
            userinfo.setUserId(user.getId());
            userinfo.setLook(0);
            userinfo.setRegisterTime(new Date());
            i = userinfoMapper.insertSelective(userinfo);
        }
        return i;
    }


    /**
     * 发送邮件，包含随机码
     * 1. 校验邮箱是否存在 调用 checkEmail方法
     * 1.1 如果邮箱不存在，return方法
     * 1.2 如果邮箱存在，则执行发送邮件功能
     *
     * @param code 验证码
     * @param to 接受验证码的邮箱
     */
    @Override
    public int sendEmail(String code, String to) {

        // 校验邮箱是否注册
        User user = new User();
        user.setEmail(to);
        User user1 = userMapper.selectOne(user);
        if (user1==null){// 未注册结束方法
            return 0;
        }
        try {
            MailUtil.send(to, "小标交友网验证码", code, "UTF-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
