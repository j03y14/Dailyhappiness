package com.example.dailyhappiness;

public class Mission {

    static String todayMission = "";

    static int missionNumber = 0;
    static int count = 0; // 사용자가 이 미션을 넘긴 횟수

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
