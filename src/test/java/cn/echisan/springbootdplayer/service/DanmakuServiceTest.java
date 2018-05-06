package cn.echisan.springbootdplayer.service;

import cn.echisan.springbootdplayer.entity.DanmakuEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DanmakuServiceTest {

    @Autowired
    private IDanmakuService danmakuService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void saveDanmaku() throws Exception {
        DanmakuEntity danmakuEntity = new DanmakuEntity();
        danmakuEntity.setPlayer("plentymore");
        danmakuEntity.setAuthor("aaaaa");
        danmakuEntity.setColor("#cccc");
        danmakuEntity.setTime(11.712759);
        danmakuEntity.setType("right");
        danmakuEntity.setText("难受啊飞");

        danmakuService.saveDanmaku(danmakuEntity);
        System.out.println("==========存入一条数据=============");
        System.out.println(danmakuService.listDanmakuById(danmakuEntity.getPlayer(),5));

    }

    @Test
    public void getDanmakuEntity(){

        System.out.println(mongoTemplate.find(new Query(Criteria.where("player").is("plentymore")),DanmakuEntity.class));
    }


    @Test
    public void listDanmakuById() throws Exception {
        List<DanmakuEntity> danmakuEntityList = danmakuService.listDanmakuById("plentymore",5);
        System.out.println(danmakuEntityList);

    }

    @Test
    public void redisTest(){

    }
}