package cn.jian.service.impl;

import cn.jian.entity.Conference;
import cn.jian.entity.Dept;
import cn.jian.entity.User;
import cn.jian.mapper.ConJoinMapper;
import cn.jian.mapper.ConferenceMapper;
import cn.jian.mapper.DeptMapper;
import cn.jian.mapper.UserMapper;
import cn.jian.service.ConferenceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ConferenceServiceImpl extends ServiceImpl<Conference> implements ConferenceService {

    @Autowired
    ConferenceMapper conferenceMapper;

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ConJoinMapper conJoinMapper;

    @Override
    public PageInfo<Conference> selectConferenceByCondition(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        return new PageInfo(conferenceMapper.selectConferenceByCondition(params));
    }


    @Override
    public Map<String, Object> selectAddDetail(Integer uid) {

        Map<String, Object> map = null;

        try {
            Dept dept = deptMapper.selectByUId(uid);
            List<User> users = userMapper.selectByDeptId(dept.getId());

            map = new HashMap<String, Object>();
            map.put("dept",dept);
            map.put("users",users);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    @Override
    public int add(Map<String, Object> params) {
        Conference conference = new Conference();
        if(params.containsKey("deptId")&&! StringUtils.isEmpty(params.get("deptId"))){
            conference.setDeptId(((Integer) params.get("deptId")));
        }
        if(params.containsKey("title")&&! StringUtils.isEmpty(params.get("title"))){
            conference.setTitle((String) params.get("title"));
        }
        if(params.containsKey("content")&&! StringUtils.isEmpty(params.get("content"))){
            conference.setContent((String) params.get("content"));
        }
        if(params.containsKey("publishDate")&&! StringUtils.isEmpty(params.get("publishDate"))){
            String  publishDate = (String) params.get("publishDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                conference.setPublishDate(sdf.parse(publishDate));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
        if(params.containsKey("startTime")&&! StringUtils.isEmpty(params.get("startTime"))){
            String  startTime = (String) params.get("startTime");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                conference.setStartTime(sdf.parse(startTime));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
        conference.setStatus(0);

        //获取部门名称
        Dept dept = deptMapper.selectByPrimaryKey(conference.getDeptId());
        conference.setDeptName(dept.getName());
        //添加会议
        try {
            conferenceMapper.insertConference(conference);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        //添加相关抄送人
        if(params.containsKey("userIds")&&! StringUtils.isEmpty(params.get("userIds"))){
            ArrayList<Object> userIds = (ArrayList<Object>) params.get("userIds");
            if (userIds.size()>0){
                conferenceMapper.insertBatchConferenceUser(conference.getId(), userIds);
            }
        }

        return 1;
    }

    @Override
    public int join(Integer uid, Integer cid) {

        return conJoinMapper.join(uid, cid);
    }

    @Override
    public List<Map<String, Object>> selectUsersByCId(Integer id) {
        return conJoinMapper.selectUsersByCId(id);
    }
}
