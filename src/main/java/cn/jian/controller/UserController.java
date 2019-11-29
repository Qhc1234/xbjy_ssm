package cn.jian.controller;

import cn.jian.entity.User;
import cn.jian.entity.Userfocus;
import cn.jian.entity.Userinfo;
import cn.jian.service.UserService;
import cn.jian.service.UserfocusService;
import cn.jian.service.UserinfoService;
import cn.jian.utils.Upload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    UserfocusService userfocusService;

    @RequestMapping("toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getContextPath());
        return "redirect:/login.html";
    }

    @RequestMapping("toList")
    public String toList() {
        return "/user_list";
    }

    @RequestMapping("focus/toList")
    public String toFocusList() {
        return "/user_focus";
    }

    @RequestMapping("toDetail")
    public String toDetail() {
        return "/user_detail";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public Map<String, Object> doLogin(String username, String password, String remember, HttpSession session, HttpServletResponse response) {

        User checkUser = new User();
        checkUser.setUsername(username);
        checkUser.setPassword(password);
        User user = userService.doLogin(checkUser);//返回user不为null则表示正确用户

        //返回结果
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            if ("true".equals(remember)) {//记住密码,user保存到cookie
                Base64.Encoder encoder = Base64.getEncoder();//处理编码   json
                try {
                    String text = new ObjectMapper().writeValueAsString(user);//转换成json
                    //转换成base64格式字符串
                    String encodeToString = encoder.encodeToString(text.getBytes());
                    //保存7天
                    Cookie cookie = new Cookie("userInfo", encodeToString);
                    cookie.setMaxAge(60 * 60 * 24 * 7);
                    cookie.setPath("/");//当前应用下所有请求都会带cookie信息
                    response.addCookie(cookie);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            session.setAttribute("user", user);
            map.put("flag", true);
            map.put("msg", "登录成功");
            map.put("user", user);
        } else {
            map.put("flag", false);
            map.put("msg", "登录失败");
        }
        return map;
    }

    @RequestMapping("toIndex")
    public String toIndex() {
        return "/index";
    }


    @RequestMapping("findPage")
    @ResponseBody
    public PageInfo<Userinfo> list(@RequestBody Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        return userService.selectUserinfoByCondition(params);
    }

    @RequestMapping("findById")
    @ResponseBody
    public Map<String, Object> findDetailById(@RequestParam("id") Integer id) {
        return userinfoService.selectDetailById(id);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Map<String, Object> params) {
        int i = userinfoService.updateUser(params);
        //返回结果
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (i > 0) {
            map.put("flag", true);
            map.put("msg", "跟新成功");
        } else {
            map.put("flag", false);
            map.put("msg", "跟新失败");
        }
        return map;
    }

    //查关注
    @RequestMapping("findFocus")
    @ResponseBody
    public List<Integer> findFocus(HttpSession session) {
        //通过session获取用户id
        User user = (User) session.getAttribute("user");
        List<Integer> usersId = userfocusService.selectFocus(user.getId());
        return usersId;
    }

    //加关注
    @RequestMapping("addFocus")
    @ResponseBody
    public Map<String, Object> addFocus(@RequestParam("id") Integer id, HttpSession session) {
        //通过session获取用户id
        User user = (User) session.getAttribute("user");
        Userfocus userfocus = new Userfocus();
        userfocus.setUserId(user.getId());
        userfocus.setUserFocusId(id);
        int i = userfocusService.insertSelective(userfocus);
        //返回结果
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (i > 0) {
            map.put("flag", true);
            map.put("msg", "关注成功");
        } else {
            map.put("flag", false);
            map.put("msg", "关注失败");
        }
        return map;
    }

    //取消关注
    @RequestMapping("deleteFocus")
    @ResponseBody
    public Map<String, Object> deleteFocus(@RequestParam("id") Integer id, HttpSession session) {
        //通过session获取用户id
        User user = (User) session.getAttribute("user");
        int i = userfocusService.deleteByUidAndFid(user.getId(), id);
        //返回结果
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (i > 0) {
            map.put("flag", true);
            map.put("msg", "取消关注成功");
        } else {
            map.put("flag", false);
            map.put("msg", "取消关注失败");
        }
        return map;
    }

    //查询已关注用户
    @RequestMapping("findPageFocus")
    @ResponseBody
    public PageInfo<Userinfo> findPageFocus(@RequestBody Map<String, Object> params, HttpSession session) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        //通过session获取用户id
        User user = (User) session.getAttribute("user");
        return userfocusService.selectUserinfoByConditionFocus(params, user.getId());
    }

    //查询关注数
    @RequestMapping("findFocusCount")
    @ResponseBody
    public Integer findFocusCount(@RequestParam("id") Integer id) {
        return userfocusService.selectFocusCount(id);
    }


    //查看数加1
    @RequestMapping("addLookCount")
    @ResponseBody
    public String addLookCount(@RequestParam("id") Integer id) {
        try {
            userinfoService.addLookCount(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //图片上传
    @RequestMapping("uploadImg")
    public String uploadImg(@RequestParam(value = "img", required = false) MultipartFile img, @RequestParam(value = "userId", required = false) Integer userId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = Upload.fileUpload(img, request);

        //文件名存入数据库
        Userinfo userinfo = userinfoService.selectByPrimaryKey(userId);
        userinfo.setPic(fileName);
        int i = userinfoService.updateByPrimaryKeySelective(userinfo);
        System.out.println(i);
        return "/user_list";
    }

    //注册
    @RequestMapping("doRegister")
    @ResponseBody
    public Map<String, Object> doRegister(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = new HashMap<String, Object>();//返回结果
        //注册校验
        //邮箱是否已使用
        if (params.containsKey("email") && !StringUtils.isEmpty(params.get("email"))) {
            User user=new User();
            user.setEmail((String) params.get("email"));
            User one = userService.selectOne(user);
            if (one!=null){//邮箱已存在
                map.put("flag", false);
                map.put("msg", "邮箱已存在");
                return map;
            }
        }else {
            map.put("flag", false);
            map.put("msg", "请填写邮箱");
            return map;
        }
        //用户名是否已使用
        if (params.containsKey("username") && !StringUtils.isEmpty(params.get("username"))) {
            User user=new User();
            user.setUsername((String) params.get("username"));
            User one = userService.selectOne(user);
            if (one!=null){//用户名已使用
                map.put("flag", false);
                map.put("msg", "用户名已被使用");
                return map;
            }
        }else {
            map.put("flag", false);
            map.put("msg", "请填写用户名");
            return map;
        }
        if (params.containsKey("password") && !StringUtils.isEmpty(params.get("password"))) {
            //注册
            Integer integer = userService.doRegister(params);
            if (integer>0){
                map.put("flag", true);
                map.put("msg", "注册成功");
                return map;
            }else {
                map.put("flag", false);
                map.put("msg", "注册失败");
                return map;
            }
        }else {
            map.put("flag", false);
            map.put("msg", "请填写密码");
            return map;
        }
    }

    //发邮件
    @RequestMapping("sendEmail")
    @ResponseBody
    public Map<String, Object> sendEmail(@RequestParam("email") String email,HttpSession session) {
        HashMap<String, Object> map = new HashMap<String, Object>();//返回结果

        if (!email.equals("")) {
            //2.生成随机码
            String uuid = UUID.randomUUID().toString();
            //3.保存随机码和 email
            session.setAttribute("uuid", uuid);
            session.setAttribute("email", email);

            //4.发送邮件
            int i = userService.sendEmail("你正在修改登录密码，这是验证码:" + uuid + " ,如果不是你本人操作，请联系110。", email);
            if (i == 1) {
                map.put("flag", true);
                map.put("msg", "验证码已发至你的邮箱");
                return map;
            } else {
                map.put("flag", false);
                map.put("msg", "你输入的邮箱未注册");
                return map;
            }
        } else {
            map.put("flag", false);
            map.put("msg", "请输入邮箱地址");
            return map;
        }
    }


    //修改密码
    @RequestMapping("changePassword")
    @ResponseBody
    public Map<String, Object> changePassword(@RequestBody Map<String, Object> params,HttpSession session) {
        HashMap<String, Object> map = new HashMap<String, Object>();//返回结果
        User user=new User();
        user.setEmail((String) session.getAttribute("email"));
        User one = userService.selectOne(user);
        if (params.containsKey("newPassword") && !StringUtils.isEmpty(params.get("newPassword"))) {
            one.setPassword((String) params.get("newPassword"));
            if (params.containsKey("code") && !StringUtils.isEmpty(params.get("code"))) {
                if (params.get("code").equals(session.getAttribute("uuid"))){
                    int result = userService.updateByPrimaryKey(one);
                    if (result > 0) {
                        map.put("flag", true);
                        map.put("msg", "修改成功");
                        return map;
                    } else {
                        map.put("flag", false);
                        map.put("msg", "修改失败，请重试");
                        return map;
                    }
                }else {
                    map.put("flag", false);
                    map.put("msg", "验证码不正确");
                    return map;
                }
            }else {
                map.put("flag", false);
                map.put("msg", "请填写验证码");
                return map;
            }
        }else {
            map.put("flag", false);
            map.put("msg", "请填写新密码");
            return map;
        }

    }


    //退出登录
    @RequestMapping("logout")
    public String logout(HttpSession session,HttpServletResponse response) {
        session.invalidate();//清空session
        Cookie deleteNewCookie = new Cookie("userInfo", null);
        deleteNewCookie.setMaxAge(0); //删除该Cookie
        deleteNewCookie.setPath("/");
        response.addCookie(deleteNewCookie);
        return "redirect:/login.html";
    }


    @RequestMapping("checkLogin")
    @ResponseBody
    public User checkLogin(HttpSession session) {
        //通过session获取用户id
        User user = (User) session.getAttribute("user");
        return user;
    }
}
