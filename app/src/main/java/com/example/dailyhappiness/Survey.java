package com.example.dailyhappiness;

public class Survey {

    public Survey(int num, String s){
        this.number = num;
        this.mission = s;
    }

    int number = 1;
    String mission = "";
    int score = 1;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }


}
