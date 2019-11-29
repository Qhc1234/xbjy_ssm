package cn.jian.service.impl;

import cn.jian.entity.Good;
import cn.jian.service.GoodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GoodServiceImpl extends ServiceImpl<Good> implements GoodService {
}
