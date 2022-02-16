package com.gcp.basicproject.response;


import com.gcp.basicproject.util.ParamUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Admin
 */
@ApiModel("返回数据格式")
public class ResponseModelDto<T> implements Serializable {

    private static final long serialVersionUID = 4525431233220956770L;
    @ApiModelProperty("状态码,值为200(默认)时表示没有错误,204表示查询结果为空")
    private int errorCode;
    @ApiModelProperty("提示信息")
    private String message;
    @ApiModelProperty("提示信息 英文")
    private String message_en;
    @ApiModelProperty("返回code,OK表示成功,EMPTY表示查询结果为空")
    private ResponseCode responseCode;
    @ApiModelProperty("需要传递的数据")
    private T data;
    @ApiModelProperty("调用是否成功,返回结果为空或ok都表示调用成功")
    private Boolean success;

    public ResponseModelDto responseCode(ResponseCode responseCode){
        this.responseCode = responseCode;
        this.errorCode = responseCode.getErrorCode();
        this.message = responseCode.getMessage();
        this.message_en = responseCode.getMessage_en();
        return this;
    }

    /**
     *
     * @param data
     * @return
     */
    public ResponseModelDto<T> ok(T data){
        if(ParamUtil.empty(data)){
            return this.noResult();
        }else{
            this.data = data;
            return this.ok();
        }
    }

    /**
     *
     * @param data
     * @return
     */
    public ResponseModelDto<T> ok(T data,Class<T> clazz){
        if(ParamUtil.empty(data)){
            return this.noResult();
        }else{
            this.data = data;
            return this.ok();
        }
    }

    /**
     * 成功提示
     * @return
     */
    public ResponseModelDto<T> ok(){
        this.responseCode(ResponseCode.OK);
        return this;
    }

    /**
     * 无数据提示
     * @return
     */
    public ResponseModelDto<T> noResult() {
        this.responseCode(ResponseCode.EMPTY);
        return this;
    }

    /**
     * 服务异常提示
     * @return
     */
    public ResponseModelDto<T> unkownException() {
        this.responseCode(ResponseCode.UnkownException);
        return this;
    }

    public Map<String, Object> toItemsMap() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("code", this.getErrorCode());
        data.put("msg", this.getMessage());
        data.put("items", this.getData());
        return data;
    }

    public Map<String, Object> toItemMap() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("code", this.getErrorCode());
        data.put("msg", this.getMessage());
        data.put("item", this.getData());
        return data;
    }

    public ResponseModelDto message(String message) {
        this.message = message;
        return this;
    }

    public ResponseModelDto errorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ResponseModelDto message_en(String message_en) {
        this.message_en = message_en;
        return this;
    }

    public ResponseModelDto data(T data) {
        this.data = data;
        return this;
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

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public T getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
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

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
