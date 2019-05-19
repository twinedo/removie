package com.removie.utils;

/*
Created by twin on May 11, 2019
*/

public enum ImageSize {
    w185(185, "w185"),
    w342(342, "w342"),
    w500(500, "w500"),
    w1280(1280,"w1280"),
    original(2160,"original");

    private int key;
    private String value;

    ImageSize(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}