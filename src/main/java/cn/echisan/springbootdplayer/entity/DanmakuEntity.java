package cn.echisan.springbootdplayer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 弹幕实体
 */
public class DanmakuEntity implements Serializable {
    /**
     * id
     */
    @JsonIgnore
    private String id;
    /**
     * 弹幕发送时间
     */
    private String time;
    /**
     * 弹幕正文
     */
    private String text;
    /**
     * 弹幕颜色
     */
    private String color;
    /**
     * 弹幕类型（居中，顶部，滚动等）
     */
    private String type;
    /**
     * 发送弹幕的ip地址
     */
    @JsonIgnore
    private String ipAddress;

    /**
     * 发送弹幕的用户
     */
    private String author;

    /**
     * 弹幕池id
     */
    @JsonIgnore
    private String player;

    /**
     * 请求的域名
     */
    @JsonIgnore
    private String referer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @Override
    public String toString() {
        return "DanmakuEntity{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", author='" + author + '\'' +
                ", player='" + player + '\'' +
                '}';
    }
}
