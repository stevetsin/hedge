package com.qzh.hedge.controller;

import com.qzh.hedge.model.BetInfoModel;
import com.qzh.hedge.model.OptimizationModel;
import com.qzh.hedge.service.BetInfoService;
import com.qzh.hedge.service.HedgeRecordService;
import com.qzh.hedge.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by qzh on 2018-9-13.
 */
@Component
public class HedgeHelper {
    @Autowired
    private BetInfoService betInfoService;

    @Autowired
    private HedgeRecordService hedgeRecordService;



    public  OptimizationModel getOptimization(String key, List<BetInfoModel> betInfosOfKey){
//        Map<Integer,BetInfoModel> betInfoModelMapByIsInMatch = betInfosOfKey.stream().collect(Collectors.groupingBy(BetInfoModel::getIsInMatch));
        OptimizationModel optimizationModel = new OptimizationModel();
        optimizationModel.setInfoKey(key);
        for(BetInfoModel betInfoModel:betInfosOfKey){
            String info = betInfoModel.getInfo();
            BetInfoModel betInfoDetail = JsonUtil.jsonToObject(info,BetInfoModel.class);
            String winOddsString = betInfoDetail.getWin();
            String equalOddsString = betInfoDetail.getEqual();
            String lossOddsString = betInfoDetail.getLose();
            if(Double.valueOf(winOddsString)>Double.valueOf(optimizationModel.getWinOdds())){
                optimizationModel.setWinPlatfrom(betInfoDetail.getPlatform());
                optimizationModel.setWinOdds(winOddsString);
            }
            if(Double.valueOf(equalOddsString)>Double.valueOf(optimizationModel.getEqualOdds())){
                optimizationModel.setEqualPlatform(betInfoDetail.getPlatform());
                optimizationModel.setEqualOdds(equalOddsString);
            }
            if(Double.valueOf(lossOddsString)>Double.valueOf(optimizationModel.getLossOdds())){
                optimizationModel.setLossPlatform(betInfoDetail.getPlatform());
                optimizationModel.setLossOdds(lossOddsString);
            }
        }
        return optimizationModel;
    }
}
