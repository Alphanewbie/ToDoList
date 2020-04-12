package com.azure.home.todolist;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SubFixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_fix);

        Intent intent=getIntent();
        final String userID =intent.getStringExtra("userID");
        final String subjectName =intent.getStringExtra("subjectName");
        final String subjectDay =intent.getStringExtra("subjectDay");
        final String subjectPro =intent.getStringExtra("subjectPro");
        final String subjectyear =intent.getStringExtra("subjectyear");

        final EditText subNameText = (EditText) findViewById(R.id.subreName);
        final EditText subDayText = (EditText) findViewById(R.id.subreDay);
        final EditText subProText = (EditText) findViewById(R.id.subreProfessor);
        final EditText subyearText = (EditText) findViewById(R.id.subreyear);

        subNameText.setText(subjectName);
        subDayText.setText(subjectDay);
        subProText.setText(subjectPro);
        subyearText.setText(subjectyear);

        Button subregibutton = (Button)findViewById(R.id.subregibutton);

        subregibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectfixName=subNameText.getText().toString();
                String subjectDay =subDayText.getText().toString();
                String subjectPro=subProText.getText().toString();
                String subjectyear=subyearText.getText().toString();

                Intent intent=getIntent();
                final String userID =intent.getStringExtra("userID");

                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder =new AlertDialog.Builder(SubFixActivity.this);
                                builder.setMessage("과목 수정 성공")
                                        .create()
                                        .show();
                                Intent intent = new Intent(SubFixActivity.this, SubjectActivity.class);
                                intent.putExtra("userID", userID);
                                SubFixActivity.this.startActivity(intent);

                                finish();
                            }
                            else {
                                AlertDialog.Builder builder =new AlertDialog.Builder(SubFixActivity.this);
                                builder.setMessage("과목 등록 실패")
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                SubFixRequest registerRequest = new SubFixRequest(userID,subjectfixName,subjectDay,subjectPro,subjectyear,subjectName,reponseListener);
                RequestQueue queue = Volley.newRequestQueue(SubFixActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
