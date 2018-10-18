package com.qzh.hedge.model;

import lombok.Data;

/**
 * Created by qzh on 2018-9-10.
 */
@Data
public class BetInfoOddsModel {

    private long id;

    private long betInfoId;

    private String oddsName;

    private String oddsValue;
}
