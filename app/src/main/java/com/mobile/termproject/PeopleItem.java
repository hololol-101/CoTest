package com.mobile.termproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PeopleItem implements Serializable {
    @SerializedName("time")
    String time;
    @SerializedName("nick")
    String nick;
    @SerializedName("expr")
    int expr;

    public PeopleItem(String time, String nick, int expr) {
        this.time = time;
        this.nick = nick;
        this.expr = expr;
    }
}
