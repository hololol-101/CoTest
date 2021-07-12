package com.mobile.termproject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpInterface {

    @GET("/getUid.php")
    Call<String> getUid(
            @Query("uid") String uid,
            @Query("upw") String upw
    );
    @GET("/getExpr.php")
    Call<Integer> getExpr(
            @Query("unick") String unick
    );
    @GET("/getTodo.php")
    Call<ArrayList<SerialSolvedInfo>> getTodo(
            @Query("unick") String unick
    );
    @GET("/register.php")
    Call<String> register(
            @Query("uid") String uid,
            @Query("upw") String upw,
            @Query("unick") String nick
    );
    @GET("/getQusetions.php")
    Call<ArrayList<SerialQuestionInfo>> getQusetions(
            @Query("unick") String unick
    );
    @GET("/putSolve.php")
    Call<String> putSolve(
            @Query("solve") int solve,
            @Query("uid") String uid,
            @Query("qbnum") int qbnum,
            @Query("qdate") String qdate,
            @Query("qtitle") String qtitle,
            @Query("qtime") String qtime
    );
    @GET("/getSolve.php")
    Call<ArrayList<SerialQuestionInfo>> getSolve(
            @Query("uid") String uid,
            @Query("solve") int solve
    );
    @GET("/getTest.php")
    Call<ArrayList<SerailTest>> getTest(
            @Query("day") String day
    );
    @GET("/getPeople.php")
    Call<ArrayList<PeopleItem>> getPeople(
            @Query("day") String day
    );
    @GET("/putPeople.php")
    Call<String> putPeople(
        @Query("day") String day,
        @Query("nick") String nick,
        @Query("time") String time
    );
}
