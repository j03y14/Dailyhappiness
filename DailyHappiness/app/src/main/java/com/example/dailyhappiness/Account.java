package com.example.dailyhappiness;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Account {
    public static String getId() {
        return id;
    }

    public static String getPw() {
        return pw;
    }

    public static String getGender() {
        return gender;
    }

    public static String getAge() {
        return age;
    }

    public static void setId(String id) {
        Account.id = id;
    }

    public static void setPw(String pw) {
        Account.pw = pw;
    }

    public static void setGender(String gender) {
        Account.gender = gender;
    }

    public static void setAge(String age) {
        Account.age = age;
    }

    static String id = "";
    static String pw = "";
    static String gender = "";
    static String age = "";

//    static  String PREF_ID = "";
//    static  String PREF_PW = "";
//    static String gender = "";
//    static String age = "";
//
//    static SharedPreferences getSharedPreferences(Context context){
//        return PreferenceManager.getDefaultSharedPreferences(context);
//    }
//
//    //계정 정보 저장
//    public static void setID(Context context, String id){
//        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.putString(PREF_ID,id);
//        editor.commit();
//    }
//
//    public static void setPW(Context context, String pw){
//        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.putString(PREF_ID,pw);
//        editor.commit();
//    }
//
//    //저장된 정보 가져오기
//    public static String getID(Context context){
//        return getSharedPreferences(context).getString(PREF_ID, "");
//    }
//
//    public static String getPW(Context context){
//        return getSharedPreferences(context).getString(PREF_PW, "");
//    }
//
//    //로그아웃
//    public static void clearID(Context context){
//        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.clear();
//        editor.commit();
//    }
//    public static void clearPW(Context context){
//        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.clear();
//        editor.commit();
//    }
}
