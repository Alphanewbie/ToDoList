package com.azure.home.todolist;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText idText = (EditText) findViewById(R.id.idName);
        final EditText pass = (EditText) findViewById(R.id.loginpass);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        final TextView signupButton = (TextView) findViewById(R.id.signup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                MainActivity.this.startActivity(signupIntent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID= idText.getText().toString();
                final String userpassword = pass.getText().toString();
                if(userID.equals(""))  {
                    successToast("아이디를 적어주세요");
                    return;
                }
                if(userpassword.equals(""))  {
                    successToast("비밀번호를 적어주세요");
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String temp1 = jsonResponse.getString("temp");

                            if (success) {
                                String userID = jsonResponse.getString("userID");
                                Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                                intent.putExtra("userID", userID);
                                MainActivity.this.startActivity(intent);
                                finish();
                            } else {
                                successToast("아이디 혹은 비밀번호를 확인해주세요");
//                                Toast.makeText(MainActivity.this, temp1, Toast.LENGTH_SHORT).show();
                                successToast(temp1);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID,userpassword ,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });

    }

    public void successToast(String result){
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
