package com.qzh.hedge;


import com.qzh.hedge.util.JsonUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qzh on 2018-7-12.
 * 写出除了自己谁也看不懂的代码
 * 秦志华
 */
public class HedgeUtil {

    public static Map<String,Object> caculProbability4List(List<Double> oddsList){
        Map<String,Object> res = new LinkedHashMap<>();
        List<BigDecimal> numericalOddsList = new ArrayList<>();
        List<BigDecimal> probabilityList = new ArrayList<>();
        BigDecimal returnRate = BigDecimal.ZERO;
        BigDecimal returnRateTemp = BigDecimal.ZERO;
        BigDecimal rebateRatio = BigDecimal.ZERO;
        for(Double odds:oddsList){
            BigDecimal oddsBigDecimal = new BigDecimal(Double.toString(odds));
            numericalOddsList.add(oddsBigDecimal);
            BigDecimal temp = BigDecimal.ONE.divide(oddsBigDecimal,12,BigDecimal.ROUND_HALF_UP );
            returnRateTemp = returnRateTemp.add(temp);
        }
        rebateRatio =(BigDecimal.ONE.subtract(returnRateTemp)).divide(returnRateTemp,12,BigDecimal.ROUND_HALF_UP);
        returnRate = BigDecimal.ONE.divide(returnRateTemp,12,BigDecimal.ROUND_HALF_UP );
        for(BigDecimal oddsBigDecimal:numericalOddsList){
            BigDecimal probability = returnRate.divide(oddsBigDecimal,12,BigDecimal.ROUND_HALF_UP);
            probabilityList.add(probability);
        }

        res.put("returnRate",returnRate);
        res.put("probabilityList",probabilityList);
        res.put("rebateRatio",rebateRatio);
        return  res;
    }


