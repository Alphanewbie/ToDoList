package com.azure.home.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SubjectActivity extends AppCompatActivity {

    ListView listView;
    SubBaseAdapter subAdapter;
    ArrayList<SubitemActivity> list_itemArrayList;
    StringBuilder stringBuilder;
    int stoper;
    private Map<String,String> parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        stoper = 0;

        Intent intent=getIntent();
        final String userID =intent.getStringExtra("userID");

        listView = (ListView)findViewById(R.id.subject_list);
        list_itemArrayList = new ArrayList<SubitemActivity>();
        final Button subreButton  = (Button) findViewById(R.id.subregrButton);

        subreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subReIntent = new Intent(SubjectActivity.this, SubRegister.class);
                    subReIntent.putExtra("userID", userID);
                SubjectActivity.this.startActivity(subReIntent);
//                show();
            }
        });


        new BackgroundTask().execute(userID);
        show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubjectActivity.this,TodoActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("subjectName", list_itemArrayList.get(position).getSubName());
                SubjectActivity.this.startActivity(intent);


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                PopupMenu popup= new PopupMenu(SubjectActivity.this, view);//view는 오래 눌러진 뷰를 의미
                getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                final int index= position;

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch( item.getItemId() ){
                            case R.id.m1:
                                String getSubName =   list_itemArrayList.get(index).getSubName();
                                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if(success) {
//                                                subAdapter.notifyDataSetChanged();
                                                successToast("삭제 완료");
                                                list_itemArrayList.remove(list_itemArrayList.get(index));
                                                subAdapter.notifyDataSetChanged();
                                            }
                                            else {
                                                successToast("다시 시도해주세요");
                                            }
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                DeletesubRequset registerRequest = new DeletesubRequset(userID,getSubName,reponseListener);
                                RequestQueue queue = Volley.newRequestQueue(SubjectActivity.this);
                                queue.add(registerRequest);
                                break;
                            case R.id.m2:
                                Intent subfixIntent = new Intent(SubjectActivity.this, SubFixActivity.class);
                                    subfixIntent.putExtra("userID", userID);
                                    subfixIntent.putExtra("subjectName", list_itemArrayList.get(index).getSubName());
                                    subfixIntent.putExtra("subjectDay", list_itemArrayList.get(index).getSubDay());
                                    subfixIntent.putExtra("subjectPro", list_itemArrayList.get(index).getSubProfessor());
                                    subfixIntent.putExtra("subjectyear", list_itemArrayList.get(index).getSubYear());
                                SubjectActivity.this.startActivity(subfixIntent);
                                break;
                        }

                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
                return true;
            }
        });

    }

    public void show() {
        subAdapter = new SubBaseAdapter(SubjectActivity.this,list_itemArrayList,this);
        listView.setAdapter(subAdapter);
        try {
            while (stoper==0) {sleep(10);}
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray=jsonObject.getJSONArray("response");
            int count=0;
            String subjectName,subjectDay,subjectPro,subjectyear;
            while (count<jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                subjectName = object.getString("subjectName");
                subjectDay = object.getString("subjectDay");
                subjectPro = object.getString("subjectPro");
                subjectyear= object.getString("subjectyear");
                SubitemActivity subitem= new SubitemActivity(subjectName,subjectDay,subjectPro,subjectyear);
                list_itemArrayList.add(subitem);
                count++;
            }
            stringBuilder=null;
        }catch (Exception e) {
            e.printStackTrace();
        }


    }


    class BackgroundTask extends AsyncTask<String,Void,String> {
        String target;

        @Override
        protected String doInBackground(String... param) {
            try {
                String userID = param[0];
                target = Common.addr + "subshow.php";
                URL url = new URL(target);
                String postParameters = "userID=" + userID;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                stringBuilder = new StringBuilder();
                while ((temp=bufferedReader.readLine())!=null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                stoper=1;
                return stringBuilder.toString();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("앱을 종료하시겠습니까?");
        builder.setNegativeButton("취소", null);
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }

//    public void onBackPressed() {
//        // your code.
//        Intent BACK = new Intent(SubjectActivity.this, MainActivity.class);
//        BACK.putExtra("userID", "");
//        SubjectActivity.this.startActivity(BACK);
//        finish();
//    }
    public void successToast(String result){
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
