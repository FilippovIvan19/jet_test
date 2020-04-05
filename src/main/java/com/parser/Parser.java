package com.parser;


public class Parser {
    static String curStr;


    private static String getMapArg() {
        int startInd = curStr.indexOf("map") + "map{".length();
        int endInd   = curStr.indexOf("}");
        String mapArg = curStr.substring(startInd, endInd);
        curStr = curStr.substring(endInd + "}".length());
        if (!curStr.equals("")) {
            curStr = curStr.substring("%>%".length());
        }
        return mapArg;
    }


    private static String getFilterArg() {
        int startInd = curStr.indexOf("filter") + "filter{".length();
        int endInd   = curStr.indexOf("}");
        String filterArg = curStr.substring(startInd, endInd);
        curStr = curStr.substring(endInd + "}".length());
        if (!curStr.equals("")) {
            curStr = curStr.substring("%>%".length());
        }
        return filterArg;
    }



    public static String parse(String inStr) {
        curStr = new String(inStr);
        String curReplacement = "element";
        String curFilter = "";

        while (!curStr.equals("")) {
            if (curStr.charAt(0) == 'm') {
                String mapArg = getMapArg();
                curReplacement = mapArg;
                curStr = curStr.replace("element", mapArg);
            } else {
                String filterArg = getFilterArg();
                if (!curFilter.equals("")) {
                    curFilter += "&";
                }
                curFilter += filterArg;
            }
        }

        return String.format("filter{%s}%%>%%map{%s}", curFilter, curReplacement);
    }

}