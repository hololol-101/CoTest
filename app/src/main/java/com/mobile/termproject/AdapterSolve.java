package com.mobile.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSolve extends BaseAdapter {
    private Context context;
    private LayoutInflater inflate;
    private AdapterSolve.ViewHolder viewHolder = new AdapterSolve.ViewHolder();
    private ArrayList<SerialSolvedInfo> list;

    public AdapterSolve(Context context, ArrayList<SerialSolvedInfo> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.lv_solve,null);

            viewHolder = new AdapterSolve.ViewHolder();
            viewHolder.lvStv =  convertView.findViewById(R.id.lvStv);
            viewHolder.ivSolveed = convertView.findViewById(R.id.ivSolveed);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (AdapterSolve.ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.lvStv.setText(list.get(position).qbnum+"번 " + list.get(position).qtitle);
        if(list.get(position).solve==1) viewHolder.ivSolveed.setImageResource(R.drawable.check_circle_green24);
        else viewHolder.ivSolveed.setImageResource(R.drawable.check_circle_red24);
        return convertView;
    }
    static class ViewHolder{
        public TextView lvStv;
        public ImageView ivSolveed;


    }
}