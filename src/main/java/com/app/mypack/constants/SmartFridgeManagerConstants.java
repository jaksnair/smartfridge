/**
 * SmartFridgeManagerConstants
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.constants;

public class SmartFridgeManagerConstants {



    public enum ContainerType {

        BOTTLE ("Bottle"),
        JAR ("Jar"),
        ONETYPE("One Type");

        private String type;

        ContainerType(String iType) {
            type = iType;
        }

        public String getType() {
            return type;
        }
    }

    public enum SmartFridgeItemType {

        SOLID ("Solid"),
        LIQUID ("Liquid"),
        ONETYPE("One Type");

        private String type;

        SmartFridgeItemType(String iType) {
            type = iType;
        }

        public String getType() {
            return type;
        }
    }

}
