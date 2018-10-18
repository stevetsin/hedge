package com.qzh.hedge.model.test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by qzh on 2018-7-16.
 */
public class HedgeResultModel {

    private boolean canDoHedge;

    private List<OddsSolutionModel> optimalOddsList;

    private BigDecimal profitRate;

    private BigDecimal totalProfit;

    private BigDecimal totalInvestment;

    private BigDecimal sumOfOptimalOddsReciprocal;

    public boolean isCanDoHedge() {
        return canDoHedge;
    }

    public void setCanDoHedge(boolean canDoHedge) {
        this.canDoHedge = canDoHedge;
    }

    public List<OddsSolutionModel> getOptimalOddsList() {
        return optimalOddsList;
    }

    public void setOptimalOddsList(List<OddsSolutionModel> optimalOddsList) {
        this.optimalOddsList = optimalOddsList;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public BigDecimal getSumOfOptimalOddsReciprocal() {
        return sumOfOptimalOddsReciprocal;
    }

    public void setSumOfOptimalOddsReciprocal(BigDecimal sumOfOptimalOddsReciprocal) {
        this.sumOfOptimalOddsReciprocal = sumOfOptimalOddsReciprocal;
    }
}
