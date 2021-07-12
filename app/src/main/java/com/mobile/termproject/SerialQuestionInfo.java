package com.mobile.termproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SerialQuestionInfo implements Serializable {
    @SerializedName("qurl")
    public String qurl;
    @SerializedName("qtitle")
    public String qtitle;
    @SerializedName("qbnum")
    public int qbnum;
    @SerializedName("qpnum")
    public int qpnum;
    @SerializedName("solve")
    public int solve;
}
