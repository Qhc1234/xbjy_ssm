package cn.jian.service.impl;

import cn.jian.entity.Userfocus;
import cn.jian.entity.Userinfo;
import cn.jian.mapper.UserfocusMapper;
import cn.jian.service.UserfocusService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserfocusServiceImpl extends ServiceImpl<Userfocus> implements UserfocusService {

    @Autowired
    UserfocusMapper userfocusMapper;

    @Override
    public int deleteByUidAndFid(Integer uid, Integer fid) {
        return userfocusMapper.deleteByUidAndFid(uid,fid);
    }

    @Override
    public List<Integer> selectFocus(Integer uid) {
        return userfocusMapper.selectFocus(uid);
    }

    @Override
    public PageInfo<Userinfo> selectUserinfoByConditionFocus(Map<String, Object> params, Integer uid) {
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        return new PageInfo(userfocusMapper.selectUserinfoByConditionFocus(params,uid));
    }

    @Override
    public Integer selectFocusCount(Integer id) {
        return userfocusMapper.selectFocusCount(id);
    }


}
