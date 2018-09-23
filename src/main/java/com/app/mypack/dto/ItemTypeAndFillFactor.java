/**
 * ItemTypeAndFillFactor
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.dto;

import java.util.StringJoiner;

public class ItemTypeAndFillFactor {

   private long itemType;
   private Double fillFactor;

    public ItemTypeAndFillFactor(long iItemType, Double iFillFactor) {
        itemType = iItemType;
        fillFactor = iFillFactor;
    }

    public long getItemType() {
        return itemType;
    }

    public void setItemType(long iItemType) {
        itemType = iItemType;
    }

    public Double getFillFactor() {
        return fillFactor;
    }

    public void setFillFactor(Double iFillFactor) {
        fillFactor = iFillFactor;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", ItemTypeAndFillFactor.class.getSimpleName() + "[", "]")
                .add("itemType=" + itemType)
                .add("fillFactor=" + fillFactor)
                .toString();
    }
}
