package com.qzh.hedge.constant;

/**
 * Created by qzh on 2018-7-16.
 */
public enum InvestTypeEnum {

    INVEST_TYPE_TOTAL_INVEST_MODEL(1,"投资类型--总投资额固定"),

    INVEST_TYPE_WANNA_PROFIT_MODEL(2,"投资类型--期望收益固定");

    private int code;
    private String description;

    InvestTypeEnum(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
