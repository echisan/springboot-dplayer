package cn.echisan.springbootdplayer;

import cn.echisan.springbootdplayer.constant.RedisKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDplayerApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Test
    public void contextLoads() {
    }

    @Test
    public void mongoTemplateTest(){

    }


    @Test
    public void redisTemplateTest(){

    }
}
