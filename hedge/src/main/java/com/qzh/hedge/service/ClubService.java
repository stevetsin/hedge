package com.qzh.hedge.service;

import com.qzh.hedge.model.ClubModel;
import com.qzh.hedge.model.ClubQueryParam;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
public interface ClubService {
    ClubModel getClubByOtherName(String otherName);

    boolean insertClubDic(long clubId,String clubOtherName);

    List<ClubModel> getClubList(ClubQueryParam clubQueryParam);

    boolean setClubMatchTypes(long clubId,List<Long> matchTypeIds);

    ClubModel getClubDetail(long clubId);

    long addClubModel(ClubModel clubModel);

    boolean deleteClub(long clubId);
}
