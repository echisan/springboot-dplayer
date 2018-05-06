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
        Person person = new Person("dick",24);
//        System.out.println(mongoTemplate.collectionExists(Person.class));


        mongoTemplate.insert(person);

        System.out.println(mongoTemplate.find(new Query(Criteria.where("name").is("dick")), Person.class));
    }


    @Test
    public void redisTemplateTest(){
        Person person = new Person("dick",24);
        Person person1 = new Person("joe",11);
        Person person2 = new Person("gay",12);
        List<Person> people = new ArrayList<>();
        people.add(person);
        people.add(person1);
        people.add(person2);
        redisTemplate.opsForValue().set(RedisKey.DANMAKU_KEY+"1",people);
        System.out.println("people 已存入缓存");

        List<Person> personList = null;
        if (redisTemplate.hasKey(RedisKey.DANMAKU_KEY+"1")){
            System.out.println("==========================");
            System.out.println("从缓存取出");
            System.out.println("==========================");
            personList = (List<Person>) redisTemplate.opsForValue().get(RedisKey.DANMAKU_KEY+"1");
            System.out.println(redisTemplate.opsForValue().get(RedisKey.DANMAKU_KEY+"1"));
            System.out.println("==========================");
        }
    }
}
