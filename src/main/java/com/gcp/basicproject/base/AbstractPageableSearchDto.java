package com.gcp.basicproject.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Admin
 */
@ApiModel("统一分页数据格式")
public class AbstractPageableSearchDto {
    private static final long serialVersionUID = 6966525696481796578L;
    @ApiModelProperty(
            value = "当前页",
            notes = "第一页值为1",
            example = "1"
    )
    private Integer page = 1;
    @ApiModelProperty(
            value = "每页显示数量",
            example = "10"
    )
    private Integer rows = 10;
    @ApiModelProperty("排序条件:key为排序字段,value为排序顺序(ASC正序排序 DESC倒序排序),以json格式提交,如:{'create_time':'asc','app_time':'desc'}")
    private String sort;

    public Pageable pageRequestInfo() {
        Sort sort = null;
        if (StringUtils.isNotBlank(this.sort)) {
            JSONObject jsonObject = JSON.parseObject(this.sort);
            List<Order> orderList = (List)jsonObject.entrySet().stream().map((e) -> {
                return e.getValue() != null && e.getValue().toString().equalsIgnoreCase(Direction.DESC.name()) ? new Order(Direction.DESC, (String)e.getKey()) : new Order(Direction.ASC, (String)e.getKey());
            }).collect(Collectors.toList());
            sort = Sort.by(orderList);
        }

        return PageRequest.of((Integer)Optional.ofNullable(this.page).orElse(0), (Integer)Optional.ofNullable(this.rows).orElse(10), sort);
    }

    public <T> Page<T> iPageInfo() {
        Page<T> page = new Page((long)Optional.ofNullable(this.page).orElse(0), (long)Optional.ofNullable(this.rows).orElse(10));
        if (StringUtils.isNotBlank(this.sort) && !"null".equalsIgnoreCase(this.sort)) {
            JSONObject jsonObject = JSON.parseObject(this.sort);
            jsonObject.entrySet().stream().map((e) -> {
                return e.getValue() != null && e.getValue().toString().equalsIgnoreCase(Direction.DESC.name()) ? OrderItem.desc((String)e.getKey()) : OrderItem.asc((String)e.getKey());
            }).forEach((xva$0) -> {
                page.addOrder(new OrderItem[]{xva$0});
            });
        }

        return page;
    }

    public AbstractPageableSearchDto() {
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getRows() {
        return this.rows;
    }

    public String getSort() {
        return this.sort;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AbstractPageableSearchDto)) {
            return false;
        } else {
            AbstractPageableSearchDto other = (AbstractPageableSearchDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$page = this.getPage();
                    Object other$page = other.getPage();
                    if (this$page == null) {
                        if (other$page == null) {
                            break label47;
                        }
                    } else if (this$page.equals(other$page)) {
                        break label47;
                    }

                    return false;
                }

                Object this$rows = this.getRows();
                Object other$rows = other.getRows();
                if (this$rows == null) {
                    if (other$rows != null) {
                        return false;
                    }
                } else if (!this$rows.equals(other$rows)) {
                    return false;
                }

                Object this$sort = this.getSort();
                Object other$sort = other.getSort();
                if (this$sort == null) {
                    if (other$sort != null) {
                        return false;
                    }
                } else if (!this$sort.equals(other$sort)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof AbstractPageableSearchDto;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $page = this.getPage();
//        int result = result * 59 + ($page == null ? 43 : $page.hashCode());
//        Object $rows = this.getRows();
//        result = result * 59 + ($rows == null ? 43 : $rows.hashCode());
//        Object $sort = this.getSort();
//        result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
//        return result;
//    }

    public String toString() {
        return "AbstractPageableSearchDto(page=" + this.getPage() + ", rows=" + this.getRows() + ", sort=" + this.getSort() + ")";
    }
}

