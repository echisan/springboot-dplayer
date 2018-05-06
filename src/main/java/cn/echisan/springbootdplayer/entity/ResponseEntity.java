package cn.echisan.springbootdplayer.entity;

import java.util.List;

public class ResponseEntity {
    private Integer code;
    private String msg;
    private List<Object[]> danmaku;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Object[]> getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(List<Object[]> danmaku) {
        this.danmaku = danmaku;
    }
}
