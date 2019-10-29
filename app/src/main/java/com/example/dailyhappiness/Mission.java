package com.example.dailyhappiness;

public class Mission {

    static String todayMission = "";
    static String nextMission = "";
    static int missionNumber = 0;
    static int count = 0;

    public static int getCount() { return count; }

    public static void setCount(int count) { Mission.count = count; }

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