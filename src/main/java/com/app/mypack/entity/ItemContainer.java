/**
 * ItemContainer
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.entity;

import com.app.mypack.constants.SmartFridgeManagerConstants;
import com.app.mypack.exception.ContainerEmptyException;
import com.app.mypack.exception.ContainerFullException;
import com.app.mypack.exception.ItemAlreadyRemovedException;
import com.app.mypack.exception.ItemCannotBeAddedException;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class ItemContainer {

    private int index;
    private SmartFridgeManagerConstants.ContainerType containerType;
    private int sizeInUnits;
    private Set<SmartFridgeItem> smartFridgeItemSet;

    public ItemContainer(int iIndex, SmartFridgeManagerConstants.ContainerType iContainerType, int iSizeInUnits) {

        index = iIndex;
        containerType = iContainerType;
        sizeInUnits= iSizeInUnits;

        smartFridgeItemSet = new HashSet<>();
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int iIndex) {
        index = iIndex;
    }

    public SmartFridgeManagerConstants.ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(SmartFridgeManagerConstants.ContainerType iContainerType) {
        containerType = iContainerType;
    }

    public int getSizeInUnits() {
        return sizeInUnits;
    }

    public void setSizeInUnits(int iSizeInUnits) {
        sizeInUnits = iSizeInUnits;
    }


    public boolean isFull() {
        return smartFridgeItemSet.size() == sizeInUnits;
    }

    public boolean isEmpty() {
        return smartFridgeItemSet.isEmpty();
    }


    public int currentItemsCount() {
        return smartFridgeItemSet.size();
    }

    public void addItemToContainer(final SmartFridgeItem iSmartFridgeItem) throws ContainerFullException, ItemCannotBeAddedException {

        if(isFull()){
            throw new ContainerFullException("ItemContainer : " + index );
        }

        if((containerType.equals(SmartFridgeManagerConstants.ContainerType.BOTTLE)
                && iSmartFridgeItem.getSmartFridgeItemType().equals(SmartFridgeManagerConstants.SmartFridgeItemType.SOLID))
                || (containerType.equals(SmartFridgeManagerConstants.ContainerType.JAR)
                && iSmartFridgeItem.getSmartFridgeItemType().equals(SmartFridgeManagerConstants.SmartFridgeItemType.LIQUID))) {
            throw new ItemCannotBeAddedException("Item is a " + iSmartFridgeItem.getSmartFridgeItemType().getType()
                    + " and Item Container can have only " + containerType.getType());
        }


        if(!isEmpty()) {
            long containerItemType = smartFridgeItemSet.stream().findFirst().get().getItemType();
            if(containerItemType != iSmartFridgeItem.getItemType()) {
                throw new ItemCannotBeAddedException("Item type of given item is " + iSmartFridgeItem.getItemType()
                        + " and Item Container can have only " + containerItemType);
            }
        }
        smartFridgeItemSet.add(iSmartFridgeItem);

    }


    public void removeItemFromContainer(final SmartFridgeItem iSmartFridgeItem) throws ContainerEmptyException,ItemAlreadyRemovedException {
        if(isEmpty()){
            throw new ContainerEmptyException("ItemContainer : " + index );
        }
        if(!smartFridgeItemSet.contains(iSmartFridgeItem)) {
            throw new ItemAlreadyRemovedException("Item " + iSmartFridgeItem.getItemUUID() + " is already removed from the container");
        }
        smartFridgeItemSet.remove(iSmartFridgeItem);
    }


    public void updateFillFactorForAllItems (Double updatedFillFactor) {
        smartFridgeItemSet.stream().forEach(iSmartFridgeItem -> iSmartFridgeItem.setFillFactor(updatedFillFactor));
    }


    public boolean hasItem (final SmartFridgeItem iSmartFridgeItem) {
        return smartFridgeItemSet.contains(iSmartFridgeItem);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ItemContainer.class.getSimpleName() + "[", "]")
                .add("index=" + index)
                .add("containerType=" + containerType)
                .add("sizeInUnits=" + sizeInUnits)
                .add("smartFridgeItemSet=" + smartFridgeItemSet)
                .toString();
    }
}
