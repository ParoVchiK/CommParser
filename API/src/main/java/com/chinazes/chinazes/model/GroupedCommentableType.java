package com.chinazes.chinazes.model;

public class GroupedCommentableType {
    private long commentable_id;
    private long count;
    private long votes;

    public GroupedCommentableType(long commentable_id, long count, long votes) {
        this.commentable_id = commentable_id;
        this.count = count;
        this.votes = votes;
    }


    public long getCommentable_id() {
        return commentable_id;
    }

    public void setCommentable_id(long commentable_id) {
        this.commentable_id = commentable_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}
