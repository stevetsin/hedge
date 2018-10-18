package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Data
@ApiModel("盘口信息查询实体")
public class BetInfoQueryParam {


    @ApiModelProperty(value = "比赛key")
    private String infoKey;

    @ApiModelProperty(value = "比赛盘口列表")
    private List<String> platforms = new ArrayList<>();
}
