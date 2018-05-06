package cn.echisan.springbootdplayer;

import com.mongodb.MongoClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoApp {

    private static final Log log = LogFactory.getLog(MongoApp.class);

    public static void main(String[] args) {
        MongoOperations mongoOps = new MongoTemplate(new MongoClient("localhost",27017), "database");
        mongoOps.insert(new Person("Joe", 34));
        log.info(mongoOps.findOne(new Query(Criteria.where("name").is("joe")),Person.class));
        List<Person> personList = mongoOps.findAll(Person.class);
        log.info(personList.toString());

        mongoOps.dropCollection("person");
    }
}
