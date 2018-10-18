package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by qzh on 2018-10-12.
 */
@Data
@ApiModel(description = "联赛类型实体")
public class MatchTypeModel {

    @ApiModelProperty(value = "联赛类型Id")
    private long matchTypeId;

    @ApiModelProperty(value = "联赛类型名称")
    private String matchTypeName;

    @ApiModelProperty(value = "联赛类型别名，添加别名时候要传该字段")
    private String matchTypeOtherName;

    @ApiModelProperty(value = "联赛类型所有别名")
    private List<String> allMatchTypeOtherNames;



}
