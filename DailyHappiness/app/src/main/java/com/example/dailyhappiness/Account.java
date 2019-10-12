package com.example.dailyhappiness;

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

}
