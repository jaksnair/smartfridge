package com.app.mypack.service.init;

import com.app.mypack.controller.SmartFridgeManager;
import com.app.mypack.controller.impl.SmartFridgeManagerImpl;
import com.app.mypack.service.id.UUIDManager;
import com.app.mypack.service.id.impl.UUIDManagerImpl;

public class SmartFridgeManagerMain {

    public static void main(String[] args) {

        UUIDManager uuidManager = new UUIDManagerImpl();

        SmartFridgeManager smartFridgeManager = new SmartFridgeManagerImpl();
        String uuid1 = uuidManager.generateItemUUID();
        smartFridgeManager.handleItemAdded( 1L,uuid1, "Item Name1", 1.0);
        String uuid12 = uuidManager.generateItemUUID();
        smartFridgeManager.handleItemAdded( 1L,uuid12, "Item Name2", 1.0);
        String uuid2 = uuidManager.generateItemUUID();
        smartFridgeManager.handleItemAdded( 2L,uuid2, "Item Name2", 1.0);
        String uuid3 = uuidManager.generateItemUUID();
        smartFridgeManager.handleItemAdded( 3L,uuid3, "Item Name3", 1.0);


        Double fillFactor = smartFridgeManager.getFillFactor(1L);

        Object[] objects = smartFridgeManager.getItems(fillFactor);

        smartFridgeManager.handleItemRemoved(uuid12);

        fillFactor = smartFridgeManager.getFillFactor(1L);

        objects = smartFridgeManager.getItems(fillFactor);

        smartFridgeManager.forgetItem(1L);


    }

}
