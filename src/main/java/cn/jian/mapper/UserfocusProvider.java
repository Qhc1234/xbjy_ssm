package cn.jian.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.Map;

public class UserfocusProvider {
    public String selectUserinfoByConditionFocus(Map<String,Object> params ,@Param("uid") Integer uid){
        StringBuilder sb = new StringBuilder(
                "SELECT a.* FROM userinfo a,userfocus b " +
                "where a.user_id=b.user_focus_id " +
                "and b.user_id=#{uid} ");

        if(params.containsKey("realName")&& !StringUtils.isEmpty(params.get("realName"))){
            String realName = (String) params.get("realName");
            sb.append("and real_name like concat('%',"+"'"+realName+"'"+",'%') ");
        }
        return sb.toString();
    }
}
