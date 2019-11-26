package com.example.dailyhappiness;

public class KingList {

    int rank = 0;
    //유저 프로필도 넣어야 함
    String user = "";
    int userIndex = 0;
    int count = 0;
    String emblem = "";

    public KingList(int rank, String user, int userIndex, int count, String emblem) {
        this.rank = rank;
        this.user = user;
        this.userIndex = userIndex;
        this.count = count;
        this.emblem = emblem;
    }

    public int getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(int userIndex) {
        this.userIndex = userIndex;
    }

    public String getEmblem() {
        return emblem;
    }

    public void setEmblem(String emblem) {
        this.emblem = emblem;
    }



    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
