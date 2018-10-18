package com.qzh.hedge.service.impl;

import com.qzh.hedge.mapper.ClubMatchTypeRelationMapper;
import com.qzh.hedge.mapper.MatchTypeMapper;
import com.qzh.hedge.model.MatchTypeModel;
import com.qzh.hedge.service.MatchTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Service("matchTypeService")
public class MatchTypeServiceImpl implements MatchTypeService{

    @Autowired
    private MatchTypeMapper matchTypeMapper;

    @Autowired
    private ClubMatchTypeRelationMapper clubMatchTypeRelationMapper;

    @Override
    public String getDefaultMatchTypeNameByDic(String orginMatchTypeName) {
        return matchTypeMapper.getDefaultMatchTypeNameByDic(orginMatchTypeName);
    }

    @Override
    public List<MatchTypeModel> getAllMatchTypes() {
        return matchTypeMapper.getAllMatchTypes();
    }

    @Override
    public MatchTypeModel getMatchTypeDetail(long matchTypeId) {
        MatchTypeModel res = matchTypeMapper.getMatchTypeDetail(matchTypeId);
        if(res!=null){
            List<String> otherNames = matchTypeMapper.getMatchTypeOtherNames(res.getMatchTypeId());
            res.setAllMatchTypeOtherNames(otherNames);
        }
        return res;
    }

    @Override
    public long addMatchType(MatchTypeModel matchTypeModel) {
        matchTypeMapper.insertMatchType(matchTypeModel);
        long matchTypeId = matchTypeModel.getMatchTypeId();
        if(matchTypeId>0){
            matchTypeMapper.insertMatchTypeOtherName(matchTypeId,matchTypeModel.getMatchTypeName());
        }
        return matchTypeId;
    }

    @Override
    public boolean setMatchTypeOtherName(long matchTypeId, String matchTypeOtherName) {
        return matchTypeMapper.insertMatchTypeOtherName(matchTypeId,matchTypeOtherName)>0;
    }

    @Override
    public List<MatchTypeModel> getClubMatchTypes(long clubId) {
        return clubMatchTypeRelationMapper.getClubMatchTypes(clubId);
    }


    @Override
    public boolean deleteMatchType(long matchTypeId) {
        int res =  matchTypeMapper.deleteMatchType(matchTypeId);
        if(res>0){
            matchTypeMapper.deleteMatchTypeDic(matchTypeId);
        }
        return res>0;
    }
}
