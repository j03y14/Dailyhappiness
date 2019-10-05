package com.example.dailyhappiness;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Account {

    private static Account instance =null;

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

    private Account(){}

    public static Account getInstance(){
        if(instance ==null){
            instance = new Account();
        }
        return instance;
    }

    public static void Account(String id, String pw, String gender, String age,String name, String time_affordable, String expense_affordable) {
        Log.d("account 생성자", id);
        setAge(age);
        setGender(gender);
        setId(id);
        setPw(pw);
    }

    static String id = "";
    static String pw = "";
    static String gender = "";
    static String age = "";
    static String name = "";
    static String time_affordable= "";
    static String expense_affordable = "";

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
