package com.zeljko.videoservice.utils;

public class Utils {
    public static String removeFileExt(String fileName) {
        int extIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, extIndex);
    }
}
