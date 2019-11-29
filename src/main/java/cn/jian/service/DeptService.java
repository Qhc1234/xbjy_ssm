package cn.jian.service;

import cn.jian.entity.Dept;

import java.util.List;
import java.util.Map;

public interface DeptService extends IService<Dept> {

    Dept selectByUId(int id);

    List<Map<String,Object>> selectDeptAndUserCount();
}
