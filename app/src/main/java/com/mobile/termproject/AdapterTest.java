package com.mobile.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTest extends BaseAdapter {
    private Context context;
    private LayoutInflater inflate;
    private AdapterTest.ViewHolder viewHolder = new AdapterTest.ViewHolder();
    private ArrayList<SerailTest> list;

    public AdapterTest(Context context, ArrayList<SerailTest> list) {
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
            convertView = inflate.inflate(R.layout.sample,null);

            viewHolder = new AdapterTest.ViewHolder();
            viewHolder.ctnum =  convertView.findViewById(R.id.ctnum);
            viewHolder.ctbnum =  convertView.findViewById(R.id.ctbnum);
            viewHolder.cname =  convertView.findViewById(R.id.cname);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (AdapterTest.ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.ctnum.setText((position+1) +". ");
        viewHolder.cname.setText(list.get(position).qname);
        viewHolder.ctbnum.setText("("+list.get(position).qbnum + "번)");

        return convertView;
    }
    static class ViewHolder{
        public TextView ctnum, ctbnum, cname;


    }
}