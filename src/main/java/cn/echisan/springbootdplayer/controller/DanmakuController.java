package cn.echisan.springbootdplayer.controller;

import cn.echisan.springbootdplayer.constant.RedisKey;
import cn.echisan.springbootdplayer.constant.ResponseType;
import cn.echisan.springbootdplayer.entity.DanmakuEntity;
import cn.echisan.springbootdplayer.entity.ResponseEntity;
import cn.echisan.springbootdplayer.service.IDanmakuService;
import cn.echisan.springbootdplayer.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/dplayer")
public class DanmakuController {

    private static final Logger logger = LoggerFactory.getLogger(DanmakuController.class);

    private static final Long POST_FREQUENT_IP_TIME_OUT = 5L;

    @Autowired
    private IDanmakuService danmakuService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping
    public ResponseEntity getDanmakuList(@RequestParam("id") String id,
                                         @RequestParam(value = "max", required = false, defaultValue = "1000") Integer max) {

        ResponseEntity responseEntity = new ResponseEntity();
        try {
            List<DanmakuEntity> danmakuEntityList = danmakuService.listDanmakuById(id, max);
            if (danmakuEntityList != null && danmakuEntityList.size() != 0) {
                responseEntity.setData(danmakuEntityList);
            } else {
                responseEntity.setData(new ArrayList<>());
            }
            responseEntity.setCode(ResponseType.SUCCESS);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("数据库出错");
            responseEntity.setCode(ResponseType.DATABASE_ERROR);
            responseEntity.setMsg("数据库出现了点偏差");
            return responseEntity;
        }
    }

    @PostMapping
    public ResponseEntity postDanmaku(@RequestParam("author") String author,
                                      @RequestParam("color") String color,
                                      @RequestParam("player") String player,
                                      @RequestParam("text") String text,
                                      @RequestParam("time") String time,
                                      @RequestParam("token") String token,
                                      @RequestParam("type") String type,
                                      HttpServletRequest request) {

        ResponseEntity responseEntity = new ResponseEntity();

        String ip = GeneralUtils.getIpAddress(request);

        List<String> blackList = GeneralUtils.loadBlackList();
        for (String bl : blackList) {
            if (bl.contains(ip)) {
                logger.info("拒绝ip地址:[ {} ]访问", ip);
                responseEntity.setCode(ResponseType.BLACK_IP);
                responseEntity.setMsg("改ip/域名正处于黑名单,拒绝请求");
                return responseEntity;
            }
        }

        String fequentIpKey = RedisKey.POST_FREQUENT_IP_KEY + ip;
        if (stringRedisTemplate.hasKey(fequentIpKey)) {
            logger.info("ip为 [{}] 访问频繁");
            responseEntity.setCode(ResponseType.FREQUENT_OPERATION);
            responseEntity.setMsg("你发弹幕太快啦!稍后再试试吧!");
            return responseEntity;
        } else {
            stringRedisTemplate.opsForValue().set(fequentIpKey, ip, POST_FREQUENT_IP_TIME_OUT, TimeUnit.SECONDS);
        }

        if (isEmpty(author) || isEmpty(color) || isEmpty(player)
                || isEmpty(text) || isEmpty(time) || isEmpty(type)) {
            responseEntity.setCode(ResponseType.ILLEGAL_DATA);
            responseEntity.setMsg("数据异常");
            return responseEntity;
        }

        // TODO token
        // 有需要的自己进行验证

        DanmakuEntity danmakuEntity = new DanmakuEntity();
        danmakuEntity.setAuthor(GeneralUtils.htmlEncode(author));
        danmakuEntity.setColor(GeneralUtils.htmlEncode(color));
        danmakuEntity.setPlayer(GeneralUtils.htmlEncode(player));
        danmakuEntity.setText(GeneralUtils.htmlEncode(text));
        danmakuEntity.setTime(time);
        danmakuEntity.setType(GeneralUtils.htmlEncode(type));

        try {
            DanmakuEntity de = danmakuService.saveDanmaku(danmakuEntity);
            responseEntity.setCode(ResponseType.SUCCESS);
            responseEntity.setData(Collections.singletonList(de));
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("数据库出现了点偏差");
            responseEntity.setCode(ResponseType.DATABASE_ERROR);
            responseEntity.setMsg("数据库出现了点偏差");
            return responseEntity;
        }
    }

    private boolean isEmpty(String string) {
        return StringUtils.isEmpty(string);
    }
}