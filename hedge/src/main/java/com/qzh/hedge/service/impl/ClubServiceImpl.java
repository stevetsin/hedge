package com.qzh.hedge.service.impl;

import com.qzh.hedge.mapper.ClubMapper;
import com.qzh.hedge.mapper.ClubMatchTypeRelationMapper;
import com.qzh.hedge.model.ClubModel;
import com.qzh.hedge.model.ClubQueryParam;
import com.qzh.hedge.model.MatchTypeModel;
import com.qzh.hedge.service.ClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */

@Service("clubService")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private ClubMatchTypeRelationMapper clubMatchTypeRelationMapper;

    @Override
    public ClubModel getClubByOtherName(String otherName) {
        ClubModel clubModel = clubMapper.getClubByOtherName(otherName);
        return clubModel;
    }

    @Override
    public boolean insertClubDic(long clubId, String clubOtherName) {
        return clubMapper.insertClubDic(clubId,clubOtherName)>0;
    }

    @Override
    public List<ClubModel> getClubList(ClubQueryParam clubQueryParam) {
        return clubMapper.getClubList(clubQueryParam);
    }

    @Override
    public boolean setClubMatchTypes(long clubId, List<Long> matchTypeIds) {
        clubMatchTypeRelationMapper.deleteClubMatchRelations(clubId);
        if(matchTypeIds!=null && matchTypeIds.size()>0){
            clubMatchTypeRelationMapper.addClubMatchRelations(clubId,matchTypeIds);
        }
        return true;
    }

    @Override
    public ClubModel getClubDetail(long clubId) {

        ClubModel clubModel = clubMapper.getClubDetail(clubId);
        if(clubModel!=null){
            List<String> clubOtherNames = clubMapper.getClubOtherNames(clubId);
            clubModel.setAllClubOtherNames(clubOtherNames);
            List<MatchTypeModel> allMatchTypes =clubMatchTypeRelationMapper.getClubMatchTypes(clubId);
            clubModel.setClubRelatedMatchTypes(allMatchTypes);
        }
        return clubModel;
    }

    @Override
    public long addClubModel(ClubModel clubModel) {
        clubMapper.insertClubModel(clubModel);
        long clubId = clubModel.getClubId();
        if(clubId>0){
            String clubOtherName = clubModel.getClubName();
            clubMapper.insertClubDic(clubId,clubOtherName);
        }
        return clubId;
    }

    @Override
    public boolean deleteClub(long clubId) {
        int deleteClubRes = clubMapper.deleteClub(clubId);
        if(deleteClubRes>0){
            clubMapper.deleteClubOtherNames(clubId);
        }
        return deleteClubRes>0;
    }
}
