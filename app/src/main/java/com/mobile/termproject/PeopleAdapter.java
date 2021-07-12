package com.mobile.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PeopleAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflate;
    private PeopleAdapter.ViewHolder viewHolder = new PeopleAdapter.ViewHolder();
    private ArrayList<PeopleItem> list;

    public PeopleAdapter(Context context, ArrayList<PeopleItem> list) {
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
            convertView = inflate.inflate(R.layout.persondata,null);

            viewHolder = new PeopleAdapter.ViewHolder();
            viewHolder.pnick =  convertView.findViewById(R.id.pnick);
            viewHolder.ptime =  convertView.findViewById(R.id.ptime);
            viewHolder.level =  convertView.findViewById(R.id.level);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (PeopleAdapter.ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.pnick.setText(list.get(position).nick);
        try{
            Integer.parseInt(list.get(position).time);
            viewHolder.ptime.setText(list.get(position).time.substring(0,2)+":"+list.get(position).time.substring(2,4)+":"+list.get(position).time.substring(4,6));
        }catch (Exception e){
            viewHolder.ptime.setText(list.get(position).time);

        }

        if(list.get(position).expr<100) viewHolder.level.setImageResource(R.drawable.calculate_24);
        else if(list.get(position).expr<1000) viewHolder.level.setImageResource(R.drawable.computer_24);
        else viewHolder.level.setImageResource(R.drawable.ic_baseline_android_24);
        return convertView;
    }
    static class ViewHolder{
        public TextView pnick;
        public TextView ptime;
        public ImageView level;
    }
}
