package com.gcp.basicproject.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author Admin
 */
@Data
@ApiModel("分页id入参类")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class PageIdReqDto extends AbstractPageableSearchDto{

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

}
