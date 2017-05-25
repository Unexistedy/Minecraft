package com.unexistedy.element.mod.utility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHelper {
    public static File[] list(File folder){
        if (folder!=null&&folder.exists()&&folder.isDirectory())
            return folder.listFiles();
        return null;
    }

    public static Boolean delete(File entry) {
        if (entry.exists()){
            if (entry.isDirectory()){
                File[] files=entry.listFiles();
                if (files==null)
                    return entry.delete();
                else {
                    Boolean clear=true;
                    for (File processing:files){
                        if (!delete(processing))
                            clear=false;
                    }
                    if (clear)
                        return entry.delete();
                    return false;
                }
            }
            else return entry.delete();
        }
        else return true;
    }

    static Map<String,String> readMap(File file) {
        Map<String,String> map=new HashMap<String, String>();
        BufferedReader reader=null;
        if (file.exists()) {
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (reader!=null){
                try {
                    for (String textLine=reader.readLine();textLine!=null;textLine=reader.readLine()){
                        String KEY=getTextKey(textLine);
                        String VALUE=getTextValue(textLine);
                        if (KEY!=null&&VALUE!=null)
                            map.put(KEY,VALUE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    private static String getTextKey(String textIn){
        if (textIn.contains("=")&&textIn.indexOf("=")>0){
            return textIn.substring(0,textIn.indexOf("="));
        }
        return null;
    }
    private static String getTextValue(String textIn){
        if (textIn.contains("=")&&textIn.indexOf("=")<textIn.length()-2){
            return textIn.substring(textIn.indexOf("=")+1);
        }
        return null;
    }
}
