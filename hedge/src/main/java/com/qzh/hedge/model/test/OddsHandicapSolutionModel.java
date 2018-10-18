package com.qzh.hedge.model.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qzh on 2018-7-13.
 */
public class OddsHandicapSolutionModel {

    /**
     * 盘口ID
     */
    private int oddsHandicapId;

    /**
     * 盘口名称
     */
    private String oddsHandicapName;

    /**
     * 返还率
     */
    private BigDecimal returnRate;

    /**
     * 盘口限额
     */
    private Double amountLimit;

    /**
     * 盘口赔率列表
     */
    private List<OddsSolutionModel> oddsList =new ArrayList<>();

    /**
     * 盘口总投资额
     */
    private BigDecimal handicapTotalInvestAmount;


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

    public BigDecimal getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(BigDecimal returnRate) {
        this.returnRate = returnRate;
    }

    public List<OddsSolutionModel> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<OddsSolutionModel> oddsList) {
        this.oddsList = oddsList;
    }

    public Double getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(Double amountLimit) {
        this.amountLimit = amountLimit;
    }

    public BigDecimal getHandicapTotalInvestAmount() {
        return handicapTotalInvestAmount;
    }

    public void setHandicapTotalInvestAmount(BigDecimal handicapTotalInvestAmount) {
        this.handicapTotalInvestAmount = handicapTotalInvestAmount;
    }

}
