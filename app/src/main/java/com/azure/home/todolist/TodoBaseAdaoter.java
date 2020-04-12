package com.azure.home.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by HOME on 2017-11-15.
 */

public class TodoBaseAdaoter extends BaseAdapter {
    Context context;
    ArrayList<TodoitemActivity> list_itemArrayList;

    ViewHolder viewholder;


    class ViewHolder{
        TextView todoHead;
        TextView todoStart;
        TextView todoEnd;
        TextView todoEnd2;

    }

    public TodoBaseAdaoter(Context context, ArrayList<TodoitemActivity> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;

    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_todoitem,null);
            viewholder = new ViewHolder();
            viewholder.todoHead = (TextView)convertView.findViewById(R.id.todohead);
            viewholder.todoStart = (TextView)convertView.findViewById(R.id.todoStart);
            viewholder.todoEnd  =(TextView)convertView.findViewById(R.id.todoend);
            viewholder.todoEnd2  =(TextView)convertView.findViewById(R.id.todoend2);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder)convertView.getTag();
        }

   /*     final View v1 =  convertView;
        convertView.findViewById(R.id.check).setOnClickListener(new Button.OnClickListener(){
            public  void onClick(View v){
                CheckBox ch1 = (CheckBox)v.findViewById(R.id.check);
                if(ch1.isChecked()){
                    v1.setBackgroundColor(Color.MAGENTA);
                }else {
                    v1.setBackgroundColor(Color.WHITE);
                }
            }
        });*/

        if(list_itemArrayList.get(position).gettodoimport().equals("1"))
            convertView.setBackgroundColor(Color.rgb(245,130,65)); // true 일시 자주색으로 설정
        else
            convertView.setBackgroundColor(Color.WHITE);// false 일시 하얀색으로 설정

        ImageView imgview = (ImageView)convertView.findViewById(R.id.imgCheck);
        if(list_itemArrayList.get(position).gettodoclear().equals("1"))
            imgview.setImageResource(R.drawable.checked);
        else
            imgview.setImageResource(R.drawable.unchecked);

        viewholder.todoHead.setText(list_itemArrayList.get(position).getTodoHead());
        viewholder.todoStart.setText(list_itemArrayList.get(position).getTodoStart().toString());
        viewholder.todoEnd.setText(list_itemArrayList.get(position).getTodoEnd().toString());
        viewholder.todoEnd2.setText(list_itemArrayList.get(position).getTodoEnd2().toString());
        return convertView;
    }

    public int getViewTypeCount() {
        return 4;
    }

    public int getItemViewType(int position) {
        if(this.list_itemArrayList.get(position).gettodoimport().equals("0"))
        {
            // 중요0, 완료0
            if(this.list_itemArrayList.get(position).gettodoclear().equals("0"))
                return 0;
            // 중요0, 완료1
            else
                return 1;
        }
        if(this.list_itemArrayList.get(position).gettodoimport().equals("1"))
        {
            // 중요1, 완료0
            if(this.list_itemArrayList.get(position).gettodoclear().equals("0"))
                return 2;
            // 중요1, 완료1
            else
                return 3;
        }
        return -1;
    }


}