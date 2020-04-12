package com.azure.home.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SubitemActivity extends AppCompatActivity {

    private int profile_image;
    private String subName;
    private String subDay;
    private String subProfessor;
    private String subyear;

    public SubitemActivity( String subName, String subDay, String subProfessor,String subyear) {
        this.subName = subName;
        this.subDay = subDay;
        this.subProfessor = subProfessor;
        this.subyear = subyear;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubDay() {
        return subDay;
    }

    public void setSubDay(String subDay) {
        this.subDay = subDay;
    }

    public String getSubProfessor() {
        return subProfessor;
    }

    public void setSubProfessor(String subProfessor) {
        this.subProfessor = subProfessor;
    }

    public String getSubYear() {
        return subyear;
    }

    public void setSubYear(String subyear) {
        this.subyear = subyear;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subitem);
    }
}
