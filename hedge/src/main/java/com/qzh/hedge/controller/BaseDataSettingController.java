package com.qzh.hedge.controller;

import com.qzh.hedge.model.*;
import com.qzh.hedge.service.ClubService;
import com.qzh.hedge.service.MatchTypeService;
import com.qzh.hedge.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by qzh on 2018-10-12.
 */
@RestController
@Slf4j
@Api(description = "基础数据设置前后端交互接口文档")
public class BaseDataSettingController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private MatchTypeService matchTypeService;


    @RequestMapping(value ="datasetting/getAllClubs" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取俱乐部列表", notes = "获取俱乐部列表")
    public List<ClubModel> getAllClubs(@RequestBody ClubQueryParam clubQueryParam){
        log.info("获取俱乐部列表-->"+JsonUtil.toJsonString(clubQueryParam));
        List<ClubModel> clubList = clubService.getClubList(clubQueryParam);
        return clubList;
    }

    @RequestMapping(value ="datasetting/getClubDetail" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取俱乐部详情", notes = "获取俱乐部详情")
    public ClubModel getClubDetail(@RequestParam("clubId") long clubId){
        return  clubService.getClubDetail(clubId);
    }


    @RequestMapping(value ="datasetting/addClub" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增俱乐部", notes = "新增俱乐部")
    public boolean addClub(@RequestBody ClubAddParam clubAddParam){
        log.info("新增俱乐部 --》"+ JsonUtil.toJsonString(clubAddParam));
        ClubModel clubModel = new ClubModel();
        clubModel.setClubName(clubAddParam.getClubName().replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", ""));
        clubModel.setCountry(clubAddParam.getCountry().replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", ""));
        clubModel.setClubType(clubAddParam.getClubType());
        return clubService.addClubModel(clubModel)>0;
    }


    @RequestMapping(value ="datasetting/deleteClub" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除俱乐部", notes = "删除俱乐部")
    public boolean deleteClub(@RequestParam("clubId") long clubId){
        return clubService.deleteClub(clubId);
    }

    @RequestMapping(value ="datasetting/setClubMatchTypes" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置俱乐部关联联赛", notes = "设置俱乐部关联联赛")
    public boolean setClubMatchTypes(@RequestBody ClubMatchTypeSetParam clubMatchTypeSetParam){
        return  clubService.setClubMatchTypes(clubMatchTypeSetParam.getClubId(),clubMatchTypeSetParam.getMatchTypeIds());
    }

    @RequestMapping(value ="datasetting/addClubOtherName" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置俱乐部别名", notes = "设置俱乐部别名")
    public boolean addClubOtherName(@RequestBody ClubModel clubModel){
        log.info("添加俱乐部字典" + JsonUtil.toJsonString(clubModel));
        clubModel.setClubOtherName(clubModel.getClubOtherName().replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", ""));
        return  clubService.insertClubDic(clubModel.getClubId(), clubModel.getClubOtherName());
    }


    @RequestMapping(value ="datasetting/getAllMatchTypes" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取所有联赛类型列表", notes = "获取所有联赛类型列表")
    public List<MatchTypeModel> getAllMatchTypes(){
        return matchTypeService.getAllMatchTypes();
    }

    @RequestMapping(value ="datasetting/getMatchTypeDetail" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取联赛详情", notes = "获取联赛详情")
    public MatchTypeModel getMatchTypeDetail(@RequestParam long matchTypeId){
        return matchTypeService.getMatchTypeDetail(matchTypeId);
    }

    @RequestMapping(value ="datasetting/addMatchType" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增联赛类型", notes = "新增联赛类型")
    public long addMatchType(@RequestBody MatchTypeModel matchTypeModel){
        matchTypeModel.setMatchTypeName(matchTypeModel.getMatchTypeName().replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", ""));
        return matchTypeService.addMatchType(matchTypeModel);
    }

    @RequestMapping(value ="datasetting/addMatchTypeOtherName" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增联赛类型别名", notes = "新增联赛类型别名")
    public boolean addMatchTypeOtherName(@RequestBody MatchTypeModel matchTypeModel){
        return matchTypeService.setMatchTypeOtherName(matchTypeModel.getMatchTypeId(),matchTypeModel.getMatchTypeOtherName().replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", ""));
    }

    @RequestMapping(value ="datasetting/deleteMatchType" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除联赛类型", notes = "删除联赛类型")
    public boolean deleteMatchType(long matchTypeId){
        return matchTypeService.deleteMatchType(matchTypeId);
    }

}
