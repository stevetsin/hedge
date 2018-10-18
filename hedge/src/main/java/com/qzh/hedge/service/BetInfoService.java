package com.qzh.hedge.service;

import com.qzh.hedge.model.BetInfoModel;
import com.qzh.hedge.model.BetInfoOddsModel;
import com.qzh.hedge.model.BetInfoQueryParam;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
public interface BetInfoService {

    List<BetInfoModel> getBetInfoByKeyAndPlatform(String key, String platfrom);

    List<BetInfoModel> getNewestInfosByKey(String key);

    List<BetInfoModel> getAllNewestInfos( BetInfoQueryParam betInfoQueryParam);

    List<BetInfoModel> getAllValidInfos();

    long insertBetInfo(BetInfoModel betInfoModel);

    boolean updateIsNewestStatus(String key, String platfrom);

    boolean invalidBetInfo(List<Long> shouldInvalidIds);

    boolean insertBetInfoOddsInfos(List<BetInfoOddsModel> betInfoOddsModelList);
}
