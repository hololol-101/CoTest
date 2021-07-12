package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    EditText edtId, edtPw;
    Button btnLogin, btnRegist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인");
        edtId=findViewById(R.id.edtId);
        edtPw=findViewById(R.id.edtPw);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegist=findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id, pw, nick;
                id=edtId.getText().toString();
                pw=edtPw.getText().toString();
                String result = null;
                try {
                    result = new GetUid().execute(id,pw).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("ddddddddddddddddddddddd", result);
                if(result.equals("\"failed\"")==false){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("nick", result);
                    intent.putExtra("uid", id);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private class GetUid extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<String> call = httpInterface.getUid(strings[0], strings[1]);
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

}