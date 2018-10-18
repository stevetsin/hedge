package com.qzh.hedge.controller;

import com.qzh.hedge.HedgeUtil;
import com.qzh.hedge.model.*;
import com.qzh.hedge.service.BetInfoService;
import com.qzh.hedge.service.ClubService;
import com.qzh.hedge.service.HedgeRecordService;
import com.qzh.hedge.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qzh on 2018-9-11.
 */
@Controller
@Slf4j
@ApiIgnore
public class WebController {

    @Autowired
    private HedgeRecordService hedgeRecordService;
    @Autowired
    private ClubService clubService;

    @Autowired
    private BetInfoService betInfoService;

    @Autowired
    private HedgeHelper hedgeHelper;

    @RequestMapping(value = "/hedgeRecordList")
    public String getAllHedge(HttpServletRequest request) {
        log.info("查询对冲统计结果");
        List<HedgeRecordModel> list = hedgeRecordService.getAllRecords();
        if (list == null) {
            list = new ArrayList<>();
        }

        for (HedgeRecordModel hedgeRecordModel : list) {
            String info = hedgeRecordModel.getHedgeInfo();
            OptimizationModel optimizationModel = JsonUtil.jsonToObject(info, OptimizationModel.class);
            hedgeRecordModel.setOptimizationDetail(optimizationModel);
        }
        request.setAttribute("hedgeRecordList", list);
        return "hedgelist";
    }


    @RequestMapping(value = "/clubdic/insert")
    public String insertClubDic(HttpServletRequest request) {
        List<ClubModel> clubList = clubService.getClubList(new ClubQueryParam());
        request.setAttribute("clubNameList", clubList);
        request.setAttribute("message", "");
        return "club_dic_insert";
    }

    @RequestMapping(value = "/setting/clubdic/insert")
    public String insertClubDicToDb(HttpServletRequest request, ClubModel clubModel) {
        log.info("添加俱乐部字典" + JsonUtil.toJsonString(clubModel));
        try {
            clubService.insertClubDic(clubModel.getClubId(), clubModel.getClubOtherName());
        } catch (Exception e) {

        }
        ClubQueryParam clubQueryParam = new ClubQueryParam();
        List<ClubModel> clubList = clubService.getClubList(clubQueryParam);
        request.setAttribute("clubNameList", clubList);
        request.setAttribute("message", "");
        return "club_dic_insert";
    }


    @RequestMapping(value = "/getCurrAllBetInfos")
    public String getCurrAllBetInfos(HttpServletRequest request) {
        List<OptimizationModel> optimizationModels = new ArrayList<>();
        Map<String, List<BetInfoModel>> groupBy = new HashMap<>();
        List<BetInfoModel> betInfoModels = betInfoService.getAllNewestInfos(new BetInfoQueryParam());
        List<BetInfoModel> betInfoModelsWithDetail = new ArrayList<>();
        if (betInfoModels != null && betInfoModels.size() > 0) {
            for (BetInfoModel betInfoModel : betInfoModels) {
                String info = betInfoModel.getInfo();
                BetInfoModel betInfoDetail = JsonUtil.jsonToObject(info, BetInfoModel.class);
                betInfoDetail.setInfoId(betInfoModel.getInfoId());
                betInfoDetail.setInfoKey(betInfoModel.getInfoKey());
                betInfoDetail.setInfo(info);
                betInfoModelsWithDetail.add(betInfoDetail);

            }
            groupBy = betInfoModelsWithDetail.stream().collect(Collectors.groupingBy(BetInfoModel::getInfoKey));
        }
        for (Map.Entry entry : groupBy.entrySet()) {
            String key = (String) entry.getKey();
            List<BetInfoModel> betInfos = (List<BetInfoModel>) entry.getValue();
            OptimizationModel optimizationModel = hedgeHelper.getOptimization(key, betInfos);
            String win = optimizationModel.getWinOdds();
            String equal = optimizationModel.getEqualOdds();
            String loss = optimizationModel.getLossOdds();
            List<Double> oddsList = new ArrayList<>();
            oddsList.add(Double.valueOf(win));
            oddsList.add(Double.valueOf(equal));
            oddsList.add(Double.valueOf(loss));
            Map<String,Object> map = HedgeUtil.caculProbability4List(oddsList);
            BigDecimal returnRateDecimal = (BigDecimal) map.get("returnRate");
            optimizationModel.setReturnRateDecimal(returnRateDecimal.toString());
            optimizationModels.add(optimizationModel);
        }
        request.setAttribute("betInfoOptList", optimizationModels);
        return "betinfolist";
    }
}
