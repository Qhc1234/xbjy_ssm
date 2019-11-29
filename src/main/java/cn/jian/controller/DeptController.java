package cn.jian.controller;

import cn.jian.entity.Dept;
import cn.jian.entity.User;
import cn.jian.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("dept")
public class DeptController {

    @Autowired
    DeptService deptService;

    @RequestMapping("toList")
    public String toList(){
        return "/dept_list";
    }

    @RequestMapping("selectAll")
    @ResponseBody
    public List<Dept> selectAll(){
        return deptService.selectAll();
    }

    @RequestMapping("selectByUId")
    @ResponseBody
    public Dept selectByUId(HttpSession session){
        //从session中获取用户id
        User user = (User) session.getAttribute("user");
        return deptService.selectByUId(user.getId());
    }

    //查询部门及拥有人数
    @RequestMapping("findDeptAndUserCount")
    @ResponseBody
    List<Map<String,Object>> findDeptAndUserCount(){
        return deptService.selectDeptAndUserCount();
    }

    //新建部门
    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(@RequestParam("name") String name){
        Map<String,Object> map =new HashMap<String, Object>();
        Dept dept = new Dept();
        dept.setName(name);
        int i = deptService.insertSelective(dept);
        if (i>0){
            map.put("flag",true);
            map.put("msg","新建成功");
        }else {
            map.put("flag",false);
            map.put("msg","新建失败");
        }
        return map;
    }

}
