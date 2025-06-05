package com.chinazes.chinazes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Players")
public class Players {
    @Id
    private long id;

    @Column(name = "votes_up")
    private int votes_up;

    @Column(name = "comms_count")
    private int comms_count;

    public Players(){

    }

    public Players(long id, int votes_up, int comments_count) {
        this.id = id;
        this.votes_up = votes_up;
        this.comms_count = comments_count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVotes_up() {
        return votes_up;
    }

    public void setVotes_up(int votes_up) {
        this.votes_up = votes_up;
    }

    public int getComms_count() {
        return comms_count;
    }

    public void setComms_count(int comms_count) {
        this.comms_count = comms_count;
    }
}
