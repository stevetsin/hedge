//package com.qzh.hedge.model;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import java.io.Serializable;
//
///**
// * Created by qzh on 2018-10-11.
// */
//@ApiModel(
//        value = "PageInfo",
//        description = "网关分页查询信息"
//)
//public class PageInfo implements Serializable {
//    private static final long serialVersionUID = 1868812352562982645L;
//    @ApiModelProperty(
//            value = "排序降序",
//            hidden = true
//    )
//    public static final String ORDER_TYPE_DESC = "desc";
//    @ApiModelProperty(
//            value = "排序升序",
//            hidden = true
//    )
//    public static final String ORDER_TYPE_ASC = "asc";
//    @ApiModelProperty(
//            value = "当前页,第一页默认1",
//            required = true,
//            dataType = "int"
//    )
//    private int currPage = 1;
//    @ApiModelProperty(
//            value = "每页的数量",
//            required = true,
//            dataType = "int"
//    )
//    private int pageSize = 20;
//    @ApiModelProperty(
//            value = "排序字段【,】分隔",
//            allowEmptyValue = true,
//            dataType = "String"
//    )
//    private String orderField = "";
//    @ApiModelProperty(
//            value = "默认降序",
//            allowEmptyValue = true,
//            dataType = "String"
//    )
//    private String orderType = "desc";
//    @ApiModelProperty(
//            value = "排序拼接",
//            hidden = true
//    )
//    private String orderby = "";
//
//    public String getOrderby() {
//        return StringUtils.isNotBlank(this.orderField) && StringUtils.isNotBlank(this.orderType)?this.orderField + " " + this.orderType:"";
//    }
//
//    public int getCurrPage() {
//        return this.currPage == 0?1:this.currPage;
//    }
//
//    public int getPageSize() {
//        return this.pageSize == 0?20:this.pageSize;
//    }
//
//    public PageInfo() {
//    }
//
//    public String getOrderField() {
//        return this.orderField;
//    }
//
//    public String getOrderType() {
//        return this.orderType;
//    }
//
//    public void setCurrPage(int currPage) {
//        this.currPage = currPage;
//    }
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public void setOrderField(String orderField) {
//        this.orderField = orderField;
//    }
//
//    public void setOrderType(String orderType) {
//        this.orderType = orderType;
//    }
//
//    public void setOrderby(String orderby) {
//        this.orderby = orderby;
//    }
//
//    public boolean equals(Object o) {
//        if(o == this) {
//            return true;
//        } else if(!(o instanceof PageInfo)) {
//            return false;
//        } else {
//            PageInfo other = (PageInfo)o;
//            if(!other.canEqual(this)) {
//                return false;
//            } else if(this.getCurrPage() != other.getCurrPage()) {
//                return false;
//            } else if(this.getPageSize() != other.getPageSize()) {
//                return false;
//            } else {
//                label52: {
//                    String this$orderField = this.getOrderField();
//                    String other$orderField = other.getOrderField();
//                    if(this$orderField == null) {
//                        if(other$orderField == null) {
//                            break label52;
//                        }
//                    } else if(this$orderField.equals(other$orderField)) {
//                        break label52;
//                    }
//
//                    return false;
//                }
//
//                String this$orderType = this.getOrderType();
//                String other$orderType = other.getOrderType();
//                if(this$orderType == null) {
//                    if(other$orderType != null) {
//                        return false;
//                    }
//                } else if(!this$orderType.equals(other$orderType)) {
//                    return false;
//                }
//
//                String this$orderby = this.getOrderby();
//                String other$orderby = other.getOrderby();
//                if(this$orderby == null) {
//                    if(other$orderby != null) {
//                        return false;
//                    }
//                } else if(!this$orderby.equals(other$orderby)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }
//
//    protected boolean canEqual(Object other) {
//        return other instanceof PageInfo;
//    }
//
//    public int hashCode() {
//        boolean PRIME = true;
//        byte result = 1;
//        int result1 = result * 59 + this.getCurrPage();
//        result1 = result1 * 59 + this.getPageSize();
//        String $orderField = this.getOrderField();
//        result1 = result1 * 59 + ($orderField == null?43:$orderField.hashCode());
//        String $orderType = this.getOrderType();
//        result1 = result1 * 59 + ($orderType == null?43:$orderType.hashCode());
//        String $orderby = this.getOrderby();
//        result1 = result1 * 59 + ($orderby == null?43:$orderby.hashCode());
//        return result1;
//    }
//
//    public String toString() {
//        return "PageInfo(currPage=" + this.getCurrPage() + ", pageSize=" + this.getPageSize() + ", orderField=" + this.getOrderField() + ", orderType=" + this.getOrderType() + ", orderby=" + this.getOrderby() + ")";
//    }
//}
