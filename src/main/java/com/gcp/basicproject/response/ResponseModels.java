package com.gcp.basicproject.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.domain.Page;

/**
 * @author Admin
 */
public class ResponseModels {

    public ResponseModels(){

    }

    /**
     * 创建新对象
     * @return
     */
    public static ResponseModelDto newInstance(){
        ResponseModelDto responseMpdelDto = new ResponseModelDto();
        return responseMpdelDto;
    }

    /**
     * 返回对象中的内容
     * @param responseCode
     * @return
     */
    public static ResponseModelDto newInstance(ResponseCode responseCode){
        return newInstance().responseCode(responseCode);
    }

    /**
     * 实体出参封装
     * @param date
     * @param <T>
     * @return
     */
    public static <T> ResponseModelDto<T> ok(T date){
        return newInstance().ok(date);
    }

    public static <T> ResponseModelDto<T> ok(T date,Class<T> clazz){
        return newInstance().ok(date,clazz);
    }

    /**
     * 无参数返回封装
     * @return
     */
    public static ResponseModelDto ok(){
        return newInstance().ok();
    }

    /**
     * 业务异常封装
     * @param <T>
     * @return
     */
    public static <T> ResponseModelDto<T> commonnException() {
        ResponseModelDto<T> responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.CommonException);
        return responseModel;
    }

    /**
     * 业务异常信息封装
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseModelDto<T> commonException(String message) {
        ResponseModelDto<T> responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.CommonException).message(message);
        return responseModel;
    }

    public static <T> ResponseModelDto<T> commonException(CommonException commonException) {
        ResponseModelDto<T> responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.CommonException).message_en(commonException.getMessage_en()).message(commonException.getMessage()).errorCode(commonException.getCode()).data(commonException.getData());
        return responseModel;
    }

    public static ResponseModelDto unkownException() {
        ResponseModelDto responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.UnkownException);
        return responseModel;
    }

    /**
     * 登录异常
     * @return
     */
    public static ResponseModelDto loginException(){
        ResponseModelDto responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.LOGINEXCEPTION);
        return responseModel;
    }

    /**
     * 未登录
     * @return
     */
    public static ResponseModelDto noLoginException(){
        ResponseModelDto responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.NoAuthor);
        return responseModel;
    }

    /**
     * 无权限
     * @return
     */
    public static ResponseModelDto noPowerException(){
        ResponseModelDto responseModel = new ResponseModelDto();
        responseModel.responseCode(ResponseCode.NOPOWER);
        return responseModel;
    }

    public static <T> PageableResponseModelDto<T> page2ResponseModel(Page<T> page) {
        PageableResponseModelDto<T> responseModel = new PageableResponseModelDto();
        if (page.getContent() != null && page.getContent().size() != 0) {
            responseModel.ok(page.getContent());
        } else {
            responseModel.responseCode(ResponseCode.EMPTY);
        }

        responseModel.total(page.getTotalElements()).page(page.getNumber() + 1).rows(page.getSize()).totalPages((long)page.getTotalPages());
        return responseModel;
    }

    public static <T> PageableResponseModelDto<T> page2ResponseModel(IPage<T> page) {
        PageableResponseModelDto<T> responseModel = new PageableResponseModelDto();
        if (page.getRecords() != null && page.getRecords().size() != 0) {
            responseModel.ok(page.getRecords());
        } else {
            responseModel.responseCode(ResponseCode.EMPTY);
        }

        responseModel.total(page.getTotal()).page(Long.valueOf(page.getCurrent()).intValue()).rows(Long.valueOf(page.getSize()).intValue()).totalPages(page.getPages());
        return responseModel;
    }

}
