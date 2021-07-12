package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Query;

public class QuestionActivity extends AppCompatActivity {
    TextView tvQsname, tvBacknum;
    WebView wvQurl;
    String time;
    Chronometer qTimer;
    Button btnQstart, btnQfinish, btnQgiveUP;
    LinearLayout lvQbtn;
    String uid, nick;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
        intent.putExtra("nick", nick);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setTitle("기출 문제");
        //문제, 문제 설명, 타이머
        tvQsname=findViewById(R.id.tvQsname);
        tvBacknum=findViewById(R.id.tvBacknum);
        wvQurl=findViewById(R.id.wvQurl);
        qTimer=findViewById(R.id.qTimer);
        btnQstart=findViewById(R.id.btnQstart);
        btnQfinish=findViewById(R.id.btnQfinish);
        btnQgiveUP=findViewById(R.id.btnQgiveUP);
        lvQbtn=findViewById(R.id.lvQbtn);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        SerialQuestionInfo serialQuestionInfo = (SerialQuestionInfo) bundle.getSerializable("serial");
        uid=intent.getStringExtra("uid");
        nick=intent.getStringExtra("nick");
        tvQsname.setText(serialQuestionInfo.qtitle);
        tvBacknum.setText("백준 번호"+serialQuestionInfo.qbnum);
        wvQurl.getSettings().setJavaScriptEnabled(true);
        wvQurl.loadUrl(serialQuestionInfo.qurl);
        wvQurl.setWebChromeClient(new WebChromeClient());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String year;
        year = simpleDateFormat.format(date);
        btnQstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qTimer.setBase(SystemClock.elapsedRealtime());
                qTimer.start();
                btnQstart.setVisibility(View.INVISIBLE);
                lvQbtn.setVisibility(View.VISIBLE);
            }
        });

        btnQfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qTimer.stop();
                qTimer.setTextColor(Color.BLUE);
                lvQbtn.setVisibility(View.INVISIBLE);
                time=qTimer.getText().toString();
                new PutSolve().execute("1", uid, String.valueOf(serialQuestionInfo.qbnum), year, serialQuestionInfo.qtitle, time);
            }
        });
        btnQgiveUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qTimer.stop();
                qTimer.setTextColor(Color.BLUE);
                time=qTimer.getText().toString();
                lvQbtn.setVisibility(View.INVISIBLE);
                new PutSolve().execute("0", uid, String.valueOf(serialQuestionInfo.qbnum), year, serialQuestionInfo.qtitle, time);
            }
        });



    }
    @SuppressLint("StaticFieldLeak")
    private class PutSolve extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<String> call = httpInterface.putSolve(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2]), strings[3], strings[4], strings[5]);
            String response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}