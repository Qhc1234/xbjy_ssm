package cn.jian.controller;

import cn.jian.entity.Article;
import cn.jian.entity.Conference;
import cn.jian.entity.User;
import cn.jian.service.ConferenceService;
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
@RequestMapping("conference")
public class ConferenceController {

    @Autowired
    ConferenceService conferenceService;

    @RequestMapping("toList")
    public String toIndex(){
        return "/conference_list";
    }

    @RequestMapping("toDetail")
    public String toDetail(){
        return "/conference_detail";
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "/conference_add";
    }

    @RequestMapping("findPage")
    @ResponseBody
    public PageInfo<Conference> list(@RequestBody Map<String,Object> params,HttpSession session){
        if(!params.containsKey("pageNum")|| StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(!params.containsKey("pageSize")|| StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        if (params.containsKey("join")&&!StringUtils.isEmpty(params.get("join"))){
            if ((Boolean) params.get("join")){
                //从session获取uid
                User user = (User) session.getAttribute("user");
                params.put("userId",user.getId());
            }

        }
        return conferenceService.selectConferenceByCondition(params);
    }


    @RequestMapping("findById")
    @ResponseBody
    public Conference findById(@RequestParam("id") Integer id){
        return conferenceService.selectByPrimaryKey(id);
    }


    @RequestMapping("findAddDetail")
    @ResponseBody
    public Map<String,Object> selectAddDetail(HttpSession session){
        //通过session获取用户id
        User user = (User) session.getAttribute("user");
        return conferenceService.selectAddDetail(user.getId());
    }

    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(@RequestBody Map<String,Object> params){
        Map<String,Object> map =new HashMap<String, Object>();

        int i = conferenceService.add(params);
        if (i>0){
            map.put("flag",true);
            map.put("msg","添加成功");
        }else {
            map.put("flag",false);
            map.put("msg","添加失败");
        }
        return map;
    }

    //参加会议
    @RequestMapping("join")
    @ResponseBody
    public Map<String,Object> join(@RequestParam("id") Integer id,HttpSession session){
        Map<String,Object> map =new HashMap<String, Object>();

        //从session获取uid
        User user = (User) session.getAttribute("user");

        int i = 0;
        try {
            i = conferenceService.join(user.getId(),id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag",false);
            map.put("msg","参加失败");
        }
        if (i>0){
            map.put("flag",true);
            map.put("msg","参加成功");
        }else {
            map.put("flag",false);
            map.put("msg","参加失败");
        }
        return map;
    }

    //取消会议
    @RequestMapping("cancel")
    @ResponseBody
    public Map<String,Object> cancel(@RequestParam("id") Integer id){
        Map<String,Object> map =new HashMap<String, Object>();

        Conference conference = new Conference();
        conference.setId(id);
        conference.setStatus(2);
        int i = conferenceService.updateByPrimaryKeySelective(conference);
        if (i>0){
            map.put("flag",true);
            map.put("msg","取消成功");
        }else {
            map.put("flag",false);
            map.put("msg","取消失败");
        }
        return map;
    }

    //查询参加会议人员
    @RequestMapping("findUsersByCId")
    @ResponseBody
    public List<Map<String,Object>> findUsersByCId(@RequestParam("id") Integer id){
        List<Map<String, Object>> maps = conferenceService.selectUsersByCId(id);
        return maps;
    }

}
