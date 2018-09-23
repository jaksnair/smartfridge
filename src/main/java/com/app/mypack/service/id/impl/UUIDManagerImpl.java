/**
 * UUIDManagerImpl
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.service.id.impl;

import com.app.mypack.service.id.UUIDManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class UUIDManagerImpl implements UUIDManager {

    private static final Map<String, Set<String>> FRIDGE_ID_TO_ITEM_UUID_MAP = new HashMap<>();

    private static final String DEFAULT_FRIDGE_ID = "FRIDGE_ID";

    private static Logger LOGGER = LoggerFactory.getLogger(UUIDManagerImpl.class);

    @Override
    public String generateItemUUID() {

        final String itemUUID = getItemUUID();

        Set<String> itemUUIDs = FRIDGE_ID_TO_ITEM_UUID_MAP.get(DEFAULT_FRIDGE_ID);
        if(null == itemUUIDs) {
            itemUUIDs = new HashSet<>();
        }
        itemUUIDs.add(itemUUID);
        return itemUUID;
    }


    @Override
    public String generateItemUUID(final String iFridgeId) {

        Set<String> itemUUIDs = null;
        if(!FRIDGE_ID_TO_ITEM_UUID_MAP.containsKey(iFridgeId)) {
            itemUUIDs = FRIDGE_ID_TO_ITEM_UUID_MAP.get(iFridgeId);
        }

        if(itemUUIDs == null) {
            itemUUIDs = new HashSet<>();
        }

        final String itemUUID = getItemUUID();
        itemUUIDs.add(itemUUID);
        return itemUUID;
    }

    @Override
    public String getFridgeId(final String iUUID) {


        final Optional<Map.Entry<String,Set<String>>> fridgeEntryOpt = FRIDGE_ID_TO_ITEM_UUID_MAP.entrySet()
                .stream()
                .filter(iStringSetEntry -> iStringSetEntry.getValue().contains(iUUID))
                .findAny();

        if(fridgeEntryOpt.isPresent()) {
            return fridgeEntryOpt.get().getKey();
        } else {
            LOGGER.error("UUID Manager could not find any smart fridge id corresponding to item UUID received. ");
            return DEFAULT_FRIDGE_ID;
        }

    }
}
