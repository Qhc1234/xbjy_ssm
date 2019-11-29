package cn.jian.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
public class ConferenceProvider {

    public String selectConferenceByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder("SELECT `id`,`dept_name`,`dept_id`,`title`,`content`,`publish_date`,`start_time`,`status` FROM conference where 1=1 ");


        if(params.containsKey("userId")&& !StringUtils.isEmpty(params.get("userId"))){
            sb.append("and id IN (SELECT c_id FROM con_join  WHERE  u_id=#{userId}) ");
        }
        if(params.containsKey("title")&& !StringUtils.isEmpty(params.get("title"))){
            sb.append("and title like concat('%',#{title},'%') ");
        }
        if(params.containsKey("deptId")&& !StringUtils.isEmpty(params.get("deptId"))){
            sb.append("and dept_id=#{deptId} ");
        }
        if(params.containsKey("sort")&& !StringUtils.isEmpty(params.get("sort"))){
            Integer sort = (Integer) params.get("sort");
            if(sort!=1){//升序
                sb.append(" ORDER BY publish_date ,start_time ");
            }else {//降序
                sb.append(" ORDER BY publish_date desc ,start_time desc ");
            }
        }else {
            sb.append(" ORDER BY publish_date ,start_time ");
        }

        return sb.toString();
    }


    public String insertBatchConferenceUser(@Param("cId") Integer cId, @Param("ids") List<Object> userIds){
        StringBuilder sb = new StringBuilder("INSERT INTO `xiaobiao`.`con_join` (`c_id`, `u_id`, `status`) VALUES  ");
        for (Object userId : userIds) {
            sb.append("(");
            sb.append("#{cId},"+userId+",'0'");
            sb.append("),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}
