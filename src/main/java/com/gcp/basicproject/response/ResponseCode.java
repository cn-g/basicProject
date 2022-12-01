package com.gcp.basicproject.response;

/**
 * @author Admin
 */
public enum ResponseCode {

    OK(200, "OK", "OK"),
    UnkownException(500, "服务器撸猫去了~~", "Unknow Exception"),
    CommonException(503, "业务异常", "Common Exception"),
    EMPTY(204, "查询结果为空", "NO RESULT"),
    NoAuthor(401, "用户未登录，无法访问受保护资源", "Sorry,you are not login,please login"),
    NOPOWER(403,"权限不足","No Power"),
    LOGINEXCEPTION(402,"认证失败","LOGIN ERROR");

    private int errorCode;

    private String message;

    private String message_en;

    private ResponseCode(int errorCode,String message,String message_en){
        this.errorCode = errorCode;
        this.message = message;
        this.message_en = message_en;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage_en(String message_en) {
        this.message_en = message_en;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage_en() {
        return message_en;
    }

    @Override
    public String toString() {
        return "{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", message_en='" + message_en + '\'' +
                '}';
    }
}
