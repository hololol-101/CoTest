package com.mobile.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterSearch extends BaseAdapter {
    private Context context;
    private LayoutInflater inflate;
    private ViewHolder viewHolder = new ViewHolder();
    private ArrayList<SerialQuestionInfo> list;

    public AdapterSearch(Context context, ArrayList<SerialQuestionInfo> list) {
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
            convertView = inflate.inflate(R.layout.list_simple_question,null);

            viewHolder = new ViewHolder();
            viewHolder.tvSname =  convertView.findViewById(R.id.tvSname);
            viewHolder.tvSnum =  convertView.findViewById(R.id.tvSnum);
            viewHolder.tvSBnum =  convertView.findViewById(R.id.tvSBnum);
            viewHolder.ivSchecked = convertView.findViewById(R.id.ivSchecked);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.tvSname.setText(list.get(position).qtitle);

        viewHolder.tvSnum.setText("푼 사람: "+list.get(position).qpnum);
        viewHolder.tvSBnum.setText("백준 번호 "+list.get(position).qbnum);
        if(list.get(position).solve==-1) viewHolder.ivSchecked.setImageResource(R.drawable.ic_baseline_check_circle_24);
        else if(list.get(position).solve==1) viewHolder.ivSchecked.setImageResource(R.drawable.check_circle_green24);
        else if(list.get(position).solve==0) viewHolder.ivSchecked.setImageResource(R.drawable.check_circle_red24);
        return convertView;
    }
    static class ViewHolder{
        public TextView tvSname;
        public TextView tvSnum;
        public TextView tvSBnum;
        public ImageView ivSchecked;


    }
}