    public static Map<String,Object> findBestSolutions(List<String> inputStringParamList,long wantProfit){
        Map<String,Object> resMap = new LinkedHashMap<>();
        BigDecimal rebateRatioFinal = BigDecimal.ZERO;
        List<Integer> finalIndexForBestSolutions = new ArrayList<>();
        List<List<Double>> multipleOddsList = new ArrayList<>();
        for(String stringParam:inputStringParamList){
            String[] params = stringParam.split(",");
            List<Double> doubleParams = new ArrayList<>();
            for(String odds:params){
                doubleParams.add(Double.valueOf(odds));
            }
            multipleOddsList.add(doubleParams);
        }

        for(int i = 0;i<multipleOddsList.size();i++){
            List<Double> oddsListOfI = multipleOddsList.get(i);
            for(int j = i+1;j<multipleOddsList.size();j++){
                List<Double> oddsListOfJ = multipleOddsList.get(j);
                Map<String,Object> calcuPotentialOfOddsListIandJ = calcuPotentialOfOddsListIandJ(oddsListOfI,oddsListOfJ);
                BigDecimal potentialRebateRatio = (BigDecimal) calcuPotentialOfOddsListIandJ.get("potentialRebateRatio");
                List<Integer> potentialIndexListOfI = (List<Integer>) calcuPotentialOfOddsListIandJ.get("potentialIndexListOfI");
                if(potentialRebateRatio!=null && potentialRebateRatio.compareTo(rebateRatioFinal)>0 && potentialIndexListOfI.size()>0){
                    rebateRatioFinal = potentialRebateRatio;
                    finalIndexForBestSolutions.clear();
                    finalIndexForBestSolutions.add(i);
                    finalIndexForBestSolutions.add(j);
                    finalIndexForBestSolutions.addAll(potentialIndexListOfI);
                }
            }
        }
        BigDecimal totalCapital = null;
        if(rebateRatioFinal!=null && rebateRatioFinal.compareTo(BigDecimal.ZERO)!=0) {
            BigDecimal wantProfitDecimal = BigDecimal.valueOf(wantProfit);
            totalCapital = wantProfitDecimal.divide(rebateRatioFinal,12,BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal expectedTotalIncome = totalCapital.add(BigDecimal.valueOf(wantProfit));

        List<Double> finalOddsSolutions = new ArrayList<>();
        if(finalIndexForBestSolutions!=null && finalIndexForBestSolutions.size()>2){
            int indexI = finalIndexForBestSolutions.get(0);
            int indexJ = finalIndexForBestSolutions.get(1);
            List<Double> oddsListI = multipleOddsList.get(indexI);
            List<Double> oddsListJ = multipleOddsList.get(indexJ);
            if(finalIndexForBestSolutions.size()==3){
                int index1OfI = finalIndexForBestSolutions.get(2);
                finalOddsSolutions.add(oddsListI.get(index1OfI));
                for(int i=0;(i<3) &&(i!=index1OfI) ;i++){
                    finalOddsSolutions.add(oddsListJ.get(i));
                }
            }else if(finalIndexForBestSolutions.size()==4){
                int index1OfI = finalIndexForBestSolutions.get(2);
                int index2OfI = finalIndexForBestSolutions.get(3);
                int index1OfJ = 0;
                for(int i=0;i<3;i++){
                    if(i!=index1OfI && i!=index2OfI){
                        index1OfJ=i;
                    }
                }
                finalOddsSolutions.add(oddsListJ.get(index1OfJ));
                finalOddsSolutions.add(oddsListI.get(index1OfI));
                finalOddsSolutions.add(oddsListI.get(index2OfI));
            }
        }
        Map<Double,BigDecimal> moneyPartion = new LinkedHashMap<>();
        if(finalOddsSolutions!=null && finalOddsSolutions.size()>0){
            for(Double odds:finalOddsSolutions){

                BigDecimal money = expectedTotalIncome.divide(BigDecimal.valueOf(odds),12,BigDecimal.ROUND_HALF_UP);
                moneyPartion.put(odds,money);
            }
        }
        resMap.put("finalOddsSolutions",finalOddsSolutions);
        resMap.put("totalCapital",totalCapital);
        resMap.put("rebateRatioFinal",rebateRatioFinal);
        resMap.put("finalIndexForBestSolutions",finalIndexForBestSolutions);
        resMap.put("moneyPartion",moneyPartion);
        return resMap;
    }


    public static Map<String,Object> calcuPotentialOfOddsListIandJ(List<Double> oddsListOfI,List<Double> oddsListOfJ){
        Map<String,Object> resMap = new LinkedHashMap<>();
        BigDecimal rebateRatioTemp = BigDecimal.ZERO;
        List<Integer> indexListOfI = new ArrayList<>();
        for(int a =0;a<3;a++){
            List<Double> finalCombination = new ArrayList<>();
            Double aOfI = oddsListOfI.get(a);
            finalCombination.add(aOfI);
            for(int b =0;b<3;b++){
                if(b==a)continue;
                finalCombination.add(oddsListOfJ.get(b));
            }
            Map<String,Object> combinationRes = caculProbability4List(finalCombination);
            BigDecimal rebateRatio =(BigDecimal) combinationRes.get("rebateRatio");
            if(rebateRatio !=null &&rebateRatio.compareTo(BigDecimal.ZERO)>0 && rebateRatio.compareTo(rebateRatioTemp)> 0){
                rebateRatioTemp = rebateRatio;
                indexListOfI.clear();
                indexListOfI.add(a);
            }
        }

        for(int b =0;b<3;b++){
            List<Double> finalCombination = new ArrayList<>();
            Double bOfJ = oddsListOfJ.get(b);
            finalCombination.add(bOfJ);
            List<Integer> indexOfI = new ArrayList<>();
            for(int a =0;a<3;a++){
                if(a==b)continue;
                finalCombination.add(oddsListOfI.get(a));
                indexOfI.add(a);
            }
            Map<String,Object> combinationRes = caculProbability4List(finalCombination);
            BigDecimal rebateRatio =(BigDecimal) combinationRes.get("rebateRatio");
            if(rebateRatio.compareTo(BigDecimal.ZERO)>0 && rebateRatio.compareTo(rebateRatioTemp)> 0){
                rebateRatioTemp = rebateRatio;
                indexListOfI.clear();
                indexListOfI.addAll(indexOfI);
            }
        }
        resMap.put("potentialRebateRatio",rebateRatioTemp);
        resMap.put("potentialIndexListOfI",indexListOfI);
        return resMap;
    }


    public static void main(String args[]){
//        List<String> inputParamList = new ArrayList<>();
//        inputParamList.add("2.0,3.25,4.5");
//        inputParamList.add("1.98,3.33,4.65");
//        Map hah = findBestSolutions(inputParamList,100L);

        List<Double> array = new ArrayList<>();
        array.add(5.42);
        array.add(3.82);
        array.add(2.9);

//        2  3.5  4      1.95  3.5   3.8

        System.out.println(JsonUtil.toJsonString(caculProbability4List(array)));
    }

}
