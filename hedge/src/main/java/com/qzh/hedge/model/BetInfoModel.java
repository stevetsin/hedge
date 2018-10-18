package com.qzh.hedge.model;

import lombok.Data;

/**
 * Created by qzh on 2018-9-6.
 */
@Data
public class BetInfoModel {
    //信息Id
    private long infoId;
    //竞赛信息key
    private String infoKey;
    //平台
    private String platform;
    //比赛日期
    private String dateOfMatch;
    //比赛类型
    private String matchType;
    //主
    private String host;
    //客
    private String guest;
    //主胜
    private String win;
    //主负
    private String lose;
    //平
    private String equal;
    //当前时间
    private String currentTime;
    //是否最新的
    private int isNewest;

    private long createdTimeStamp;

    private String info;

    private long invalidTime;

    private long dateOfMatchTime;

    //是否在比赛当中
    private int isInMatch;
}
