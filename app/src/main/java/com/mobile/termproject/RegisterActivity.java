package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {
    EditText edtRid, edtRpw, edtRnick;
    Button btnRregist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("회원가입");

        edtRid=findViewById(R.id.edtRid);
        edtRpw=findViewById(R.id.edtRpw);
        edtRnick=findViewById(R.id.edtRnick);

        btnRregist=findViewById(R.id.btnRregister);
        btnRregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id, pw, nick;
                id = edtRid.getText().toString();
                pw=edtRpw.getText().toString();
                nick=edtRnick.getText().toString();
                String result = null;

                try {
                    result = new Register().execute(id, pw, nick).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("dddddddddd", "result = " +result);
                if(result.equals(new String("\"success\""))){
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    if(result.equals(new String("\"uid\""))){
                        Toast.makeText(getApplicationContext(), "동일한 아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.equals(new String("\"unick\""))){
                        Toast.makeText(getApplicationContext(), "동일한 닉네임이 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private class Register extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<String> call = httpInterface.register(strings[0], strings[1], strings[2]);
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