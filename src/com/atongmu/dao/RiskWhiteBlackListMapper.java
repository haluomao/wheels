package com.atongmu.dao;

import com.atongmu.model.RiskWhiteBlackList;

public interface RiskWhiteBlackListMapper {
    int deleteByPrimaryKey(Integer listId);

    int insert(RiskWhiteBlackList record);

    int insertSelective(RiskWhiteBlackList record);

    RiskWhiteBlackList selectByPrimaryKey(Integer listId);

    int updateByPrimaryKeySelective(RiskWhiteBlackList record);

    int updateByPrimaryKey(RiskWhiteBlackList record);
}