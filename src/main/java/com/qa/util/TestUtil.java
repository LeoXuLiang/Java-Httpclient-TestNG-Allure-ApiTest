package com.qa.util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class TestUtil {

    /**
     *
     * @param responseJson ,这个变量是拿到响应字符串通过json转换成json对象
     * @param jpath,这个jpath指的是用户想要查询json对象的值的路径写法
     * jpath写法举例：1) per_page  2)data[1]/first_name ，data是一个json数组，[1]表示索引
     * /first_name 表示data数组下某一个元素下的json对象的名称为first_name
     * @return，返回first_name这个json对象名称对应的值
     */


    //1 json解析方法

    public static String getValueByJPath(JSONObject responseJson, String jpath){

        //注意是Object obj,不是Objectobj
        Object obj = responseJson;

        for(String s : jpath.split("/")) {

            if(!s.isEmpty()) {

                if(!(s.contains("[") || s.contains("]"))) {

                    obj = ((JSONObject) obj).get(s);

                }else if(s.contains("[") || s.contains("]")) {

                    obj =((JSONArray)((JSONObject)obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));

                }
            }
        }
        return obj.toString();
    }
}