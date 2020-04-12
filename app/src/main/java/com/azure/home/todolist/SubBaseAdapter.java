package com.azure.home.todolist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HOME on 2017-11-08.
 */

public class SubBaseAdapter extends BaseAdapter{
    Context context;
    ArrayList<SubitemActivity> list_itemArrayList;
    ViewHolder viewholder;


    public SubBaseAdapter(Context context, ArrayList<SubitemActivity> list_itemArrayList,Activity parentactivity) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_subitem, null);

            viewholder = new ViewHolder();
            viewholder.subNameView = (TextView)convertView.findViewById(R.id.subName);
            viewholder.subdayView = (TextView)convertView.findViewById(R.id.subDay);
            viewholder.subYearView= (TextView)convertView.findViewById(R.id.subYear);
            viewholder.subProfessorView= (TextView)convertView.findViewById(R.id.subProfessor);
            convertView.setTag(viewholder);

        } else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        viewholder.subNameView.setText(list_itemArrayList.get(position).getSubName());
        viewholder.subdayView.setText(list_itemArrayList.get(position).getSubDay());
        viewholder.subYearView.setText(list_itemArrayList.get(position).getSubYear());
        viewholder.subProfessorView.setText(list_itemArrayList.get(position).getSubProfessor());

        return convertView;
    }

    class ViewHolder {

        TextView subNameView;
        TextView subdayView;
        TextView subYearView;
        TextView subProfessorView;
    }
}
