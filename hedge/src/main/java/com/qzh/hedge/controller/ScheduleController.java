package com.qzh.hedge.controller;

import com.qzh.hedge.HedgeUtil;
import com.qzh.hedge.model.BetInfoModel;
import com.qzh.hedge.model.BetInfoQueryParam;
import com.qzh.hedge.model.HedgeRecordModel;
import com.qzh.hedge.model.OptimizationModel;
import com.qzh.hedge.service.BetInfoService;
import com.qzh.hedge.service.HedgeRecordService;
import com.qzh.hedge.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qzh on 2018-9-10.
 * 定时器
 */
@Slf4j
@Component
public class ScheduleController {

    @Autowired
    private BetInfoService betInfoService;

    @Autowired
    private HedgeRecordService hedgeRecordService;

    @Autowired
    private HedgeHelper hedgeHelper;

    @Scheduled(cron = "0/1 * * * *  ?")
    public void caculHedge(){
        List<BetInfoModel> betInfoModels = betInfoService.getAllNewestInfos(new BetInfoQueryParam());
        if(betInfoModels != null || betInfoModels.size()>0){
            Map<String,List<BetInfoModel>> groupBy = betInfoModels.stream().collect(Collectors.groupingBy(BetInfoModel::getInfoKey));
            for(Map.Entry entry:groupBy.entrySet()){
                String key = (String) entry.getKey();
                List<BetInfoModel> betInfos =(List<BetInfoModel>) entry.getValue();
                OptimizationModel optimizationModel = hedgeHelper.getOptimization(key,betInfos);
                String newestInfo = JsonUtil.toJsonString(optimizationModel);
                String win = optimizationModel.getWinOdds();
                String equal = optimizationModel.getEqualOdds();
                String loss = optimizationModel.getLossOdds();
                List<Double> oddsList = new ArrayList<>();
                oddsList.add(Double.valueOf(win));
                oddsList.add(Double.valueOf(equal));
                oddsList.add(Double.valueOf(loss));
                Map<String,Object> map = HedgeUtil.caculProbability4List(oddsList);
                BigDecimal returnRateDecimal = (BigDecimal) map.get("returnRate");
                if(returnRateDecimal.compareTo(BigDecimal.ONE)>0){
                    //记录
                    List<HedgeRecordModel> currList = hedgeRecordService.getHedgeRecordsByKey(key);
                    HedgeRecordModel originNewest = null;
                    if(currList!=null&&currList.size()>0){
                        for(HedgeRecordModel hedgeRecordModel:currList){
                            if(hedgeRecordModel.getIsNewest()==1){
                                originNewest = hedgeRecordModel;
                            }
                        }
                    }
                    String originInfo = "";
                    if(originNewest !=null) {
                        originInfo = originNewest.getHedgeInfo();
                    }
                        if(originInfo.equals(newestInfo)){
                            //没有变化不用动
                        }else {
                            log.info("有新的符合对冲的情况出现！！！");
                            hedgeRecordService.updateHedgeRecordNewest(key);
                            HedgeRecordModel hedgeRecordModel = new HedgeRecordModel();
                            hedgeRecordModel.setBetInfoKey(key);
                            hedgeRecordModel.setHedgeInfo(newestInfo);
                            hedgeRecordModel.setIsNewest(1);
                            hedgeRecordModel.setCurrTime(System.currentTimeMillis());
                            hedgeRecordModel.setReturnRateString(returnRateDecimal.toString());
                            hedgeRecordService.insertHedgeRecord(hedgeRecordModel);
                        }
                    }
                }
        }
    }


    @Scheduled(cron = "0/30 * * * *  ?")
    public void setInvalid(){
        List<BetInfoModel> validBetInfos = betInfoService.getAllValidInfos();
        if(validBetInfos!=null && validBetInfos.size()>0){
            List<Long> shouldInvalidIds = new ArrayList<>();
            for(BetInfoModel betInfoModel:validBetInfos){
                if(betInfoModel.getInvalidTime()<System.currentTimeMillis()){
                    shouldInvalidIds.add(betInfoModel.getInfoId());
                }
            }
            if(shouldInvalidIds!=null&& shouldInvalidIds.size()>0){
                betInfoService.invalidBetInfo(shouldInvalidIds);
            }
        }
    }
}
