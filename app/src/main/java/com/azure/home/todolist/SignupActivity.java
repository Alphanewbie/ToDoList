package com.azure.home.todolist;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText idText = (EditText) findViewById(R.id.idName);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);

        Button signUpButton = (Button)findViewById(R.id.signup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=idText.getText().toString();
                String userPassword =passwordText.getText().toString();

                if(userID.contains(" "))  {
                    successToast("아이디에 띄어쓰기를 없애주세요");
                    return;
                }
                if(userID.equals(""))  {
                    successToast("아이디를 적어주세요");
                    return;
                }
                if(userPassword.contains(" "))
                {
                    successToast("비밀번호에 띄어쓰기를 없애주세요");
                    return;
                }
                if(userPassword.equals(""))
                {
                    successToast("비밀번호를 적어주세요");
                    return;
                }
                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                successToast("회원가입 성공");
                                Intent BACK = new Intent(SignupActivity.this, MainActivity.class);
                                BACK.putExtra("userID", "");
                                SignupActivity.this.startActivity(BACK);
                                finish();
                            }
                            else {
                                successToast("이미 사용중인 아이디입니다");
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,reponseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    public void successToast(String result){
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
    public void onBackPressed() {
        // your code.
        Intent BACK = new Intent(SignupActivity.this, MainActivity.class);
        BACK.putExtra("userID", "");
        SignupActivity.this.startActivity(BACK);
        finish();
    }
}