package com.qzh.hedge.service;

import com.qzh.hedge.model.HedgeRecordModel;

import java.util.List;

/**
 * Created by qzh on 2018-9-11.
 */
public interface HedgeRecordService {
    List<HedgeRecordModel> getHedgeRecordsByKey(String betInfoKey);

    long insertHedgeRecord(HedgeRecordModel hedgeRecordModel);

    int updateHedgeRecordNewest(String betInfoKey);

    List<HedgeRecordModel> getAllRecords();
}
