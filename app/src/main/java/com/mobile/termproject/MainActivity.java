package com.mobile.termproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    ImageView ivMainlevel;
    TextView tvMainnick, tvMainExpr;
    Button btnMainsolve, btnMainnsolve, btnMaintest, btnMainboard, btnMainmy;
    CalendarView cvMain;
    ListView lvMtodo;
    ArrayList<SerialSolvedInfo> basic, result;

    String nick;
    int expr=0;
    String uid;
    String year, month, day;
    AdapterSolve adapterSolve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("홈 화면");
        Intent intent = getIntent();
        nick = intent.getStringExtra("nick");
        uid = intent.getStringExtra("uid");
        ivMainlevel = findViewById(R.id.ivMainlevel);
        tvMainnick = findViewById(R.id.tvMainnick);
        tvMainExpr=findViewById(R.id.tvMainExpr);
        btnMainsolve =findViewById(R.id.btnMainsolve);
        btnMainnsolve=findViewById(R.id.btnMainnsolve);
        btnMaintest=findViewById(R.id.btnMaintest);
        btnMainboard=findViewById(R.id.btnMainboard);
        btnMainmy=findViewById(R.id.btnMainmy);
        cvMain=findViewById(R.id.cvMain);
        lvMtodo=findViewById(R.id.lvMtodo);

        btnMainsolve.setOnClickListener(onClickListener);
        btnMainnsolve.setOnClickListener(onClickListener);
        btnMaintest.setOnClickListener(onClickListener);
        btnMainboard.setOnClickListener(onClickListener);
        btnMainmy.setOnClickListener(onClickListener);
        tvMainnick.setText(nick);
        try {
            basic=new Todo().execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result=new ArrayList<>();
        adapterSolve=new AdapterSolve(getApplicationContext(), result);
        lvMtodo.setAdapter(adapterSolve);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Date mdate = new Date(System.currentTimeMillis());
        for(int c=0;c<basic.size();c++){
            if(basic.get(c).qdate.equals(simpleDateFormat.format(mdate)))result.add(basic.get(c));
        }
        adapterSolve.notifyDataSetChanged();

        try {
            expr = new Expr().execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(expr<100){
            ivMainlevel.setImageResource(R.drawable.calculate_24);
            tvMainExpr.setText(expr+"/100");
        }
        else if(expr<1000){
            ivMainlevel.setImageResource(R.drawable.computer_24);
            tvMainExpr.setText(expr+"/1000");
        }
        else {
            ivMainlevel.setImageResource(R.drawable.computer_24);
            tvMainExpr.setText(expr+"/10000");
        }

        cvMain.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                result.clear();
                year=String.valueOf(i);
                if(i1+1<10)month="0"+(i1+1);
                else month=String.valueOf(i1+1);
                if(i2<10)day="0"+i2;
                else day=String.valueOf(i2);
                String date=year+month+day;
                for(int c=0;c<basic.size();c++){
                    if(basic.get(c).qdate.equals(date))result.add(basic.get(c));
                }
                adapterSolve.notifyDataSetChanged();
            }
        });

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){
                case R.id.btnMaintest:
                    intent=new Intent(getApplicationContext(), ContestActivity.class);
                    break;
                case R.id.btnMainboard:
                    intent=new Intent(getApplicationContext(), QuestionListActivity.class);
                    break;
                case R.id.btnMainmy:
                    intent=new Intent(getApplicationContext(), MainActivity.class);
                    break;
                case R.id.btnMainsolve:
                    intent=new Intent(getApplicationContext(), SolvedActivity.class);
                    intent.putExtra("issolve", true);
                    break;
                case R.id.btnMainnsolve:
                    intent=new Intent(getApplicationContext(), SolvedActivity.class);
                    intent.putExtra("issolve", false);
                    break;
                default:
                    return;
            }
            intent.putExtra("nick", nick);
            intent.putExtra("uid", uid);
            finish();
            startActivity(intent);
        }
    };

    @SuppressLint("StaticFieldLeak")
    private class Expr extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<Integer> call = httpInterface.getExpr(strings[0]);
            Integer response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class Todo extends AsyncTask<String, Void, ArrayList<SerialSolvedInfo>> {
        @Override
        protected ArrayList<SerialSolvedInfo> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<ArrayList<SerialSolvedInfo>> call = httpInterface.getTodo(strings[0]);
            ArrayList<SerialSolvedInfo> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

}