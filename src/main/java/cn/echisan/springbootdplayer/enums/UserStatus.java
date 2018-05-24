package cn.echisan.springbootdplayer.enums;

public enum UserStatus {

    LOCK((byte)1),
    UN_LOCK((byte)0),

    EMAIL_VERIFY((byte)1),
    EMAIL_UN_VERIFY((byte)0),

    PHONE_VERIFY((byte)1),
    PHONE_UN_VERIFY((byte)0);

    Byte status;

    UserStatus(Byte i) {
        this.status = i;
    }

    public Byte getStatus() {
        return status;
    }
}
