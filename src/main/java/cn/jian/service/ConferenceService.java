package cn.jian.service;

import cn.jian.entity.Conference;
import cn.jian.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ConferenceService extends IService<Conference> {

    PageInfo<Conference> selectConferenceByCondition(Map<String, Object> params);

    Map<String,Object> selectAddDetail(Integer uid);

    int add(Map<String, Object> params);

    int join(Integer uid, Integer cid);

    List<Map<String,Object>> selectUsersByCId(Integer id);
}
