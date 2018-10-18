package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by qzh on 2018-10-14.
 */
@Data
@ApiModel(description = "俱乐部新增实体")
public class ClubAddParam {
    @ApiModelProperty(value = "俱乐部名称")
    private String clubName;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "俱乐部类型  0-足球")
    private int clubType;
}
