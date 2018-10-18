package com.qzh.hedge.mapper;

import com.qzh.hedge.model.BetInfoModel;
import com.qzh.hedge.model.BetInfoOddsModel;
import com.qzh.hedge.model.BetInfoQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Mapper
public interface BetInfoMapper {

    List<BetInfoModel> getBetInfoByKeyAndPlatform(@Param("key") String key, @Param("platform") String platform);

    List<BetInfoModel> getNewestInfosByKey(@Param("key") String key);

    long insertBetInfo(@Param("betInfoModel") BetInfoModel betInfoModel);

    boolean updateIsNewestStatus(@Param("key") String key, @Param("platform") String platform);

    List<BetInfoModel> getAllNewestInfos(@Param("betInfoQueryParam") BetInfoQueryParam betInfoQueryParam);

    List<BetInfoModel> getAllValidInfos();


    int invalidBetInfo(@Param("shouldInvalidIds") List<Long> shouldInvalidIds);

    int insertBetInfoOdds(@Param("betInfoOddsModelList") List<BetInfoOddsModel> betInfoOddsModelList);


}
