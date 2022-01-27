package com.gcp.basicproject.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Admin
 */
@Data
@ApiModel(value = "登录入参")
public class LoginReqDto {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
