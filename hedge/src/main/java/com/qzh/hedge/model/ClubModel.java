package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Data
@ApiModel(description = "俱乐部实体")
public class ClubModel {

    @ApiModelProperty(value = "俱乐部Id")
    private long clubId;

    @ApiModelProperty(value = "俱乐部名称")
    private String clubName;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "俱乐部类型  0-足球")
    private int clubType;

    @ApiModelProperty(value = "俱乐部别名")
    private String clubOtherName;

    @ApiModelProperty(value = "俱乐部全部别名列表")
    private List<String> allClubOtherNames;

    @ApiModelProperty(value = "俱乐部所参与联赛类别列表")
    private List<MatchTypeModel> clubRelatedMatchTypes;

}
