package com.gcp.basicproject.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Admin
 */
@Data
@ApiModel("id入参类")
public class IdRequestDto {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

}
