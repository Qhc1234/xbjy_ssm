package cn.jian.service;

import cn.jian.entity.Userfocus;
import cn.jian.entity.Userinfo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserfocusService extends IService<Userfocus> {
    int deleteByUidAndFid(Integer uid, Integer fid);

    List<Integer> selectFocus(Integer uid);

    PageInfo<Userinfo> selectUserinfoByConditionFocus(Map<String, Object> params,Integer uid);

    Integer selectFocusCount(Integer id);
}
