package cn.jian.inteceptor;


import cn.jian.entity.User;
import cn.jian.mapper.UserMapper;
import cn.jian.service.UserService;
import cn.jian.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;

/**
 * 登录拦截  未登录用户不能访问
 */
@Configurable
public class LoginInteceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            return true;
        }else {
            //查看cookie，是否可以免密登录
            Cookie[] cookies = request.getCookies();
            Cookie cookie = null;
            if (cookies != null) {
                for (Cookie cookie1 : cookies) {//获取Cookie
                    if ("userInfo".equals(cookie1.getName())) {
                        cookie = cookie1;
                    }
                }
            }
            //cookie存在则检验Cookie信息，不存在则拦截并跳转到登录页
            if (cookie != null) {
                String value = cookie.getValue();
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] bytes = decoder.decode(value);
                String userJson = new String(bytes, "utf-8");
                ObjectMapper mapper = new ObjectMapper();
                User cookieUser = mapper.readValue(userJson, User.class);

                //登录校验
                User checkUser = new User();
                checkUser.setUsername(cookieUser.getUsername());
                checkUser.setPassword(cookieUser.getPassword());
                User usercheck = userService.doLogin(checkUser);//返回user不为null则表示正确用户

                if (usercheck!=null) {//登录成功
                    session.setAttribute("user",usercheck);
                    return true;
                } else {
                    cookie.setMaxAge(0);//清除不正确cookie信息
                    System.out.println("拦截到非法的用户：" + request.getRemoteAddr());
                    //登录失败
                    request.getRequestDispatcher("/user/toLogin").forward(request,response);
                    return false;
                }

            } else {
                System.out.println("拦截到非法的用户：" + request.getRemoteAddr());
                //没有登录
                response.sendRedirect(request.getContextPath() + "/user/toLogin");
                return false;
            }
        }
    }
}
