package cn.jian.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class UserinfoProvider {

    public String selectUserinfoByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder("SELECT `user_id`, `real_name`, `age`, `phone`, `gender`, `desc`, `register_time`, `login_time`, `pic`, `look` FROM userinfo where 1=1 ");

        if(params.containsKey("realName")&& !StringUtils.isEmpty(params.get("realName"))){
            sb.append("and real_name like concat('%',#{realName},'%') ");
        }
        return sb.toString();
    }
}
