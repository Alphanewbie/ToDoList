package com.azure.home.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoitemActivity extends AppCompatActivity {

    private String todoHead;
    private Date todoStart;
    private Date todoEnd;
    private Date todoEnd2;
    private String todoclear;
    private String todohidden;
    private String todoimport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoitem);
    }

    public TodoitemActivity(String todoHead, Date todoStart, Date todoEnd, Date todoEnd2, String todoclear, String todohidden, String todoimport) {
        this.todoHead = todoHead;
        this.todoStart = todoStart;
        this.todoEnd = todoEnd;
        this.todoEnd2 = todoEnd2;
        this.todoclear=todoclear;
        this.todohidden=todohidden;
        this.todoimport=todoimport;
    }

    public String getTodoHead() {
        return todoHead;
    }

    public void setTodoHead(String todoHead) {
        this.todoHead = todoHead;
    }

    public String getTodohidden() {
        return todohidden;
    }

    public void setTodohidden(String todohidden) {
        this.todohidden = todohidden;
    }

    public String gettodoimport() {
        return todoimport;
    }

    public void settodoimport(String todoimport) {
        this.todoimport = todoimport;
    }

    public String getTodoStart() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        return df.format(todoStart);
    }

    public void setTodoStart(Date todoStart) {
        this.todoStart = todoStart;
    }

    public String getTodoEnd() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        return df.format(todoEnd);
    }

    public String getTodoEnd2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        return df.format(todoEnd2);
    }

    public void setTodoEnd(Date write_date) {
        this.todoEnd = write_date;
    }

    public String gettodoclear(){
        return todoclear;
    } // boolean이나 int나 complete값 반환

    final public void settodoclear()
    {
        {
            if(this.todoclear=="1")
                this.todoclear="0";
            else
                this.todoclear="1";
        } // int면 1로설정
    }


    public String setImportantchange() {
        if(this.todoimport.equals("1")) {
            this.todoimport="0";
        }
        else{
            this.todoimport="1";
        }
        return todoimport;
    }

    public String setclearchange() {
        if(this.todoclear.equals("1")) {
            this.todoclear="0";
        }
        else{
            this.todoclear="1";
        }
        return todoclear;
    }
}
