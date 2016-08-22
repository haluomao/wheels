package com.atongmu.model;

import java.util.Date;

public class RiskWhiteBlackList {
    private Integer listId;

    private Byte whiteOrBlack;

    private Short riskType;

    private String startValue;

    private String endValue;

    private Byte status;

    private String comment;

    private Long addUserId;

    private Date addTime;

    private Long updateUserId;

    private Date updateTime;

    private Date effectTime;

    private Date expireTime;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Byte getWhiteOrBlack() {
        return whiteOrBlack;
    }

    public void setWhiteOrBlack(Byte whiteOrBlack) {
        this.whiteOrBlack = whiteOrBlack;
    }

    public Short getRiskType() {
        return riskType;
    }

    public void setRiskType(Short riskType) {
        this.riskType = riskType;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue == null ? null : startValue.trim();
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue == null ? null : endValue.trim();
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