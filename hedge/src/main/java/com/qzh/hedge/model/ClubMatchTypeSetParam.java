package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by qzh on 2018-10-12.
 */
@Data
@ApiModel("设置俱乐部关联联赛参数")
public class ClubMatchTypeSetParam {

    @ApiModelProperty(value = "俱乐部Id")
    private  long clubId;

    @ApiModelProperty(value = "关联联赛类别id列表")
    private  List<Long> matchTypeIds;
}
