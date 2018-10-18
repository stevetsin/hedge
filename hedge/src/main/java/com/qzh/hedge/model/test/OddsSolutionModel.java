package com.qzh.hedge.model.test;

import java.math.BigDecimal;

/**
 * Created by qzh on 2018-7-13.
 */
public class OddsSolutionModel {

    /**
     * 盘口ID
     */
    private int oddsHandicapId;

    /**
     * 盘口名称
     */
    private String oddsHandicapName;

    /**
     * 赔率编号
     */
    private int oddsId;

    /**
     * 赔率名称
     */
    private String oddsName;

    /**
     * 赔率double类型值
     */
    private Double oddsDoubleValue;

    /**
     * 赔率倒数
     */
    private BigDecimal reciprocalOfOddsDecimal;

    /**
     * 庄家设置概率
     */
    private BigDecimal probabilityByBanker;

    /**
     * 应该投资的金额
     */
    private BigDecimal properInvestAmount;

    public int getOddsHandicapId() {
        return oddsHandicapId;
    }

    public void setOddsHandicapId(int oddsHandicapId) {
        this.oddsHandicapId = oddsHandicapId;
    }

    public String getOddsHandicapName() {
        return oddsHandicapName;
    }

    public void setOddsHandicapName(String oddsHandicapName) {
        this.oddsHandicapName = oddsHandicapName;
    }

    public int getOddsId() {
        return oddsId;
    }

    public void setOddsId(int oddsId) {
        this.oddsId = oddsId;
    }

    public String getOddsName() {
        return oddsName;
    }

    public void setOddsName(String oddsName) {
        this.oddsName = oddsName;
    }

    public Double getOddsDoubleValue() {
        return oddsDoubleValue;
    }

    public void setOddsDoubleValue(Double oddsDoubleValue) {
        this.oddsDoubleValue = oddsDoubleValue;
    }

    public BigDecimal getReciprocalOfOddsDecimal() {
        return reciprocalOfOddsDecimal;
    }

    public void setReciprocalOfOddsDecimal(BigDecimal reciprocalOfOddsDecimal) {
        this.reciprocalOfOddsDecimal = reciprocalOfOddsDecimal;
    }

    public BigDecimal getProbabilityByBanker() {
        return probabilityByBanker;
    }

    public void setProbabilityByBanker(BigDecimal probabilityByBanker) {
        this.probabilityByBanker = probabilityByBanker;
    }

    public BigDecimal getProperInvestAmount() {
        return properInvestAmount;
    }

    public void setProperInvestAmount(BigDecimal properInvestAmount) {
        this.properInvestAmount = properInvestAmount;
    }
}
