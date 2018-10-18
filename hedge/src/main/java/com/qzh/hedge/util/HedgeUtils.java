package com.qzh.hedge.util;

import com.qzh.hedge.constant.InvestTypeEnum;
import com.qzh.hedge.model.test.HedgeResultModel;
import com.qzh.hedge.model.test.OddsHandicapSolutionModel;
import com.qzh.hedge.model.test.OddsSolutionModel;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by qzh on 2018-7-13.
 */
public class HedgeUtils{

    //精确位数
    private static  int PRECISE_DIGITS = 12;

    public static OddsHandicapSolutionModel analyseSingleOddsHandicap(OddsHandicapSolutionModel oddsHandicapSolutionModel){
        List<OddsSolutionModel> oddsList = oddsHandicapSolutionModel.getOddsList();
        int oddsHandicapId = oddsHandicapSolutionModel.getOddsHandicapId();
        String oddsHandicapName = oddsHandicapSolutionModel.getOddsHandicapName();
        //返还率
        BigDecimal returnRate = BigDecimal.ZERO;
        BigDecimal returnRateCaculTemp = BigDecimal.ZERO;
        for(OddsSolutionModel odds:oddsList){
            odds.setOddsHandicapId(oddsHandicapId);
            odds.setOddsHandicapName(oddsHandicapName);
            BigDecimal oddsBigDecimal = new BigDecimal(Double.toString(odds.getOddsDoubleValue()));
            //计算赔率倒数
            BigDecimal reciprocalOfOddsDecimal = BigDecimal.ONE.divide(oddsBigDecimal,PRECISE_DIGITS,BigDecimal.ROUND_HALF_UP );
            odds.setReciprocalOfOddsDecimal(reciprocalOfOddsDecimal);
            returnRateCaculTemp = returnRateCaculTemp.add(reciprocalOfOddsDecimal);
        }
        returnRate = BigDecimal.ONE.divide(returnRateCaculTemp,PRECISE_DIGITS,BigDecimal.ROUND_HALF_UP );
        oddsHandicapSolutionModel.setReturnRate(returnRate);
        for(OddsSolutionModel odds:oddsList){
            //计算盘口实际既定概率
            BigDecimal probabilityByBanker = odds.getReciprocalOfOddsDecimal().multiply(returnRate);
            odds.setProbabilityByBanker(probabilityByBanker);
        }
        System.out.println("##########"+JsonUtil.toJsonString(oddsHandicapSolutionModel));
        return oddsHandicapSolutionModel;
    }

    public static HedgeResultModel caculateHedgeInfo(List<OddsHandicapSolutionModel> oddsHandicapSolutionModelList){
        HedgeResultModel hedgeResultModel = new HedgeResultModel();
        boolean candoHedge = false;
        //初始化计算样本
        oddsHandicapSolutionModelList.forEach(oddsHandicapSolutionModel ->analyseSingleOddsHandicap(oddsHandicapSolutionModel));
        Map<Integer,List<OddsSolutionModel>> oddsListMapByKeyOfOddsId = new HashMap<>();
        for(OddsHandicapSolutionModel oddsHandicapSolution:oddsHandicapSolutionModelList){
            List<OddsSolutionModel> oddsSolutionModelList = oddsHandicapSolution.getOddsList();
            for(OddsSolutionModel oddsSolutionModel:oddsSolutionModelList){
                if(oddsListMapByKeyOfOddsId.containsKey(oddsSolutionModel.getOddsId())){
                    oddsListMapByKeyOfOddsId.get(oddsSolutionModel.getOddsId()).add(oddsSolutionModel);
                }else {
                    List<OddsSolutionModel> oddsSolutionModelListOfSameOddsId = new ArrayList<>();
                    oddsSolutionModelListOfSameOddsId.add(oddsSolutionModel);
                    oddsListMapByKeyOfOddsId.put(oddsSolutionModel.getOddsId(),oddsSolutionModelListOfSameOddsId);
                }
            }
        }
        List<OddsSolutionModel> optimalOddsList = new ArrayList<>();
        for(Map.Entry entry:oddsListMapByKeyOfOddsId.entrySet()){
            List<OddsSolutionModel> oddsSolutionModelListOfSameOddsId =( List<OddsSolutionModel>) entry.getValue();
            //将赔率最大的放前面
            oddsSolutionModelListOfSameOddsId.sort(new Comparator<OddsSolutionModel>() {
                @Override
                public int compare(OddsSolutionModel o1, OddsSolutionModel o2) {
                    if(o1.getOddsDoubleValue()>o2.getOddsDoubleValue()){
                        return -1;
                    }else if(o1.getOddsDoubleValue().equals(o2.getOddsDoubleValue()) ){
                        return 0;
                    }else {
                        return 1;
                    }
                }
            });
            optimalOddsList.add(oddsSolutionModelListOfSameOddsId.get(0));
        }
        BigDecimal sum = BigDecimal.ZERO;
        for(OddsSolutionModel oddsSolutionModel:optimalOddsList){
            sum = sum.add(oddsSolutionModel.getReciprocalOfOddsDecimal());
        }

        if(sum.compareTo(BigDecimal.ONE)<0){
            candoHedge = true;
        }
        hedgeResultModel.setCanDoHedge(candoHedge);
        if(candoHedge == true){
            BigDecimal profitRate = (BigDecimal.ONE.subtract(sum)).divide(sum,PRECISE_DIGITS,BigDecimal.ROUND_HALF_UP);
            hedgeResultModel.setOptimalOddsList(optimalOddsList);
            hedgeResultModel.setProfitRate(profitRate);
            hedgeResultModel.setSumOfOptimalOddsReciprocal(sum);
        }
        return hedgeResultModel;
    }


