package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class ContestActivity extends AppCompatActivity {
    String nick, uid;
    ListView lvQsimple;
    Button btnCstart;
    TextView tvCqnum, tvCqtime, battlenum, testday;
    ArrayList<SerailTest>question;
    AdapterTest adapterTest;
    ImageView ivPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);
        setTitle("대회 안내");
        //대회 문제 + 타이머 + 지금까지 풀고있는 사람 현황
        Intent intent = getIntent();
        nick = intent.getStringExtra("nick");
        uid = intent.getStringExtra("uid");

        lvQsimple=findViewById(R.id.lvQsimple);
        btnCstart=findViewById(R.id.btnCstart);
        tvCqnum=findViewById(R.id.tvCqnum);
        tvCqtime=findViewById(R.id.tvCqtime);
        testday=findViewById(R.id.testday);
        ivPerson=findViewById(R.id.ivPerson);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String year;
        year = simpleDateFormat.format(date);
        try {
            question=new getContest().execute(year).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapterTest = new AdapterTest(getApplicationContext(), question);
        lvQsimple.setAdapter(adapterTest);
        tvCqnum.setText("뭇제 수: "+question.get(0).totalnum+ " 문제");
        tvCqtime.setText("제한 시간: "+question.get(0).time+" 시간");
        testday.setText(question.get(0).day.substring(0,4)+"년 "+question.get(0).day.substring(5,6)+"월 "+question.get(0).day.substring(7,8)+"일");
        ivPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), PeopleInfoActivity.class);
                intent1.putExtra("nick", nick);
                intent1.putExtra("day", year);
                intent1.putExtra("uid", uid);
                startActivity(intent1);
            }
        });
        btnCstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), TestActivity.class);
                intent1.putExtra("nick", nick);
                intent1.putExtra("uid", uid);
                intent1.putExtra("day", year);
                Bundle bundle = new Bundle();
                bundle.putSerializable("test", question);
                intent1.putExtra("bundle", bundle);
                startActivity(intent1);
                new TestActivity.PutPeople().execute(year, nick, "Coding");
                btnCstart.setEnabled(false);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("nick", nick);
        intent.putExtra("uid", uid);
        finish();
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class PutPeople extends AsyncTask<String, Void, String> {
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