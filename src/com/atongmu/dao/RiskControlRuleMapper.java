package com.atongmu.dao;

import com.atongmu.model.RiskControlRule;

public interface RiskControlRuleMapper {
    int deleteByPrimaryKey(Integer ruleId);

    int insert(RiskControlRule record);

    int insertSelective(RiskControlRule record);

    RiskControlRule selectByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(RiskControlRule record);

    int updateByPrimaryKey(RiskControlRule record);
}