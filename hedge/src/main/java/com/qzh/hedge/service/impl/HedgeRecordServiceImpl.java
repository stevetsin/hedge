package com.qzh.hedge.service.impl;

import com.qzh.hedge.mapper.HedgeRecordMapper;
import com.qzh.hedge.model.HedgeRecordModel;
import com.qzh.hedge.service.HedgeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qzh on 2018-9-11.
 */
@Service("hedgeRecordService")
public class HedgeRecordServiceImpl implements HedgeRecordService {
    @Autowired
    private HedgeRecordMapper hedgeRecordMapper;

    @Override
    public List<HedgeRecordModel> getHedgeRecordsByKey(String betInfoKey) {
        return hedgeRecordMapper.getHedgeRecordsByKey(betInfoKey);
    }

    @Override
    public long insertHedgeRecord(HedgeRecordModel hedgeRecordModel) {
        hedgeRecordMapper.insertHedgeRecord(hedgeRecordModel);
        return hedgeRecordModel.getId();
    }

    @Override
    public int updateHedgeRecordNewest(String betInfoKey) {
        return hedgeRecordMapper.updateHedgeRecordNewest(betInfoKey);
    }

    @Override
    public List<HedgeRecordModel> getAllRecords() {
        return hedgeRecordMapper.getAllRecords();
    }
}
