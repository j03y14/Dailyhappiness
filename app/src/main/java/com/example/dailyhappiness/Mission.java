package com.example.dailyhappiness;

public class Mission {

    static String todayMission = "";
    static String nextMission = "";
    static int missionNumber = 0;
    static String count = "";

    public static String getCount() { return count; }

    public static void setCount(String count) { Mission.count = count; }

    public static String getTodayMission() {
        return todayMission;
    }

    public static void setTodayMission(String todayMission) {
        Mission.todayMission = todayMission;
    }

    public static int getMissionNumber() {
        return missionNumber;
    }

    public static void setMissionNumber(int missionNumber) {
        Mission.missionNumber = missionNumber;
    }


}