    public static HedgeResultModel findBaseHedgeSolution(int investType,String amount,List<OddsHandicapSolutionModel> oddsHandicapSolutionModelList){
        HedgeResultModel hedgeResultModel = caculateHedgeInfo(oddsHandicapSolutionModelList);
        if(hedgeResultModel.isCanDoHedge()==false){
            return hedgeResultModel;
        }
        Double amountDouble = Double.valueOf(amount);
        BigDecimal amountDecimal = BigDecimal.valueOf(amountDouble);
        List<OddsSolutionModel> oddsSolutionModelList = hedgeResultModel.getOptimalOddsList();
        BigDecimal totalInvestAmount =BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;
        if(InvestTypeEnum.INVEST_TYPE_TOTAL_INVEST_MODEL.getCode()==investType){
            BigDecimal totalGetAmount = amountDecimal.divide(hedgeResultModel.getSumOfOptimalOddsReciprocal());
            totalProfit = totalGetAmount.subtract(amountDecimal);
            totalInvestAmount = amountDecimal;
        }

        if(InvestTypeEnum.INVEST_TYPE_WANNA_PROFIT_MODEL.getCode()==investType){
            BigDecimal profitRate = hedgeResultModel.getProfitRate();
            totalProfit = amountDecimal;
            totalInvestAmount = amountDecimal.divide(profitRate,PRECISE_DIGITS,BigDecimal.ROUND_HALF_UP);
        }
        hedgeResultModel.setTotalProfit(totalProfit);
        hedgeResultModel.setTotalInvestment(totalInvestAmount);
        //计算每个赔率应买的金额
        for(OddsSolutionModel oddsSolutionModel:oddsSolutionModelList){
            BigDecimal properInvestAmount = totalInvestAmount.multiply(oddsSolutionModel.getReciprocalOfOddsDecimal());
            oddsSolutionModel.setProperInvestAmount(properInvestAmount);
        }
        return hedgeResultModel;
    }

    public static void main(String args[]){
        int investType = 2;
        String amount = "100.0";
        List<OddsHandicapSolutionModel> oddsHandicapSolutionModelList = new ArrayList<>();
        OddsHandicapSolutionModel model1 = new OddsHandicapSolutionModel();
        model1.setOddsHandicapId(1);
        model1.setOddsHandicapName("必发");
        List<OddsSolutionModel> oddsList1 = new ArrayList<>();
        OddsSolutionModel oddsSolutionModel1 = new OddsSolutionModel();
        oddsSolutionModel1.setOddsId(1);
        oddsSolutionModel1.setOddsName("主胜");
        oddsSolutionModel1.setOddsDoubleValue(3.1);
        OddsSolutionModel oddsSolutionModel2 = new OddsSolutionModel();
        oddsSolutionModel2.setOddsId(2);
        oddsSolutionModel2.setOddsName("平");
        oddsSolutionModel2.setOddsDoubleValue(3.5);
        OddsSolutionModel oddsSolutionModel3 = new OddsSolutionModel();
        oddsSolutionModel3.setOddsId(3);
        oddsSolutionModel3.setOddsName("客胜");
        oddsSolutionModel3.setOddsDoubleValue(2.3);
        oddsList1.add(oddsSolutionModel1);
        oddsList1.add(oddsSolutionModel2);
        oddsList1.add(oddsSolutionModel3);
        model1.setOddsList(oddsList1);




        OddsHandicapSolutionModel model2= new OddsHandicapSolutionModel();
        model2.setOddsHandicapId(2);
        model2.setOddsHandicapName("马拉松");
        List<OddsSolutionModel> oddsList2 = new ArrayList<>();
        OddsSolutionModel oddsSolutionModel4 = new OddsSolutionModel();
        oddsSolutionModel4.setOddsId(1);
        oddsSolutionModel4.setOddsName("主胜");
        oddsSolutionModel4.setOddsDoubleValue(2.76);
        OddsSolutionModel oddsSolutionModel5 = new OddsSolutionModel();
        oddsSolutionModel5.setOddsId(2);
        oddsSolutionModel5.setOddsName("平");
        oddsSolutionModel5.setOddsDoubleValue(3.65);
        OddsSolutionModel oddsSolutionModel6 = new OddsSolutionModel();
        oddsSolutionModel6.setOddsId(3);
        oddsSolutionModel6.setOddsName("客胜");
        oddsSolutionModel6.setOddsDoubleValue(2.5);
        oddsList2.add(oddsSolutionModel4);
        oddsList2.add(oddsSolutionModel5);
        oddsList2.add(oddsSolutionModel6);
        model2.setOddsList(oddsList2);


        oddsHandicapSolutionModelList.add(model1);
        oddsHandicapSolutionModelList.add(model2);

        HedgeResultModel hedgeResultModel = findBaseHedgeSolution(investType,amount,oddsHandicapSolutionModelList);

        System.out.println("final---------------------------------------------");
        System.out.println(JsonUtil.toJsonString(hedgeResultModel));
    }

}
