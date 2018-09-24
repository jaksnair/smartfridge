package com.app.mypack.controller.impl;

import com.app.mypack.constants.SmartFridgeManagerConstants;
import com.app.mypack.controller.SmartFridgeManager;
import com.app.mypack.dto.ItemTypeAndFillFactor;
import com.app.mypack.entity.ItemContainer;
import com.app.mypack.entity.SmartFridgeItem;
import com.app.mypack.exception.ContainerFullException;
import com.app.mypack.exception.ItemAlreadyRemovedException;

import com.app.mypack.exception.ItemCannotBeAddedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SmartFridgeManagerImpl implements SmartFridgeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmartFridgeManagerImpl.class);

    private final Map<String, SmartFridgeItem> smartFridgeItemHashMap = new HashMap<>();
    private final Map<Long, Set<Integer>> smartFridgeItemTypeContainerIdsRepoMap = new HashMap<>();
    private final Map<Integer, ItemContainer> smartFridgeContainerRepoMap = new HashMap<>();


    /**
     * This method is called every time an item is removed from the fridge
     *
     * @param iItemUUID
     */
    @Override
    public void handleItemRemoved(String iItemUUID) {

        if(! smartFridgeItemHashMap.containsKey(iItemUUID)) {
            LOGGER.info("message= \"Item UUID does not match to any Items in the Smart Fridge\"");
        }

        SmartFridgeItem smartFridgeItem = smartFridgeItemHashMap.get(iItemUUID);
        long itemType = smartFridgeItem.getItemType();

        Set<Integer> itemContainerIdsOfItemType = smartFridgeItemTypeContainerIdsRepoMap.get(itemType);
        for(Integer itemContainerId : itemContainerIdsOfItemType) {
            ItemContainer itemContainer = smartFridgeContainerRepoMap.get(itemContainerId);
            if(itemContainer.hasItem(smartFridgeItem)) {
                try {
                    itemContainer.removeItemFromContainer(smartFridgeItem);
                } catch (ItemAlreadyRemovedException iE) {
                    LOGGER.error("message= \"While removing Exception %m\"", iE.getMessage());
                }
            }
        }
        smartFridgeItemHashMap.remove(smartFridgeItem);

        updateFillFactorForItemType(smartFridgeItem.getItemType());

    }

    /**
     * This method is called every time an item is stored in the fridge
     *
     * @param iItemType
     * @param iItemUUID
     * @param iName
     * @param iFillFactor
     */
    @Override
    public void handleItemAdded(long iItemType, String iItemUUID, String iName, Double iFillFactor) {

        LocalDateTime itemAddedDateTime = LocalDateTime.now(ZoneId.of("GMT+0"));
        SmartFridgeItem smartFridgeItem =
                new SmartFridgeItem(iItemUUID,iItemType,iName,iFillFactor,
                        SmartFridgeManagerConstants.SmartFridgeItemType.ONETYPE,true,itemAddedDateTime,itemAddedDateTime);


        int itemContainerIndex = getItemContainerForItem(iItemType);
        ItemContainer itemContainer = smartFridgeContainerRepoMap.get(itemContainerIndex);
        try {
            if(null == itemContainer) {
                itemContainer = new ItemContainer(itemContainerIndex, SmartFridgeManagerConstants.ContainerType.ONETYPE, 10);
            }
            itemContainer.addItemToContainer(smartFridgeItem);
        } catch(ContainerFullException | ItemCannotBeAddedException iE) {
            LOGGER.info("message= \"Chosen container cannot have this item, need to place new container\"");
            itemContainer = new ItemContainer(++itemContainerIndex, SmartFridgeManagerConstants.ContainerType.ONETYPE, 10);
            itemContainer.addItemToContainer(smartFridgeItem);
        }
        smartFridgeContainerRepoMap.put(itemContainerIndex,itemContainer);
        smartFridgeItemTypeContainerIdsRepoMap.compute(iItemType,(k,v) -> (v==null) ? new HashSet<>():v).add(itemContainerIndex);
        smartFridgeItemHashMap.put(iItemUUID, smartFridgeItem);

        updateFillFactorForItemType(smartFridgeItem.getItemType());
    }

    /**
     * Returns a list of items based on their fill factor. This method is used by the
     * fridge to display items that are running low and need to be replenished.
     * <p>
     * i.e.
     * getItems( 0.5 ) - will return any items that are 50% or less full, including
     * items that are depleted. Unless all available containers are
     * empty, this method should only consider the non-empty containers
     * when calculating the overall fillFactor for a given item.
     *
     * @param iFillFactor
     * @return an array of arrays containing [ itemType, fillFactor ]
     */
    @Override
    public Object[] getItems(Double iFillFactor) {

        return smartFridgeItemTypeContainerIdsRepoMap.keySet()
                .stream()
                .filter(iItemType -> getCurrentFillFactorOfThisItemType(iItemType) <= iFillFactor)
                .map(iItemType -> new ItemTypeAndFillFactor(iItemType,getCurrentFillFactorOfThisItemType(iItemType)))
                .toArray();

    }

    /**
     * Returns the fill factor for a given item type to be displayed to the owner. Unless all available containers are
     * empty, this method should only consider the non-empty containers
     * when calculating the overall fillFactor for a given item.
     *
     * @param iItemType
     * @return a double representing the average fill factor for the item type
     */
    @Override
    public Double getFillFactor(long iItemType) {
       return getCurrentFillFactorOfThisItemType(iItemType);
    }

    /**
     * Stop tracking a given item. This method is used by the fridge to signal that its
     * owner will no longer stock this item and thus should not be returned from #getItems()
     *
     * @param iItemType
     */
    @Override
    public void forgetItem(long iItemType) {

        smartFridgeItemTypeContainerIdsRepoMap.remove(iItemType);
        smartFridgeContainerRepoMap.entrySet().removeIf(iIntegerItemContainerEntry -> iIntegerItemContainerEntry.getKey() == iItemType);
        smartFridgeItemHashMap.entrySet().removeIf(iStringSmartFridgeItemEntry -> iStringSmartFridgeItemEntry.getValue().getItemType() == iItemType);

    }

    private int getItemContainerForItem(final Long iItemType) {

        int itemContainerIndex = 0;

        if (smartFridgeItemTypeContainerIdsRepoMap.containsKey(iItemType)) {
            LOGGER.info("message = \"Item type exist in SmartFridge and container is identified\"");
            itemContainerIndex = smartFridgeItemTypeContainerIdsRepoMap.get(iItemType).stream().sorted().mapToInt(i->i).max().getAsInt();
        } else {
            LOGGER.info("message = \"Item type is new and need a new container\"");
            itemContainerIndex = smartFridgeContainerRepoMap
                    .keySet()
                    .stream()
                    .mapToInt(i -> i)
                    .max()
                    .orElse(1);
        }
        return itemContainerIndex;
    }


    private Double getCurrentFillFactorOfThisItemType (Long iItemType) {

        if(null == iItemType) {
            return 0.0;
        }

        Set<Integer> containersOfItemType = smartFridgeItemTypeContainerIdsRepoMap.get(iItemType);

        if(null != containersOfItemType) {
            return containersOfItemType.stream()
                    .filter(iItemContainerId -> !smartFridgeContainerRepoMap.get(iItemContainerId).isEmpty())
                    .map(iItemContainerId -> smartFridgeContainerRepoMap.get(iItemContainerId).currentItemsCount()/smartFridgeContainerRepoMap.get(iItemContainerId).getSizeInUnits())
                    .collect(Collectors.toList()).stream()
                    .mapToInt(i -> i).summaryStatistics().getAverage();
        } else {
            return 1.0;
        }
    }


    private void updateFillFactorForItemType (Long iItemType) {
        Double currentFillFactorOfItemType = getCurrentFillFactorOfThisItemType(iItemType);
        smartFridgeItemTypeContainerIdsRepoMap.get(iItemType).stream()
                .forEach(iItemContainerId -> smartFridgeContainerRepoMap.get(iItemContainerId).updateFillFactorForAllItems(currentFillFactorOfItemType));

    }

}
