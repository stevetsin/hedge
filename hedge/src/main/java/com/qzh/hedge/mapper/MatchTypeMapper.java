package com.qzh.hedge.mapper;

import com.qzh.hedge.model.MatchTypeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Mapper
public interface MatchTypeMapper {
    String getDefaultMatchTypeNameByDic(@Param("orginMatchTypeName") String orginMatchTypeName);

    List<MatchTypeModel> getAllMatchTypes();

    MatchTypeModel getMatchTypeDetail(@Param("matchTypeId") long matchTypeId);

    List<String> getMatchTypeOtherNames(@Param("matchTypeId") long matchTypeId);

    int insertMatchType(@Param("matchTypeModel") MatchTypeModel matchTypeModel);

    int insertMatchTypeOtherName(@Param("matchTypeId") long matchTypeId, @Param("matchTypeOtherName") String matchTypeOtherName);

    int deleteMatchType(@Param("matchTypeId") long matchTypeId);

    int deleteMatchTypeDic(@Param("matchTypeId") long matchTypeId);

}
