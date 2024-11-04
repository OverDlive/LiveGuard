package com.example.liveguard_app_010.models;

import com.google.gson.annotations.SerializedName;

public class Timeline {
    private int id;
    private int user;
    private String content;
    private String created_at;

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUser() { return user; }
    public void setUser(int user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}
