package com.zghh.cinema_management.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SeatingInfoUtil {

    //统计座位情况，将JSON数据转换为（剩余座位和选座位）
    public static String statistical(String json){
        List<Character> characters = jsonToList(json);
        int t=0;
        int f=0;
        for (char c:characters){
            if (c=='t'){
                t++;
            }else {
                f++;
            }
        }
        return "剩余座位数："+t+"，已选座位数："+f;
    }

    //将座位情况集合装换为JSON
    public static String listToJson(List<Character> characterList){
        Gson gson = new Gson();
        String j=null;
        //判断集合是否为空
        if (!characterList.isEmpty()) {
            j = gson.toJson(characterList);
        }else {

        }
        //装换成功返回正常字符串，转换失败则返回空字符串
        return j;
    }

    //将座位情况的JSON数据转为List集合
    public static List<Character> jsonToList(String json){
        List<Character> l=new ArrayList<>();
        if (json.startsWith("[")&&json.endsWith("]")){
            String[] split = json.split("\",\"");
            System.out.println(split);
            for (String s:split){
                if (s.length()==1){
                    char[] chars = s.toCharArray();
                    l.add(chars[0]);
                }else {
                    String s1 = s.replace("[\"", "").replace("\"]", "");
                    char[] chars = s1.toCharArray();
                    System.out.println(chars);
                    l.add(chars[0]);
                }
            }
            return l;
        }else {
            return l;
        }
    }
}
