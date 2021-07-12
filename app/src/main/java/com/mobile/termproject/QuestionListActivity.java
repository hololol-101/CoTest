package com.mobile.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Intent;

import retrofit2.Call;

public class QuestionListActivity extends AppCompatActivity {
    Button btnQset;
    EditText etQtext;
    ListView lvQlist;
    String nick;
    String uid;
    int isNum = 0;
    Button btnMaintest2, btnMainboard2, btnMainmy2;
    AdapterSearch adapterSearch;

    ArrayList<SerialQuestionInfo> basicArray, resultArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        setTitle("기출 문제 검색");

        btnQset = findViewById(R.id.btnQset);
        etQtext = findViewById(R.id.etQtext);
        lvQlist = findViewById(R.id.lvQlist);
        Intent getintent = getIntent();
        nick=getintent.getStringExtra("nick");
        uid=getintent.getStringExtra("uid");
        basicArray=new ArrayList<>();
        resultArray=new ArrayList<>();
        btnMaintest2=findViewById(R.id.btnMaintest2);
        btnMainboard2=findViewById(R.id.btnMainboard2);
        btnMainmy2=findViewById(R.id.btnMainmy2);
        btnMaintest2.setOnClickListener(onClickListener);
        btnMainboard2.setOnClickListener(onClickListener);
        btnMainmy2.setOnClickListener(onClickListener);
        adapterSearch=new AdapterSearch(getApplicationContext(), resultArray);
        lvQlist.setAdapter(adapterSearch);
        try {
            basicArray.addAll(new getQustsionList().execute(nick).get());
            resultArray.addAll(basicArray);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lvQlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                SerialQuestionInfo serialQuestionInfo=resultArray.get(i);
                Bundle bundle =new Bundle();
                bundle.putSerializable("serial", serialQuestionInfo);
                intent.putExtra("bundle", bundle);
                intent.putExtra("nick", nick);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        btnQset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.searchset, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String str=new String();
                        switch(menuItem.getItemId()){
                            case R.id.id:
                                str="백준 번호";
                                isNum=1;
                                etQtext.setText("");
                                break;
                            case R.id.name:
                                str="문제 명";
                                isNum=-1;
                                etQtext.setText("");
                                break;
                        }
                        btnQset.setText(str);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        etQtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                resultArray.clear();
                if(isNum==0){
                    resultArray.addAll(basicArray);
                }
                else if(isNum==1) searchId(etQtext.getText().toString());
                else if(isNum==-1) searchTitle(etQtext.getText().toString());
            }
        });
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){
                case R.id.btnMaintest2:
                    intent=new Intent(getApplicationContext(), ContestActivity.class);
                    break;
                case R.id.btnMainboard2:
                    intent=new Intent(getApplicationContext(), QuestionListActivity.class);
                    break;
                case R.id.btnMainmy2:
                    intent=new Intent(getApplicationContext(), MainActivity.class);
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
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("nick", nick);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
    void searchId(String str){
        if(str.length()==0) resultArray.addAll(basicArray);
        else{
            for(int i=0;i<basicArray.size();i++){
                if(String.valueOf(basicArray.get(i).qbnum).toLowerCase().contains(str))
                    resultArray.add(basicArray.get(i));
            }
        }
        adapterSearch.notifyDataSetChanged();
    }
    void searchTitle(String str){
        if(str.length()==0) resultArray.addAll(basicArray);
        else{
            for(int i=0;i<basicArray.size();i++){
                if(basicArray.get(i).qtitle.toLowerCase().contains(str))
                    resultArray.add(basicArray.get(i));
            }
        }
        adapterSearch.notifyDataSetChanged();


    }
    @SuppressLint("StaticFieldLeak")
    private class getQustsionList extends AsyncTask<String, Void, ArrayList<SerialQuestionInfo>> {

        @Override
        protected ArrayList<SerialQuestionInfo> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<ArrayList<SerialQuestionInfo>> call = httpInterface.getQusetions(strings[0]);
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