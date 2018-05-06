package cn.echisan.springbootdplayer.service;

import cn.echisan.springbootdplayer.entity.DanmakuEntity;

import java.util.List;

public interface IDanmakuService {

    DanmakuEntity saveDanmaku(DanmakuEntity danmakuEntity) throws Exception;
    List<DanmakuEntity> listDanmakuById(String id, Integer max) throws Exception;

}
