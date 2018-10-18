package com.qzh.hedge.mapper;

import com.qzh.hedge.model.HedgeRecordModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by qzh on 2018-9-11.
 */
@Mapper
public interface HedgeRecordMapper {

    List<HedgeRecordModel> getHedgeRecordsByKey(@Param("betInfoKey") String betInfoKey);

    int insertHedgeRecord(@Param("hedgeRecordModel") HedgeRecordModel hedgeRecordModel);

    int updateHedgeRecordNewest(@Param("betInfoKey") String betInfoKey);

    List<HedgeRecordModel> getAllRecords();
}
