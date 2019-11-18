package com.example.dailyhappiness;

public class MissionCandidate {
    String user;
    String missionName = "";
    int index = 0;
    int likes = 0;
    int dislikes =0;
    int duplicateCount =0;

    boolean likeChecked = false;
    boolean dislikeChecked = false;
    boolean duplicateCountChecked = false;

    public MissionCandidate(String user, String missionName, int index, int likes, int dislikes, int duplicateCount, boolean likeChecked, boolean dislikeChecked, boolean duplicateCountChecked) {
        this.user = user;
        this.missionName = missionName;
        this.index = index;
        this.likes = likes;
        this.dislikes = dislikes;
        this.duplicateCount = duplicateCount;
        this.likeChecked = likeChecked;
        this.dislikeChecked = dislikeChecked;
        this.duplicateCountChecked = duplicateCountChecked;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getDuplicateCount() {
        return duplicateCount;
    }

    public void setDuplicateCount(int duplicateCount) {
        this.duplicateCount = duplicateCount;
    }

    public boolean isLikeChecked() {
        return likeChecked;
    }

    public void setLikeChecked(boolean likeChecked) {
        this.likeChecked = likeChecked;
    }

    public boolean isDislikeChecked() {
        return dislikeChecked;
    }

    public void setDislikeChecked(boolean dislikeChecked) {
        this.dislikeChecked = dislikeChecked;
    }

    public boolean isDuplicateCountChecked() {
        return duplicateCountChecked;
    }

    public void setDuplicateCountChecked(boolean duplicateCountChecked) {
        this.duplicateCountChecked = duplicateCountChecked;
    }
}
