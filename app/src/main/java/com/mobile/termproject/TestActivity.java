package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class TestActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    private static final int MILLISINFUTURE = 60*60*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    int hour, sec, min;
    String nick, uid;
    WebView backurl;
    ArrayList<SerailTest> arrayList;
    TextView timeview;
    Button q1, q2, q3, q4, complete;
    String day;
    int time;
    boolean check=false;
    @Override
    public void onBackPressed() {
        if(check==false){
            Toast.makeText(getApplicationContext(), "코딩을 실패하셨습니다.", Toast.LENGTH_SHORT).show();
            new PutPeople().execute(day, nick, "Failed");
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle("코딩 테스트");
        Intent intent = getIntent();
        nick = intent.getStringExtra("nick");
        uid = intent.getStringExtra("uid");
        Bundle bundle=intent.getBundleExtra("bundle");
        arrayList =(ArrayList<SerailTest>)bundle.getSerializable("test");
        day=intent.getStringExtra("day");
        time = arrayList.get(0).time;
        hour=time;
        timeview=findViewById(R.id.timeview);
        q1=findViewById(R.id.q1); q1.setOnClickListener(onClickListener);
        q2=findViewById(R.id.q2); q2.setOnClickListener(onClickListener);
        q3=findViewById(R.id.q3); q3.setOnClickListener(onClickListener);
        q4=findViewById(R.id.q4); q4.setOnClickListener(onClickListener);
        complete=findViewById(R.id.complete);
        backurl=findViewById(R.id.backurl);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int h, m, s;
                s =time*60*60 - hour*60*60 -min*60 -sec;
                h = s/3600;
                m = s%3600/60;
                s = s%60;
                String t;
                if(h<10)t="0"+h;
                else t = h+"";
                if(m<10)t+="0"+m;
                else t+=""+m;
                if(s<10)t+="0"+s;
                else t+=s+"";
                new PutPeople().execute(day, nick, t);
                countDownTimer.onFinish();
                complete.setEnabled(false);
                check=true;
            }
        });
        countDownTimer();
        countDownTimer.start();
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           String url=null;
            q1.setBackgroundColor(Color.parseColor("#AAD4DA"));
            q2.setBackgroundColor(Color.parseColor("#AAD4DA"));
            q3.setBackgroundColor(Color.parseColor("#AAD4DA"));
            q4.setBackgroundColor(Color.parseColor("#AAD4DA"));
            switch(view.getId()){
                case R.id.q1:
                    url=arrayList.get(0).qurl;
                    int r,g,b;
                    q1.setBackgroundColor(Color.parseColor("#65A1A0"));
                    break;
                case R.id.q2:
                    url=arrayList.get(1).qurl;
                    q2.setBackgroundColor(Color.parseColor("#65A1A0"));
                    break;
                case R.id.q3:
                    url=arrayList.get(2).qurl;
                    q3.setBackgroundColor(Color.parseColor("#65A1A0"));
                    break;
                case R.id.q4:
                    url=arrayList.get(3).qurl;
                    q4.setBackgroundColor(Color.parseColor("#65A1A0"));
                    break;
            }
            backurl.getSettings().setJavaScriptEnabled(true);
            backurl.loadUrl(url);
            backurl.setWebChromeClient(new WebChromeClient());
        }
    };
    public void countDownTimer(){

        countDownTimer = new CountDownTimer(MILLISINFUTURE*time, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                timeview.setText(hour+":"+min+":"+sec);
                sec--;
                if(sec<0){
                    min--;
                    sec=59;
                }
                if(min<0){
                    hour--;
                    min=59;
                }
            }
            public void onFinish() {
                timeview.setText(String.valueOf("Finish ."));
                countDownTimer.cancel();
                complete.setEnabled(false);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    static
    class PutPeople extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<String> call = httpInterface.putPeople(strings[0], strings[1], strings[2]);
            String response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class getContest extends AsyncTask<String, Void, ArrayList<SerailTest>> {
        @Override
        protected ArrayList<SerailTest> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<ArrayList<SerailTest>> call = httpInterface.getTest(strings[0]);
            ArrayList<SerailTest> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}