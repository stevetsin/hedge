package com.qzh.hedge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by qzh on 2018-9-10.
 */
@Data
@ApiModel(description = "符合对冲最优化赔率详情实体")
public class OptimizationModel {

    @ApiModelProperty(value = "比赛关键字")
    private String infoKey;

    @ApiModelProperty(value = "时间")
    private long time;

    @ApiModelProperty(value = "胜所选盘口")
    private String winPlatfrom;

    @ApiModelProperty(value = "胜所选盘口赔率")
    private String winOdds = "0.0";

    @ApiModelProperty(value = "平所选盘口")
    private String equalPlatform ;

    @ApiModelProperty(value = "平所选盘口赔率")
    private String equalOdds = "0.00";

    @ApiModelProperty(value = "负所选盘口")
    private String lossPlatform;

    @ApiModelProperty(value = "负所选盘口赔率")
    private String lossOdds = "0.0";

    @ApiModelProperty(value = "返还率")
    private String returnRateDecimal;
}
