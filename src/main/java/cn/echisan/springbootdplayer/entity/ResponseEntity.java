package cn.echisan.springbootdplayer.entity;

import java.util.List;

public class ResponseEntity {
    private Integer code;
    private String msg;
    private List<DanmakuEntity> data;

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

    public List<DanmakuEntity> getData() {
        return data;
    }

    public void setData(List<DanmakuEntity> data) {
        this.data = data;
    }
}
