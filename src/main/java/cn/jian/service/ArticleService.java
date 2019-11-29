package cn.jian.service;

import cn.jian.entity.Article;
import cn.jian.entity.Conference;
import cn.jian.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ArticleService extends IService<Article> {

    PageInfo<Map<String, Object>> selectArticleByCondition(Map<String, Object> params);

    PageInfo<Map<String, Object>> selectArticleByConditionAndFavorite(Map<String, Object> params);

    int add(Map<String, Object> params, User user);

    int good(Integer uid, Integer aid);

    List<Map<String,Object>> selectArticlePublishUser();

    void addBrowseCount(Integer id);

    Map<String,Object>selectGoodUsers(Integer aid);

    List<Map<String,Object>> selectUsersFocusFavorite(Integer aid,Integer uid);

    Integer selectFavoriteCount(Integer aid);

    Boolean selectIsGood(Integer aid,Integer uid);

    Boolean selectIsFavorite(Integer aid,Integer uid);

    Integer cancelFavorite(Integer aid,Integer uid);

    Integer cancelGood(Integer aid,Integer uid);
}
