package com.azure.home.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SubRegister extends AppCompatActivity {

    TextView subyearText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subregister);

        final EditText subNameText = (EditText) findViewById(R.id.subreName);
        final EditText subDayText = (EditText) findViewById(R.id.subreDay);
        final EditText subProText = (EditText) findViewById(R.id.subreProfessor);
        subyearText = (TextView) findViewById(R.id.subreyear);

        Button subregibutton = (Button)findViewById(R.id.subregibutton);

        subyearText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MyYearMonthPickerDialog pd = new MyYearMonthPickerDialog();
                pd.setListener(d);

                pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
            }
        });

        subregibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName=subNameText.getText().toString();
                String subjectDay =subDayText.getText().toString();
                String subjectPro=subProText.getText().toString();
                String subjectyear=subyearText.getText().toString();

                if(subjectName.equals(""))
                {
                    successToast("과목 이름을 입력해주세요");
                    return;
                }

                Intent intent=getIntent();
                final String userID =intent.getStringExtra("userID");

                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                successToast("등록 완료");
                                Intent intent = new Intent(SubRegister.this, SubjectActivity.class);
                                    intent.putExtra("userID", userID);
                                SubRegister.this.startActivity(intent);
                                finish();
                            }
                            else {
                                successToast("등록 실패");
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                SubRegisterRequest registerRequest = new SubRegisterRequest(userID,subjectName,subjectDay,subjectPro,subjectyear,reponseListener);
                RequestQueue queue = Volley.newRequestQueue(SubRegister.this);
                queue.add(registerRequest);
            }
        });
    }
    public void successToast(String result){
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
            subyearText.setText(String.format("%d년 %d학기", year, monthOfYear));
        }
    };
}