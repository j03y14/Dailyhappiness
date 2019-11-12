package com.example.dailyhappiness;

public class Review {
    int missionNumber;  //미션번호
    String user;        //유저의 id
    String date;        //날짜
    String missionName;     //미션
    String content;     //내용
    int rating;         //평점 (1점 ~ 10점)
    int weather;        //날씨 1: 맑음, 2: 비, 3: 눈, 4: 흐림
    float temperature;  //온도
    String image;     //인증사진이 보여지는 사진 주소를 가지고 있음

    public Review(int missionNumber, String user, String date, String missionName, String content, int rating, int weather, float temperature, String image) {
        this.missionNumber = missionNumber;
        this.date = date;
        this.user = user;
        this.missionName = missionName;
        this.content = content;
        this.rating = rating;
        this.temperature = temperature;
        this.weather = weather;
        this.image = image;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getUser() {
        return user;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getMissionNumber() {
        return missionNumber;
    }

    public String getDate() {
        return date;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public int getWeather() {
        return weather;
    }

    public String getImage() {
        return image;
    }

    public void setMissionNumber(int missionNumber) {
        this.missionNumber = missionNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
