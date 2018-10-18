package com.qzh.hedge.controller;

import com.qzh.hedge.model.*;
import com.qzh.hedge.model.test.OddsHandicapSolutionModel;
import com.qzh.hedge.service.BetInfoService;
import com.qzh.hedge.service.ClubService;
import com.qzh.hedge.service.HedgeRecordService;
import com.qzh.hedge.service.MatchTypeService;
import com.qzh.hedge.util.HedgeUtils;
import com.qzh.hedge.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qzh on 2018-9-1.
 */
@RestController
@Slf4j
@Api(description = "盘口分析前后端交互接口文档")
public class HedgeCacuController {

    @Autowired
    private BetInfoService betInfoService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private MatchTypeService matchTypeService;
    @Autowired
    private HedgeRecordService hedgeRecordService;

    @Value(value = "${football.time.gap}")
    private long FOOTBALL_TIME_GAP = 0;

    @RequestMapping(value ="/sendData" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public OddsHandicapSolutionModel receiveData(@RequestBody  OddsHandicapSolutionModel oddsHandicapSolutionModel){
        System.out.println(JsonUtil.toJsonString(oddsHandicapSolutionModel));
        return HedgeUtils.analyseSingleOddsHandicap(oddsHandicapSolutionModel);
    }


    @RequestMapping(value ="/receiveAndProceed" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "前端推送相关比赛盘口信息给后端", notes = "前端推送相关比赛盘口信息给后端")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "betInfoModels", value = "盘口信息", required = true, dataType = "list", paramType = "query"),
    })
    public boolean receiveAndProceed(@RequestBody List<BetInfoModel> betInfoModels) throws Exception{
        log.info("接受比赛盘口数据--》"+JsonUtil.toJsonString(betInfoModels));
        boolean res = true;
        if(betInfoModels ==null || betInfoModels.size()==0){
            return false;
        }
        for(BetInfoModel betInfoModel:betInfoModels){
            String key = "";
            try {
                key = this.betInfoKeyGenerator(betInfoModel);
            }catch (Exception e){
                log.info(e.getMessage());
                continue;
            }
            String matchTypeDefault = matchTypeService.getDefaultMatchTypeNameByDic(betInfoModel.getMatchType());
            betInfoModel.setMatchType(matchTypeDefault);
            String platform = betInfoModel.getPlatform();
            List<BetInfoModel> exsistInfos = betInfoService.getBetInfoByKeyAndPlatform(key,platform);


            String dateOfMatch = betInfoModel.getDateOfMatch();
            long matchTimeStamp = Long.valueOf(dateOfMatch);
            long invalidTime = matchTimeStamp+FOOTBALL_TIME_GAP;
            int isInMatch = System.currentTimeMillis()<matchTimeStamp?0:1;

            if(exsistInfos!=null && exsistInfos.size()>0){
                boolean skip = false;
                for(BetInfoModel exsistInfo:exsistInfos){
                    if(exsistInfo.getIsNewest() ==1){
                        if(
                        exsistInfo.getWin().equals(betInfoModel.getWin())&&
                        exsistInfo.getEqual().equals(betInfoModel.getEqual())&&
                        exsistInfo.getLose().equals(betInfoModel.getLose())&&
                                exsistInfo.getIsInMatch() ==  isInMatch
                        ){
                            skip = true;
                        }
                    }
                }
                if(skip){
                    continue;
                }
                betInfoService.updateIsNewestStatus(key,platform);
            }
            betInfoModel.setInvalidTime(invalidTime);
            betInfoModel.setInfoKey(key);
            betInfoModel.setIsNewest(1);
            betInfoModel.setIsInMatch(isInMatch);
            betInfoModel.setDateOfMatchTime(matchTimeStamp);
            String info = JsonUtil.toJsonString(betInfoModel);
            betInfoModel.setCreatedTimeStamp(System.currentTimeMillis());
            betInfoModel.setInfo(info);
            long infoId = betInfoService.insertBetInfo(betInfoModel);
            if(infoId>0){
                betInfoModel.setInfoId(infoId);
                List<BetInfoOddsModel> betInfoOddsModel = generateBetInfoOdds(betInfoModel);
                betInfoService.insertBetInfoOddsInfos(betInfoOddsModel);
            }
        }
        return res;
    }

    public List<BetInfoOddsModel> generateBetInfoOdds(BetInfoModel betInfoModel){
        long betInfoId = betInfoModel.getInfoId();
        List<BetInfoOddsModel> res = new ArrayList<>();
        String winOdds = betInfoModel.getWin();
        String equalOdds = betInfoModel.getEqual();
        String loseOdds = betInfoModel.getLose();
        BetInfoOddsModel win = new BetInfoOddsModel();
        win.setBetInfoId(betInfoId);
        win.setOddsName("win");
        win.setOddsValue(winOdds);
        BetInfoOddsModel equal = new BetInfoOddsModel();
        equal.setBetInfoId(betInfoId);
        equal.setOddsName("equal");
        equal.setOddsValue(equalOdds);
        BetInfoOddsModel lose = new BetInfoOddsModel();
        lose.setBetInfoId(betInfoId);
        lose.setOddsName("lose");
        lose.setOddsValue(loseOdds);
        res.add(win);
        res.add(equal);
        res.add(lose);
        return res;
    }


    @RequestMapping(value ="/getBetInfosByKey" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某一比赛的盘口信息", notes = "获取某一比赛的盘口信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "比赛key信息", required = true, dataType = "string", paramType = "query"),
    })
    public List<BetInfoModel> getBetInfosByKey(@RequestParam("key") String key){
        List<BetInfoModel> betInfoModels = betInfoService.getNewestInfosByKey(key);
        List<BetInfoModel> res = new ArrayList<>();
        if(betInfoModels !=null && betInfoModels.size()>0){
            for(BetInfoModel betInfoModel:betInfoModels){
                String info = betInfoModel.getInfo();
                BetInfoModel betInfoDetail = JsonUtil.jsonToObject(info,BetInfoModel.class);
                betInfoDetail.setInfoId(betInfoModel.getInfoId());
                res.add(betInfoDetail);
            }
        }
        return betInfoModels;
    }

    @RequestMapping(value ="/getAllBetInfosByGroup" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,List<BetInfoModel>> getAllBetInfosByGroup(@RequestBody BetInfoQueryParam betInfoQueryParam){
        Map<String,List<BetInfoModel>> res = new HashMap<>();
        List<BetInfoModel> betInfoModels = betInfoService.getAllNewestInfos(betInfoQueryParam);
        List<BetInfoModel> betInfoModelsWithDetail = new ArrayList<>();
        if(betInfoModels!=null && betInfoModels.size()>0){
            for(BetInfoModel betInfoModel:betInfoModels){
                String info = betInfoModel.getInfo();
                BetInfoModel betInfoDetail = JsonUtil.jsonToObject(info,BetInfoModel.class);
                betInfoDetail.setInfoId(betInfoModel.getInfoId());
                betInfoDetail.setInfoKey(betInfoModel.getInfoKey());
                betInfoModelsWithDetail.add(betInfoDetail);
            }
            res = betInfoModelsWithDetail.stream().collect(Collectors.groupingBy(BetInfoModel::getInfoKey));
        }
        return res;
    }

    private String betInfoKeyGenerator(BetInfoModel betInfoModel) throws RuntimeException{
        //比赛日期时间戳
        String dateOfMatch = betInfoModel.getDateOfMatch();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateOfMatch = simpleDateFormat.format(new Date(Long.valueOf(dateOfMatch)));


        //比赛类型
        String matchType = betInfoModel.getMatchType();

        String matchTypeDefault = matchTypeService.getDefaultMatchTypeNameByDic(matchType);

        if(matchTypeDefault == null || "".equals(matchTypeDefault)){
            throw new RuntimeException(matchType+"在比赛类型字典中不存在！");
        }
        //主
        String host = betInfoModel.getHost();
        host = host.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", "");
        ClubModel hostDefaultName = clubService.getClubByOtherName(host);
        //客
        if(hostDefaultName == null){
            throw new RuntimeException(host+"在俱乐部字典中不存在！");
        }
        String guest = betInfoModel.getGuest();
        guest = guest.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", "");
        try {
            guest = new String(guest.getBytes(), "UTF-8");
        }catch (Exception e){

        }
        ClubModel guestDefaultName = clubService.getClubByOtherName(guest);

        if(guestDefaultName == null){
            throw new RuntimeException(guest+"在俱乐部字典中不存在！");
        }
        String key = matchTypeDefault+"_"+dateOfMatch+"_"+hostDefaultName.getClubName()+"VS"+guestDefaultName.getClubName();
        return key;
    }





    @RequestMapping(value = "/getHedgeRecordList",method = RequestMethod.GET)
    @ResponseBody
    public List<HedgeRecordModel> getAllHedge() {
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
        return list;
    }

}
