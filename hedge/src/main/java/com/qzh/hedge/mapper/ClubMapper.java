package com.qzh.hedge.mapper;

import com.qzh.hedge.model.ClubModel;
import com.qzh.hedge.model.ClubQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Mapper
public interface ClubMapper {

    ClubModel getClubByOtherName(@Param("otherName") String otherName);

    int insertClubDic(@Param("clubId") long clubId, @Param("clubOtherName") String clubOtherName);

    List<ClubModel> getClubList(@Param("clubQueryParam") ClubQueryParam clubQueryParam);

    ClubModel getClubDetail(@Param("clubId") long clubId);

    List<String> getClubOtherNames(@Param("clubId") long clubId);

    int insertClubModel(@Param("clubModel") ClubModel clubModel);


    int deleteClub(@Param("clubId") long clubId);

    int deleteClubOtherNames(@Param("clubId") long clubId);



}
