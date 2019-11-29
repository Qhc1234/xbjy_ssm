package cn.jian.service.impl;

import cn.jian.entity.Dept;
import cn.jian.mapper.DeptMapper;
import cn.jian.mapper.UserMapper;
import cn.jian.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<Dept> implements DeptService{

    @Autowired
    DeptMapper deptMapper;



    @Override
    public Dept selectByUId(int id) {
        return deptMapper.selectByUId(id);
    }

    @Override
    public List<Map<String, Object>> selectDeptAndUserCount() {
        return deptMapper.selectDeptAndUserCount();
    }
}
