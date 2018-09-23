/**
 * SmartFridgeItem
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.entity;

import com.app.mypack.constants.SmartFridgeManagerConstants;
import com.app.mypack.service.validator.NotNull;
import com.app.mypack.service.validator.Size;
import com.app.mypack.service.validator.SmartFridgeBeanValidator;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class SmartFridgeItem {

    @NotNull
    @Size(min = 3, max = 64)
    private String itemUUID;
    @NotNull
    private long itemType;
    @NotNull
    @Size(min = 3, max = 64)
    private String name;
    @NotNull
    private Double fillFactor;

    private SmartFridgeManagerConstants.SmartFridgeItemType smartFridgeItemType;
    private boolean available;
    private LocalDateTime firstAdded;
    private LocalDateTime lastAddedOrRemoved;

    public SmartFridgeItem(String iItemUUID, long iItemType, String iName, Double iFillFactor) {

        itemUUID = iItemUUID;
        itemType = iItemType;
        name = iName;
        fillFactor = iFillFactor;

        new SmartFridgeBeanValidator<SmartFridgeItem>().validate(this);
    }


    public SmartFridgeItem(String iItemUUID, long iItemType, String iName, Double iFillFactor,
                           SmartFridgeManagerConstants.SmartFridgeItemType iSmartFridgeItemType,
                           boolean iAvailable, LocalDateTime iFirstAdded, LocalDateTime iLastAddedOrRemoved) {
        itemUUID = iItemUUID;
        itemType = iItemType;
        name = iName;
        fillFactor = iFillFactor;
        smartFridgeItemType = iSmartFridgeItemType;
        available = iAvailable;
        firstAdded = iFirstAdded;
        lastAddedOrRemoved = iLastAddedOrRemoved;

        new SmartFridgeBeanValidator<SmartFridgeItem>().validate(this);
    }

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String iItemUUID) {
        itemUUID = iItemUUID;
    }

    public long getItemType() {
        return itemType;
    }

    public void setItemType(long iItemType) {
        itemType = iItemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String iName) {
        name = iName;
    }

    public Double getFillFactor() {
        return fillFactor;
    }

    public void setFillFactor(Double iFillFactor) {
        fillFactor = iFillFactor;
    }

    public SmartFridgeManagerConstants.SmartFridgeItemType getSmartFridgeItemType() {
        return smartFridgeItemType;
    }

    public void setSmartFridgeItemType(SmartFridgeManagerConstants.SmartFridgeItemType iSmartFridgeItemType) {
        smartFridgeItemType = iSmartFridgeItemType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean iAvailable) {
        available = iAvailable;
    }

    public LocalDateTime getFirstAdded() {
        return firstAdded;
    }

    public void setFirstAdded(LocalDateTime iFirstAdded) {
        firstAdded = iFirstAdded;
    }

    public LocalDateTime getLastAddedOrRemoved() {
        return lastAddedOrRemoved;
    }

    public void setLastAddedOrRemoved(LocalDateTime iLastAddedOrRemoved) {
        lastAddedOrRemoved = iLastAddedOrRemoved;
    }


    @Override
    public boolean equals(Object iO) {
        if (this == iO) return true;
        if (iO == null || getClass() != iO.getClass()) return false;
        SmartFridgeItem that = (SmartFridgeItem) iO;
        return itemType == that.itemType &&
                Objects.equals(itemUUID, that.itemUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemUUID, itemType);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SmartFridgeItem.class.getSimpleName() + "[", "]")
                .add("itemUUID='" + itemUUID + "'")
                .add("itemType=" + itemType)
                .add("name='" + name + "'")
                .add("fillFactor=" + fillFactor)
                .add("smartFridgeItemType=" + smartFridgeItemType)
                .add("available=" + available)
                .add("firstAdded=" + firstAdded)
                .add("lastAddedOrRemoved=" + lastAddedOrRemoved)
                .toString();
    }
}
