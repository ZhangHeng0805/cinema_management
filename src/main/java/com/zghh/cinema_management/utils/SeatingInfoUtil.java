package com.zghh.cinema_management.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SeatingInfoUtil {
    //将表单提交的选座情况转化为集合
    public static List<Integer> subSitToList(String sitInfo){
        ArrayList<Integer> list = new ArrayList<>();
        if (sitInfo!=null){
        if (sitInfo.indexOf(",")<0){//没有逗号分割
            Integer integer = Integer.valueOf(sitInfo);
            list.add(integer);
        }else {
            String[] split = sitInfo.split(",");
            for (String s:split){
                int info=Integer.valueOf(s);
                list.add(info);
            }
        }}
        return list;
    }
    //修改座位信息(sitJson:初始的座位情况的JSON；subSit:修改的座位位置；flag:修改的状态[t/f])
    public static String updateSit(String sitJson,List<Integer> subSit,char flag){
        String s=null;
        List<Character> characters = jsonToList(sitJson);
        if (!characters.isEmpty()) {
            if (flag == 't') {
                for (int i : subSit) {
                    if (i<characters.size()) {//防止索引超过
                        characters.set(i, flag);
                    }
                }
                s = listToJson(characters);
            } else if (flag == 'f') {
                for (int i : subSit) {
                    if (i<characters.size()) {//防止索引超过
                        //判断此位置是否有座
                        if (characters.get(i).equals('t')) {
                            characters.set(i, flag);
                        }
                    }
                }
                s = listToJson(characters);
            }
        }
        //返回为null说明修改失败
        return s;
    }

    //统计座位情况，将JSON数据转换为（剩余座位和已选座位）
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
        }
        //装换成功返回正常字符串，转换失败则返回空字符串
        return j;
    }

    //初始化座位情况(num：座位数,返回null说明座位数错误）
    public static String initSeating(int num){
        String json=null;
        if (num>0){
            List<Character> SeatingInfoList=new ArrayList<>();
            for (int i=1;i<=num;i++){
                SeatingInfoList.add('t');
            }
            json = listToJson(SeatingInfoList);
        }
        return json;
    }
    //将座位情况的JSON数据转为List集合
    public static List<Character> jsonToList(String json){
        List<Character> l=new ArrayList<>();
        if (json.startsWith("[")&&json.endsWith("]")){
            String[] split = json.split("\",\"");
//            System.out.println(split);
            for (String s:split){
                if (s.length()==1){
                    char[] chars = s.toCharArray();
                    l.add(chars[0]);
                }else {
                    String s1 = s.replace("[\"", "").replace("\"]", "");
                    char[] chars = s1.toCharArray();
//                    System.out.println(chars);
                    l.add(chars[0]);
                }
            }
            return l;
        }else {
            return l;
        }
    }
}
