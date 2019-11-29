package cn.jian.mapper;

import cn.jian.entity.Article;
import cn.jian.entity.ArticleExample;
import java.util.List;
import java.util.Map;

import cn.jian.entity.Conference;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleMapper extends Mapper<Article> {

    @SelectProvider(type=ArticleProvider.class,method = "selectArticleByCondition")
    List<Map<String,Object>> selectArticleByCondition(Map<String,Object> params);
    
    @Select("SELECT " +
            "a.publish_username, " +
            "a.user_id " +
            "FROM " +
            "article a " +
            "GROUP BY a.user_id")
    List<Map<String,Object>> selectArticlePublishUser();


    @SelectProvider(type=ArticleProvider.class,method = "selectArticleByConditionAndFavorite")
    List<Map<String,Object>> selectArticleByConditionAndFavorite(Map<String,Object> params);
}