package cn.jian.controller;

import cn.jian.entity.*;
import cn.jian.service.ArticleService;
import cn.jian.service.FavoriteService;
import cn.jian.service.GoodService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    GoodService goodService;

    @Autowired
    FavoriteService favoriteService;

    @RequestMapping("toList")
    public String toIndex(){
        return "/article_list";
    }
    @RequestMapping("toAdd")
    public String toAdd(){
        return "/article_add";
    }

    @RequestMapping("toDetail")
    public String toDetail(){
        return "/article_detail";
    }

    @RequestMapping("favorite/toList")
    public String toFavoriteList(){
        return "/article_favorite";
    }

    @RequestMapping("findPage")
    @ResponseBody
    public PageInfo<Map<String, Object>> list(@RequestBody Map<String,Object> params){
        if(!params.containsKey("pageNum")|| StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(!params.containsKey("pageSize")|| StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        return articleService.selectArticleByCondition(params);
    }

    @RequestMapping("favorite/findPage")
    @ResponseBody
    public PageInfo<Map<String, Object>> favoriteFindPage(@RequestBody Map<String,Object> params,HttpSession session){
        if(!params.containsKey("pageNum")|| StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(!params.containsKey("pageSize")|| StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        //从session获取uid
        User user = (User) session.getAttribute("user");
        params.put("uid",user.getId());
        return articleService.selectArticleByConditionAndFavorite(params);
    }

    @RequestMapping("findById")
    @ResponseBody
    public Article findById(@RequestParam("id") Integer id){
        return articleService.selectByPrimaryKey(id);
    }

    //发布文章
    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(@RequestBody Map<String,Object> params, HttpSession session){
        Map<String,Object> map =new HashMap<String, Object>();
        //从session获取uid
        User user = (User) session.getAttribute("user");
        int i = articleService.add(params,user);
        if (i>0){
            map.put("flag",true);
            map.put("msg","添加成功");
        }else {
            map.put("flag",false);
            map.put("msg","添加失败");
        }
        return map;
    }


    //点赞
    @RequestMapping("good")
    @ResponseBody
    public Map<String,Object> good(@RequestParam("id") Integer id,@RequestParam("flag") Boolean flag, HttpSession session){
        Map<String,Object> map =new HashMap<String, Object>();
        //从session获取uid
        User user = (User) session.getAttribute("user");
        if (flag){//取消点赞
            Integer integer = articleService.cancelGood(id, user.getId());
            if (integer>0){
                map.put("flag",true);
                map.put("msg","取消点赞成功");
            }else {
                map.put("flag",false);
                map.put("msg","取消点赞失败");
            }
            return map;
        }else {//点赞
            Good good = new Good();
            good.setaId(id);
            good.setuId(user.getId());
            int i = goodService.insertSelective(good);
            if (i>0){
                map.put("flag",true);
                map.put("msg","点赞成功");
            }else {
                map.put("flag",false);
                map.put("msg","点赞失败");
            }
            return map;
        }
    }


    //收藏
    @RequestMapping("favorite")
    @ResponseBody
    public Map<String,Object> favorite(@RequestParam("id") Integer id,@RequestParam("flag") Boolean flag,HttpSession session){
        Map<String,Object> map =new HashMap<String, Object>();
        //从session获取uid
        User user = (User) session.getAttribute("user");
        if (flag){//取消收藏
            Integer integer = articleService.cancelFavorite(id, user.getId());
            if (integer>0){
                map.put("flag",true);
                map.put("msg","取消收藏成功");
            }else {
                map.put("flag",false);
                map.put("msg","取消收藏失败");
            }
            return map;
        }else {//添加收藏
            Favorite favorite = new Favorite();
            favorite.setaId(id);
            favorite.setuId(user.getId());
            int i = favoriteService.insertSelective(favorite);
            if (i>0){
                map.put("flag",true);
                map.put("msg","添加收藏成功");
            }else {
                map.put("flag",false);
                map.put("msg","添加收藏失败");
            }
            return map;
        }
    }

    //查询作者信息
    @RequestMapping("findArticlePublishUser")
    @ResponseBody
    public List<Map<String,Object>> findArticlePublishUser(){
        return articleService.selectArticlePublishUser();
    }

    //查询当前用户名
    @RequestMapping("findPublishUser")
    @ResponseBody
    public User findPublishUser(HttpSession session){
        //从session获取uid
        User user = (User) session.getAttribute("user");
        return user;
    }


    //浏览数数加1
    @RequestMapping("addBrowseCount")
    @ResponseBody
    public String addBrowseCount(@RequestParam("id") Integer id) {
        try {
            articleService.addBrowseCount(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据文章id查询点赞用户及人数
    @RequestMapping("findGoodUsers")
    @ResponseBody
    public Map<String, Object> findGoodUsers(@RequestParam("id") Integer id) {
        return articleService.selectGoodUsers(id);
    }


    //查询我关注的人中也收藏这篇文章的人
    @RequestMapping("findUsersFocusFavorite")
    @ResponseBody
    public List<Map<String, Object>> findUsersFocusFavorite(@RequestParam("id") Integer id,HttpSession session) {
        //从session获取uid
        User user = (User) session.getAttribute("user");
        return articleService.selectUsersFocusFavorite(id,user.getId());
    }

    //查询收藏数
    @RequestMapping("findFavoriteCount")
    @ResponseBody
    public Integer findFavoriteCount(@RequestParam("id") Integer id) {
        return articleService.selectFavoriteCount(id);
    }

    //查询是否已点赞
    @RequestMapping("findIsGood")
    @ResponseBody
    public Boolean findIsGood(@RequestParam("id") Integer id,HttpSession session) {
        //从session获取uid
        User user = (User) session.getAttribute("user");
        return articleService.selectIsGood(id,user.getId());
    }

    //查询是否已收藏
    @RequestMapping("findIsFavorite")
    @ResponseBody
    public Boolean findIsFavorite(@RequestParam("id") Integer id,HttpSession session) {
        //从session获取uid
        User user = (User) session.getAttribute("user");
        return articleService.selectIsFavorite(id,user.getId());
    }
}
