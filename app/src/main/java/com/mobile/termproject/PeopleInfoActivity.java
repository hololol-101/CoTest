package com.mobile.termproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import android.content.Intent;
public class PeopleInfoActivity extends AppCompatActivity {

    GridView gridview;
    PeopleAdapter peopleAdapter;
    ArrayList<PeopleItem> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        setTitle("대회 현황");
        String day = intent.getStringExtra("day");
        setContentView(R.layout.activity_people_info);
        gridview=findViewById(R.id.gridview);


        try {
            arrayList=new GetPeople().execute(day).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        peopleAdapter=new PeopleAdapter(getApplicationContext(), arrayList);
        gridview.setAdapter(peopleAdapter);

    }

}


@SuppressLint("StaticFieldLeak")
class GetPeople extends AsyncTask<String, Void, ArrayList<PeopleItem>> {
    @Override
    protected ArrayList<PeopleItem> doInBackground(String... strings) {
        HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
        Call<ArrayList<PeopleItem>> call = httpInterface.getPeople(strings[0]);
        ArrayList<PeopleItem> response = null;
        try {
            response = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
