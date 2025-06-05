package com.chinazes.chinazes.model;

public class Playeralt {
    private long user_id;
    private long votes_up;
    private long comms_count;

    public Playeralt(long user_id, long votes_up, long comms_count) {
        this.user_id = user_id;
        this.votes_up = votes_up;
        this.comms_count = comms_count;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getVotes_up() {
        return votes_up;
    }

    public void setVotes_up(long votes_up) {
        this.votes_up = votes_up;
    }

    public long getComms_count() {
        return comms_count;
    }

    public void setComms_count(long comms_count) {
        this.comms_count = comms_count;
    }
}
