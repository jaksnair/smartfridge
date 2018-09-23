/**
 * UUIDManager
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.service.id;

import java.util.UUID;

public interface UUIDManager {

    default String getItemUUID() {
        return UUID.randomUUID().toString();
    }

    String generateItemUUID();

    String generateItemUUID(final String iFridgeId);

    String getFridgeId (final String iUUID);

}
