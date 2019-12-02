package com.example.dailyhappiness;

import android.util.Log;

import static okhttp3.internal.Internal.instance;

public class Account {

    static String id = "";
    static String pw = "";
    static String gender = "";
    static String age = "";
    static String userIndex = "";
    static int isFirst = 1;
    static String emblem="";
    static int push_notification =0;
    static int missionCount =0;
    static int time_affordable =0;
    static int expense_affordable =0;
    static int didSurvey =0;

    public static int getDidSurvey() {
        return didSurvey;
    }

    public static void setDidSurvey(int didSurvey) {
        Account.didSurvey = didSurvey;
    }

    public static int getTime_affordable() {
        return time_affordable;
    }

    public static void setTime_affordable(int time_affordable) {
        Account.time_affordable = time_affordable;
    }

    public static int getExpense_affordable() {
        return expense_affordable;
    }

    public static void setExpense_affordable(int expense_affordable) {
        Account.expense_affordable = expense_affordable;
    }

    public static String getEmblem() {
        return emblem;
    }

    public static int getMissionCount() {
        return missionCount;
    }

    public static void setMissionCount(int missionCount) {
        Account.missionCount = missionCount;
    }

    public static void setEmblem(String emblem) {
        Account.emblem = emblem;
    }

    public static int getPush_notification() {
        return push_notification;
    }

    public static void setPush_notification(int push_notification) {
        Account.push_notification = push_notification;
    }

    public static int getIsFirst() {
        return isFirst;
    }

    public static void setIsFirst(int isFirst) {
        Account.isFirst = isFirst;
    }

    private static Account instance = null;

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

    public static String getUserIndex() {
        return userIndex;
    }

    public static void setUserIndex(String userIndex) {
        Account.userIndex = userIndex;
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
        if(instance == null){
            instance = new Account();
        }
        return instance;
    }

    public static void Account(String id, String pw, String gender, String age,String name, String time_affordable, String expense_affordable,int isFirst) {
        Log.d("account 생성자", id);
        setAge(age);
        setGender(gender);
        setId(id);
        setPw(pw);
        setIsFirst(isFirst);
    }

}
