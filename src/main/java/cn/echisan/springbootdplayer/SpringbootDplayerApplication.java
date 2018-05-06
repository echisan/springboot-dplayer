package cn.echisan.springbootdplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SpringbootDplayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDplayerApplication.class, args);
    }
}
