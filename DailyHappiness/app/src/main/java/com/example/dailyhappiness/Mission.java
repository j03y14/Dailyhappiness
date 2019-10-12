package com.example.dailyhappiness;

public class Mission {

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

    static String todayMission = "자전거 30분 이상 타기";
    static int missionNumber = 0;
}
