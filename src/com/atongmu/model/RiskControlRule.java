package com.atongmu.model;

import java.util.Date;

public class RiskControlRule {
    private Integer ruleId;

    private Short riskType;

    private Short policyType;

    private Short priority;

    private Integer appId;

    private Integer areaId;

    private String startIp;

    private String endIp;

    private Integer requestPeriod;

    private Integer requestLimit;

    private Integer forbidPeriod;

    private Byte status;

    private String comment;

    private Long addUserId;

    private Date addTime;

    private Long updateUserId;

    private Date updateTime;

    private Date effectTime;

    private Date expireTime;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Short getRiskType() {
        return riskType;
    }

    public void setRiskType(Short riskType) {
        this.riskType = riskType;
    }

    public Short getPolicyType() {
        return policyType;
    }

    public void setPolicyType(Short policyType) {
        this.policyType = policyType;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp == null ? null : startIp.trim();
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp == null ? null : endIp.trim();
    }

    public Integer getRequestPeriod() {
        return requestPeriod;
    }

    public void setRequestPeriod(Integer requestPeriod) {
        this.requestPeriod = requestPeriod;
    }

    public Integer getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(Integer requestLimit) {
        this.requestLimit = requestLimit;
    }

    public Integer getForbidPeriod() {
        return forbidPeriod;
    }

    public void setForbidPeriod(Integer forbidPeriod) {
        this.forbidPeriod = forbidPeriod;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}