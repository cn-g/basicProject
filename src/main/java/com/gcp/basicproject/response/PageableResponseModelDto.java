package com.gcp.basicproject.response;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
@ApiModel("统一分页数据格式")
public class PageableResponseModelDto<T> extends ResponseModelDto<List<T>> {
    @ApiModelProperty(
            value = "总条数",
            example = "1000"
    )
    private Long total = 0L;
    @ApiModelProperty(
            value = "总页数",
            example = "100"
    )
    private Long totalPages = 0L;
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

    public PageableResponseModelDto<T> total(long total) {
        this.total = total;
        return this;
    }

    public PageableResponseModelDto<T> totalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PageableResponseModelDto<T> page(int page) {
        this.page = page;
        return this;
    }

    public PageableResponseModelDto<T> rows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public Map<String, Object> toItemsMap() {
        Map<String, Object> data = super.toItemsMap();
        data.put("total", this.total);
        return data;
    }

    @Override
    public Map<String, Object> toItemMap() {
        Map<String, Object> data = super.toItemMap();
        data.put("total", this.total);
        return data;
    }

    @Override
    public String toString() {
        return "PageableResponseModel [total=" + this.total + ", totalPages=" + this.totalPages + ", page=" + this.page + ", rows=" + this.rows + "]";
    }

    public PageableResponseModelDto() {
    }

    public Long getTotal() {
        return this.total;
    }

    public Long getTotalPages() {
        return this.totalPages;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getRows() {
        return this.rows;
    }

    public void setTotal(final Long total) {
        this.total = total;
    }

    public void setTotalPages(final Long totalPages) {
        this.totalPages = totalPages;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public void setRows(final Integer rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageableResponseModelDto)) {
            return false;
        } else {
            PageableResponseModelDto<?> other = (PageableResponseModelDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$total = this.getTotal();
                    Object other$total = other.getTotal();
                    if (this$total == null) {
                        if (other$total == null) {
                            break label59;
                        }
                    } else if (this$total.equals(other$total)) {
                        break label59;
                    }

                    return false;
                }

                Object this$totalPages = this.getTotalPages();
                Object other$totalPages = other.getTotalPages();
                if (this$totalPages == null) {
                    if (other$totalPages != null) {
                        return false;
                    }
                } else if (!this$totalPages.equals(other$totalPages)) {
                    return false;
                }

                Object this$page = this.getPage();
                Object other$page = other.getPage();
                if (this$page == null) {
                    if (other$page != null) {
                        return false;
                    }
                } else if (!this$page.equals(other$page)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageableResponseModelDto;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $total = this.getTotal();
        result = result * 59 + ($total == null ? 43 : $total.hashCode());
        Object $totalPages = this.getTotalPages();
        result = result * 59 + ($totalPages == null ? 43 : $totalPages.hashCode());
        Object $page = this.getPage();
        result = result * 59 + ($page == null ? 43 : $page.hashCode());
        Object $rows = this.getRows();
        result = result * 59 + ($rows == null ? 43 : $rows.hashCode());
        return result;
    }
}

