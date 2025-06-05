package com.chinazes.chinazes.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="Comms")
public class Comms {
    @Id
    private long id;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "parent_id")
    private long parent_id;

    @Column(name = "commentable_type")
    private String commentable_type;

    @Column(name = "commentable_id")
    private long commentable_id;

    @Column(name = "msg_text")
    private String msg_text;

    @Column(name = "msg_date")
    private Timestamp msg_date;

    @Column(name = "votes")
    private int votes;

    public Comms()
    {

    }

    public Comms(long id, long user_id, long parent_id, String commentable_type, long commentable_id, String msg_text, Timestamp msg_date, int votes) {
        this.id = id;
        this.user_id = user_id;
        this.parent_id = parent_id;
        this.commentable_type = commentable_type;
        this.commentable_id = commentable_id;
        this.msg_text = msg_text;
        this.msg_date = msg_date;
        this.votes = votes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Timestamp getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(Timestamp msg_date) {
        this.msg_date = msg_date;
    }

    public String getMsg_text() {
        return msg_text;
    }

    public void setMsg_text(String msg_text) {
        this.msg_text = msg_text;
    }

    public long getCommentable_id() {
        return commentable_id;
    }

    public void setCommentable_id(long commentable_id) {
        this.commentable_id = commentable_id;
    }

    public String getCommentable_type() {
        return commentable_type;
    }

    public void setCommentable_type(String commentable_type) {
        this.commentable_type = commentable_type;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
