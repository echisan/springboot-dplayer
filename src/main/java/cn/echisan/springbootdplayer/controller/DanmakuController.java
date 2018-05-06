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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/dplayer/v2")
public class DanmakuController {

    private static final Logger logger = LoggerFactory.getLogger(DanmakuController.class);

    private static final Long POST_FREQUENT_IP_TIME_OUT = 5L;

    @Autowired
    private IDanmakuService danmakuService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @CrossOrigin
    @GetMapping
    public ResponseEntity getDanmakuList(@RequestParam("id") String id,
                                         @RequestParam(value = "max", required = false, defaultValue = "1000") Integer max) {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            List<DanmakuEntity> danmakuEntityList = danmakuService.listDanmakuById(id, max);
            if (danmakuEntityList != null && danmakuEntityList.size() != 0) {
                responseEntity.setDanmaku(parseDanmakuListToArray(danmakuEntityList));
            } else {
                responseEntity.setDanmaku(new ArrayList<>());
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

    @CrossOrigin
    @PostMapping
    public ResponseEntity postDanmaku(@RequestBody DanmakuEntity de,
                                      HttpServletRequest request) {

        String author = de.getAuthor();
        String color = de.getColor();
        double time = de.getTime();
        String player = de.getPlayer();
        String text = de.getText();
        String type = de.getType();

        logger.info("请求参数 :{}",de);


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
                || isEmpty(text) || isEmpty(type)) {
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
        danmakuEntity.setIpAddress(ip);

        try {
            DanmakuEntity danmaku = danmakuService.saveDanmaku(danmakuEntity);
            responseEntity.setCode(ResponseType.SUCCESS);
            responseEntity.setDanmaku(parseDanmakuListToArray(Collections.singletonList(danmaku)));
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

    /**
     * 将弹幕type转成int
     * @param type 弹幕type
     * @return 弹幕代号
     */
    private int parseTypeToInt(String type){
        if (type.equals("right")){
            return 0;
        }
        if (type.equals("top")){
            return 1;
        }
        if (type.equals("bottom")){
            return 2;
        }
        return 0;
    }

    /**
     * 将弹幕数据包装成dplayer能识别的格式
     * @param danmakuEntities 弹幕列表
     * @return 弹幕列表
     */
    private List<Object[]> parseDanmakuListToArray(List<DanmakuEntity> danmakuEntities){
        List<Object[]> data = new ArrayList<>();
        if (danmakuEntities!=null && danmakuEntities.size()!=0){
            for (DanmakuEntity de : danmakuEntities){
                Object[] danmaku = new Object[]{de.getTime(),parseTypeToInt(de.getType()),de.getColor(),de.getAuthor(),de.getText()};
                data.add(danmaku);
            }
            return data;
        }
        return data;
    }
}