package cn.jian.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.Map;

public class ArticleProvider {
    public String selectArticleByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder("SELECT " +
                "a.id, " +
                "a.title, " +
                "a.content, " +
                "a.browse_count, " +
                "a.publish_date, " +
                "a.publish_username, " +
                "a.user_id, " +
                "count(b.u_id) favoriteCount " +
                "FROM " +
                "article a " +
                "LEFT JOIN favorite b " +
                "ON b.a_id=a.id " +
                "where 1=1 ");

        if(params.containsKey("userId")&& !StringUtils.isEmpty(params.get("userId"))){
            sb.append("and user_id=#{userId} ");
        }
        if(params.containsKey("title")&& !StringUtils.isEmpty(params.get("title"))){
            sb.append("and title like concat('%',#{title},'%') ");
        }

        sb.append("GROUP BY a.id ");

        if(params.containsKey("type")&& !StringUtils.isEmpty(params.get("type"))){
            if(params.get("type").equals("1")){//按浏览数
                if(params.containsKey("typeSort")&& !StringUtils.isEmpty(params.get("typeSort"))){
                    if(params.get("typeSort").equals(1)) {//降序
                        sb.append(" ORDER BY a.browse_count desc, ");
                    }else {
                        sb.append(" ORDER BY a.browse_count , ");
                    }
                }else {
                    sb.append(" ORDER BY a.browse_count , ");
                }
            }else{//按收藏数
                if(params.containsKey("typeSort")&& !StringUtils.isEmpty(params.get("typeSort"))){
                    if(params.get("typeSort").equals(1)) {//降序
                        sb.append(" ORDER BY favoriteCount desc, ");
                    }else {
                        sb.append(" ORDER BY favoriteCount , ");
                    }
                }else {
                    sb.append(" ORDER BY favoriteCount , ");
                }
            }
        }else{
            sb.append(" ORDER BY  ");
        }
        if(params.containsKey("timeSort")&& !StringUtils.isEmpty(params.get("timeSort"))){
            Integer sort = (Integer) params.get("timeSort");
            if(sort!=1){//升序
                sb.append(" a.publish_date ");
            }else {//降序
                sb.append(" a.publish_date desc ");
            }
        }else {
            sb.append(" publish_date ");
        }

        return sb.toString();
    }


    //我的收藏条件查询
    public String selectArticleByConditionAndFavorite(Map<String,Object> params){
        StringBuilder sb = new StringBuilder("SELECT " +
                "a.id, " +
                "a.title, " +
                "a.content, " +
                "a.browse_count, " +
                "a.publish_date, " +
                "a.publish_username, " +
                "a.user_id, " +
                "count(b.u_id) favoriteCount " +
                "FROM " +
                "article a " +
                "LEFT JOIN favorite b " +
                "ON b.a_id=a.id " +
                "where b.u_id=#{uid} ");

        if(params.containsKey("userId")&& !StringUtils.isEmpty(params.get("userId"))){
            sb.append("and user_id=#{userId} ");
        }
        if(params.containsKey("title")&& !StringUtils.isEmpty(params.get("title"))){
            sb.append("and title like concat('%',#{title},'%') ");
        }

        sb.append("GROUP BY a.id ");

        if(params.containsKey("type")&& !StringUtils.isEmpty(params.get("type"))){
            if(params.get("type").equals("1")){//按浏览数
                if(params.containsKey("typeSort")&& !StringUtils.isEmpty(params.get("typeSort"))){
                    if(params.get("typeSort").equals(1)) {//降序
                        sb.append(" ORDER BY a.browse_count desc, ");
                    }else {
                        sb.append(" ORDER BY a.browse_count , ");
                    }
                }else {
                    sb.append(" ORDER BY a.browse_count , ");
                }
            }else{//按收藏数
                if(params.containsKey("typeSort")&& !StringUtils.isEmpty(params.get("typeSort"))){
                    if(params.get("typeSort").equals(1)) {//降序
                        sb.append(" ORDER BY favoriteCount desc, ");
                    }else {
                        sb.append(" ORDER BY favoriteCount , ");
                    }
                }else {
                    sb.append(" ORDER BY favoriteCount , ");
                }
            }
        }else{
            sb.append(" ORDER BY  ");
        }
        if(params.containsKey("timeSort")&& !StringUtils.isEmpty(params.get("timeSort"))){
            Integer sort = (Integer) params.get("timeSort");
            if(sort!=1){//升序
                sb.append(" a.publish_date ");
            }else {//降序
                sb.append(" a.publish_date desc ");
            }
        }else {
            sb.append(" publish_date ");
        }

        return sb.toString();
    }
}
