package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by qzh on 2018-10-12.
 */
@Data
@ApiModel(description = "俱乐部查询条件实体")
public class ClubQueryParam {

    @ApiModelProperty(value = "俱乐部Id")
    private long clubId;

    @ApiModelProperty(value = "俱乐部名称")
    private String clubName;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "俱乐部类型  0-足球")
    private int clubType;

    @ApiModelProperty(value = "俱乐部所属联赛类型")
    private long matchTypeId;

}
