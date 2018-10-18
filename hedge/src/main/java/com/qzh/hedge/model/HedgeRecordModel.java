package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by qzh on 2018-9-11.
 */
@Data
@ApiModel(description = "符合对冲结果实体")
public class HedgeRecordModel {

    private long id;

    @ApiModelProperty(value = "比赛关键字")
    private String betInfoKey;

    @ApiModelProperty(value = "记录时间")
    private long currTime;

    private String hedgeInfo;

    @ApiModelProperty(value = "最优化赔率详情")
    private OptimizationModel optimizationDetail;

    @ApiModelProperty(value = "返还率")
    private String returnRateString;

    private int isNewest;
}
