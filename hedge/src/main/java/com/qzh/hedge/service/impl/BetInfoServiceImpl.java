package com.qzh.hedge.service.impl;

import com.qzh.hedge.mapper.BetInfoMapper;
import com.qzh.hedge.model.BetInfoModel;
import com.qzh.hedge.model.BetInfoOddsModel;
import com.qzh.hedge.model.BetInfoQueryParam;
import com.qzh.hedge.service.BetInfoService;
import com.qzh.hedge.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qzh on 2018-9-10.
 */
@Service("betInfoService")
public class BetInfoServiceImpl implements BetInfoService {

    @Autowired
    private BetInfoMapper betInfoMapper;

    @Override
    public List<BetInfoModel> getBetInfoByKeyAndPlatform(String key, String platfrom) {
        List<BetInfoModel> res = betInfoMapper.getBetInfoByKeyAndPlatform(key,platfrom);
        if(res!=null && res.size()>0){
            for(BetInfoModel betInfoModel:res){
                String info = betInfoModel.getInfo();
                BetInfoModel detail = JsonUtil.jsonToObject(info,BetInfoModel.class);
                betInfoModel.setWin(detail.getWin());
                betInfoModel.setEqual(detail.getEqual());
                betInfoModel.setLose(detail.getLose()
                );
            }
        }
        return res;
    }

    @Override
    public List<BetInfoModel> getNewestInfosByKey(String key) {
        return betInfoMapper.getNewestInfosByKey(key);
    }

    @Override
    public long insertBetInfo(BetInfoModel betInfoModel) {
        betInfoMapper.insertBetInfo(betInfoModel);
        return betInfoModel.getInfoId();
    }

    @Override
    public boolean updateIsNewestStatus(String key, String platfrom) {
        return betInfoMapper.updateIsNewestStatus(key,platfrom);
    }

    @Override
    public List<BetInfoModel> getAllNewestInfos( BetInfoQueryParam betInfoQueryParam) {
        return betInfoMapper.getAllNewestInfos(betInfoQueryParam);
    }

    @Override
    public List<BetInfoModel> getAllValidInfos() {
        return betInfoMapper.getAllValidInfos();
    }

    @Override
    public boolean invalidBetInfo(List<Long> shouldInvalidIds) {
        return betInfoMapper.invalidBetInfo(shouldInvalidIds)>0;
    }

    @Override
    public boolean insertBetInfoOddsInfos(List<BetInfoOddsModel> betInfoOddsModelList) {
        return betInfoMapper.insertBetInfoOdds(betInfoOddsModelList)>0;
    }
}
