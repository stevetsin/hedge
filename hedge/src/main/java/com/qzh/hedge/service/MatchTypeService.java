package com.qzh.hedge.service;

import com.qzh.hedge.model.MatchTypeModel;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
public interface MatchTypeService {
    String getDefaultMatchTypeNameByDic(String orginMatchTypeName);

    List<MatchTypeModel> getAllMatchTypes();

    MatchTypeModel getMatchTypeDetail(long matchTypeId);

    long addMatchType(MatchTypeModel matchTypeModel);

    boolean setMatchTypeOtherName(long matchTypeId,String matchTypeOtherName);

    List<MatchTypeModel> getClubMatchTypes(long clubId);

    boolean deleteMatchType(long matchTypeId);

}
