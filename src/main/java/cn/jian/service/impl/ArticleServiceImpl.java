package cn.jian.service.impl;

import cn.jian.entity.*;
import cn.jian.mapper.ArticleMapper;
import cn.jian.mapper.FavoriteMapper;
import cn.jian.mapper.GoodMapper;
import cn.jian.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    GoodMapper goodMapper;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public PageInfo<Map<String, Object>> selectArticleByCondition(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        return new PageInfo(articleMapper.selectArticleByCondition(params));
    }

    @Override
    public PageInfo<Map<String, Object>> selectArticleByConditionAndFavorite(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        return new PageInfo(articleMapper.selectArticleByConditionAndFavorite(params));
    }

    @Override
    public int add(Map<String, Object> params, User user) {
        Article article = new Article();
        if(params.containsKey("title")&&! StringUtils.isEmpty(params.get("title"))){
            article.setTitle(((String) params.get("title")));
        }
        if(params.containsKey("content")&&! StringUtils.isEmpty(params.get("content"))){
            article.setContent((String) params.get("content"));
        }
        article.setBrowseCount(0);
        article.setPublishDate(new Date());
        article.setUserId(user.getId());
        article.setPublishUsername(user.getUsername());
        int i = articleMapper.insertSelective(article);
        return i;
    }

    @Override
    public int good(Integer uid, Integer aid) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectArticlePublishUser() {
        return articleMapper.selectArticlePublishUser();
    }

    @Override
    public void addBrowseCount(Integer id) {
        //搜索查看数
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setBrowseCount(article.getBrowseCount()+1);
        //更新查看数
        int i = articleMapper.updateByPrimaryKey(article);
        System.out.println(i);
    }

    //根据文章id查询点赞用户及人数
    @Override
    public Map<String, Object> selectGoodUsers(Integer aid) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> users = goodMapper.selectUsersByAid(aid);
        int goodCount=users.size();
        map.put("users",users);
        map.put("goodCount",goodCount);
        return map;
    }

    //查询我关注的人中也收藏这篇文章的人
    @Override
    public List<Map<String, Object>> selectUsersFocusFavorite(Integer aid, Integer uid) {
        return favoriteMapper.selectUsersFocusFavorite(aid,uid);
    }

    //查询收藏数
    @Override
    public Integer selectFavoriteCount(Integer aid) {
        return favoriteMapper.selectFavoriteCount(aid);
    }

    //查询我对该文章是否已点赞
    @Override
    public Boolean selectIsGood(Integer aid, Integer uid) {
        Good good = goodMapper.selectByAidAndUid(aid, uid);
        if (good==null){
            return false;
        }else {
            return true;
        }
    }

    //查询我对该文章是否已收藏
    @Override
    public Boolean selectIsFavorite(Integer aid, Integer uid) {
        Favorite favorite = favoriteMapper.selectByAidAndUid(aid, uid);
        if (favorite==null){
            return false;
        }else {
            return true;
        }
    }

    //取消收藏
    @Override
    public Integer cancelFavorite(Integer aid, Integer uid) {
        return favoriteMapper.cancel(aid,uid);
    }

    //取消点赞
    @Override
    public Integer cancelGood(Integer aid, Integer uid) {
        return goodMapper.cancel(aid,uid);
    }
}
