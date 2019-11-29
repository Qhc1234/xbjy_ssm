package cn.jian.service.impl;

import cn.jian.entity.Favorite;
import cn.jian.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FavoriteServiceImpl extends ServiceImpl<Favorite> implements FavoriteService {
}
