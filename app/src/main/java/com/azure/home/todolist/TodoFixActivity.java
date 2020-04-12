package com.azure.home.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TodoFixActivity extends AppCompatActivity {

    int mYear, mMonth, mDay;
    int mYear2, mMonth2, mDay2;
    int mYear3, mMonth3, mDay3;
    TextView mTxtDate1;
    TextView mTxtDate2;
    TextView mTxtDate3;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_fix);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String subjectName = intent.getStringExtra("subjectName");
        final String todoHead = intent.getStringExtra("todoHead");
        final String todostart=intent.getStringExtra("todoStart");
        final String todoend=intent.getStringExtra("todoEnd");
        final String todoend2=intent.getStringExtra("todoEnd2");

        final EditText idText = (EditText) findViewById(R.id.todoHead);
        idText.setText(todoHead);

        Button todoEnButton = (Button) findViewById(R.id.todoen);

        mTxtDate1 = (TextView) findViewById(R.id.todoStart);
        mTxtDate2 = (TextView) findViewById(R.id.todoEnd);
        mTxtDate3 = (TextView) findViewById(R.id.todoEnd2);
        checkBox = (CheckBox) findViewById(R.id.check);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        mYear2 = cal.get(Calendar.YEAR);
        mMonth2 = cal.get(Calendar.MONTH);
        mDay2 = cal.get(Calendar.DAY_OF_MONTH);

        mYear3 = cal.get(Calendar.YEAR);
        mMonth3 = cal.get(Calendar.MONTH);
        mDay3 = cal.get(Calendar.DAY_OF_MONTH);

        UpdateNow();

        Button todoEnrollButton = (Button) findViewById(R.id.todoen);

        todoEnrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fixtodoHead = idText.getText().toString();
                Date todoStart = new Date((mYear - 1900), mMonth, mDay);
                Date todoEnd = new Date((mYear2 - 1900), mMonth2, mDay2);
                Date todoEnd2 = new Date((mYear2 - 1900), mMonth3, mDay3);

                if(todoStart.compareTo(todoEnd) > 0 || todoStart.compareTo(todoEnd2) > 0 || todoEnd.compareTo(todoEnd2) > 0){
                    successToast("날짜를 정확하게 설정해주세요");
                    return;
                }

                if(todoHead.equals("")) {
                    successToast("항목 이름을 적어주세요");
                    return;
                }
                String todoclear = "0";
                String todoimport;
                if (checkBox.isChecked()) {
                    // TODO : CheckBox is checked.
                    todoimport = "1";
                } else {
                    // TODO : CheckBox is unchecked.
                    todoimport = "0";
                }

                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                String toStart = transFormat.format(todoStart);
                String toEnd = transFormat.format(todoEnd);
                String toEnd2 = transFormat.format(todoEnd2);

                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                successToast("수정 완료");
                                Intent intent = new Intent(TodoFixActivity.this, TodoActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("subjectName", subjectName);
                                TodoFixActivity.this.startActivity(intent);
                                finish();
                            } else {
                                successToast("수정 실패");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                TodoFixRequest registerRequest = new TodoFixRequest(fixtodoHead, toStart, toEnd, toEnd2, todoclear, todoimport, todoHead, userID, subjectName, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(TodoFixActivity.this);
                queue.add(registerRequest);
            }
        });


    }

    public void successToast(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌
            case R.id.todoStart:
                //여기서 리스너도 등록함
                new DatePickerDialog(TodoFixActivity.this, mDateSetListener, mYear, mMonth, mDay).show();

                break;
            case R.id.todoEnd:
                //여기서 리스너도 등록함
                new DatePickerDialog(TodoFixActivity.this, mDateSetListener2, mYear2, mMonth2, mDay2).show();
                break;
            case R.id.todoEnd2:
                //여기서 리스너도 등록함
                new DatePickerDialog(TodoFixActivity.this, mDateSetListener3, mYear3, mMonth3, mDay3).show();
                break;
        }
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            UpdateNow();
        }
    };

    DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            mYear2 = year;
            mMonth2 = monthOfYear;
            mDay2 = dayOfMonth;
            UpdateNow();
        }
    };

    DatePickerDialog.OnDateSetListener mDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            mYear3 = year;
            mMonth3 = monthOfYear;
            mDay3 = dayOfMonth;
            UpdateNow();
        }
    };

    void UpdateNow() {
        mTxtDate1.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));
        mTxtDate2.setText(String.format("%d/%d/%d", mYear2, mMonth2 + 1, mDay2));
        mTxtDate3.setText(String.format("%d/%d/%d", mYear3, mMonth3 + 1, mDay3));

    }
}

