package com.mobile.termproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SerailTest implements Serializable {
    @SerializedName("time")
    public int time;
    @SerializedName("totalnum")
    public int totalnum;
    @SerializedName("qbnum")
    public int qbnum;
    @SerializedName("qurl")
    public String qurl;
    @SerializedName("qname")
    public String qname;
    @SerializedName("day")
    public String day;
}
