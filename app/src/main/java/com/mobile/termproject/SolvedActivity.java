package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class SolvedActivity extends AppCompatActivity {
    ArrayList<SerialQuestionInfo> result;
    String uid, nick;
    AdapterSearch adapterSearch;
    ListView lvSolve;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("nick", nick);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        nick = intent.getStringExtra("nick");
        boolean isSolve = intent.getBooleanExtra("issolve", false );
        lvSolve=findViewById(R.id.lvSolve);
        lvSolve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                SerialQuestionInfo serialQuestionInfo=result.get(i);
                Bundle bundle =new Bundle();
                bundle.putSerializable("serial", serialQuestionInfo);
                intent.putExtra("bundle", bundle);
                intent.putExtra("nick", nick);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });
        if(isSolve==true){
            setTitle("푼 문제");
            try {
                result=new Solve().execute(uid, "1").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            setTitle("못 푼 문제");
            try {
                result=new Solve().execute(uid, "0").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        adapterSearch=new AdapterSearch(getApplicationContext(), result);
        lvSolve.setAdapter(adapterSearch);
    }
    @SuppressLint("StaticFieldLeak")
    private class Solve extends AsyncTask<String, Void, ArrayList<SerialQuestionInfo>> {
        @Override
        protected ArrayList<SerialQuestionInfo> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<ArrayList<SerialQuestionInfo>> call = httpInterface.getSolve(strings[0], Integer.parseInt(strings[1]));
            ArrayList<SerialQuestionInfo> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}