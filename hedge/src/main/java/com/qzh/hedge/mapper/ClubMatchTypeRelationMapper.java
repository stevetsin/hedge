package com.qzh.hedge.mapper;

import com.qzh.hedge.model.MatchTypeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by qzh on 2018-10-12.
 */
@Mapper
public interface ClubMatchTypeRelationMapper {

    int addClubMatchRelations(@Param("clubId") long clubId, @Param("matchTypeIds") List<Long> matchTypeIds);

    int deleteClubMatchRelations(@Param("clubId") long clubId);

    List<MatchTypeModel> getClubMatchTypes(@Param("clubId") long clubId);

}
