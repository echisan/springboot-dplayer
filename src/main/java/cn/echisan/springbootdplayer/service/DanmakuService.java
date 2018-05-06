package cn.echisan.springbootdplayer.service;

import cn.echisan.springbootdplayer.constant.RedisKey;
import cn.echisan.springbootdplayer.entity.DanmakuEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DanmakuService implements IDanmakuService {

    private static final Logger logger = LoggerFactory.getLogger(DanmakuService.class);

    /**
     * key过期时间 3小时
     */
    private static final Long KEY_TIME_OUT = 3L;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public DanmakuEntity saveDanmaku(DanmakuEntity danmakuEntity) throws Exception {
        mongoTemplate.insert(danmakuEntity);
        logger.info("插入数据库后的实体: [ {} ]", danmakuEntity);
        String danmakuIdKey = RedisKey.DANMAKU_KEY + danmakuEntity.getPlayer();
        if (redisTemplate.hasKey(danmakuIdKey)) {
            redisTemplate.delete(danmakuIdKey);
        }
        return danmakuEntity;
    }

    @Override
    public List<DanmakuEntity> listDanmakuById(String id, Integer max) throws Exception {
        List<DanmakuEntity> danmakuEntityList = null;
        String danmakuIdKey = RedisKey.DANMAKU_KEY + id;
        logger.info("redis danmake_id_key is [{}] and limit [{}]", id, max);
        if (redisTemplate.hasKey(danmakuIdKey)) {
            danmakuEntityList = (List<DanmakuEntity>) redisTemplate.opsForValue().get(danmakuIdKey);
        } else {
            danmakuEntityList = mongoTemplate.find(new Query(Criteria.where("player").is(id)), DanmakuEntity.class);
            redisTemplate.opsForValue().set(danmakuIdKey, danmakuEntityList, KEY_TIME_OUT, TimeUnit.HOURS);
        }

        if (danmakuEntityList.size() > max && danmakuEntityList.size() != 0) {
            danmakuEntityList.subList(0, max);
        }
        return danmakuEntityList;
    }

}
