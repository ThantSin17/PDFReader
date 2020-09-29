package com.stone.pdfreader.appUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {
    public static String getFileSize(String dir){
        long size=new File(dir).length();
        String suffix = null;

        if (size >= 1024) {
            suffix = " Bytes";
            size /= 1024;
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
    public static String getFileDate(String url){
        long dir=new File(url).lastModified();
        SimpleDateFormat format=new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss");
        Date newDate=new Date(dir);
        return format.format(newDate);
    }
}
