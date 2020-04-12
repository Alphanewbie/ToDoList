package com.azure.home.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TodoActivity extends AppCompatActivity {

    SwipeMenuListView listView;
    TodoBaseAdaoter myListAdapter;
    ArrayList<TodoitemActivity> list_itemArrayList;
    StringBuilder stringBuilder;
    TextView subName;
    int stoper;
    String userID;
    String subjectName;
    String todoHead2;

    int option=0;
    int option_updown=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        stoper = 0;
        Intent intent=getIntent();
        userID =intent.getStringExtra("userID");
        subjectName =intent.getStringExtra("subjectName");

        listView = (SwipeMenuListView)findViewById(R.id.todolist);
        list_itemArrayList = new ArrayList<TodoitemActivity>();
        subName = (TextView)findViewById(R.id.txtSubjectName);
        subName.setText(subjectName);

        body();
        sort();




        SwipeMenuCreator creator = new SwipeMenuCreator() {
            // 스와이프 넣기
            @Override
            public void create(SwipeMenu menu) {

                // 완료 안된 상태
                SwipeMenuItem noclear = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                noclear.setBackground(new ColorDrawable(Color.rgb(80,
                        240, 255)));
                // set item width
                noclear.setWidth(170);
                // set a icon
                noclear.setTitle("완료\n설정");
                noclear.setTitleSize(18);
                // set item title font color
                noclear.setTitleColor(Color.WHITE);


                // 중요 안된 상태
                SwipeMenuItem noimportant = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                noimportant.setBackground(new ColorDrawable(Color.rgb(245,
                        130, 65)));
                // set item width
                noimportant.setWidth(170);
                // set a icon
                noimportant.setTitle("중요\n설정");
                noimportant.setTitleSize(18);
                // set item title font color
                noimportant.setTitleColor(Color.WHITE);

                // 완료 된 상태
                SwipeMenuItem yesclear = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                yesclear.setBackground(new ColorDrawable(Color.rgb(255,
                        255, 255)));
                // set item width
                yesclear.setWidth(170);
                // set a icon
                yesclear.setTitle("완료\n해제");
                yesclear.setTitleSize(18);
                // set item title font color
                yesclear.setTitleColor(Color.BLACK);


                // 중요 된 상태
                SwipeMenuItem yesimportant = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                yesimportant.setBackground(new ColorDrawable(Color.rgb(255,
                        255, 255)));
                // set item width
                yesimportant.setWidth(170);
                // set a icon
                yesimportant.setTitle("중요\n해제");
                yesimportant.setTitleSize(18);
                // set item title font color
                yesimportant.setTitleColor(Color.BLACK);


                switch(menu.getViewType()) {
                    // 중요0 완료0
                    case(0):
                        menu.addMenuItem(noclear);
                        menu.addMenuItem(noimportant);
                        break;

                    // 중요0 완료1
                    case(1):
                        menu.addMenuItem(yesclear);
                        menu.addMenuItem(noimportant);
                        break;

                    // 중요1 완료0
                    case(2):
                        menu.addMenuItem(noclear);
                        menu.addMenuItem(yesimportant);
                        break;

                    // 중요1 완료1
                    case(3):
                        menu.addMenuItem(yesclear);
                        menu.addMenuItem(yesimportant);
                        break;

                }
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        list_itemArrayList.get(position).setclearchange(); // important(배경색 저장용)값을 false로 설정
                        Response.Listener<String> reponseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        TodoClear registerRequest = new TodoClear(list_itemArrayList.get(position).getTodoHead(),list_itemArrayList.get(position).gettodoclear(),
                                userID,subjectName,reponseListener);
                        RequestQueue queue = Volley.newRequestQueue(TodoActivity.this);
                        queue.add(registerRequest);
                        sort();
                        myListAdapter.notifyDataSetChanged(); // 값변경알려줌
                        break;
                    case 1:
                        list_itemArrayList.get(position).setImportantchange();
                        reponseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Todoimport Request = new Todoimport(list_itemArrayList.get(position).getTodoHead(),list_itemArrayList.get(position).gettodoimport(),
                                userID,subjectName,reponseListener);
                        RequestQueue queue1 = Volley.newRequestQueue(TodoActivity.this);
                        queue1.add(Request);
                        sort();
                        myListAdapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        final TextView todoSort = (TextView)findViewById(R.id.todoSort);
        todoSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.sort_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                option=0;
                                break;
                            case R.id.m2:
                                option=1;
                                break;
                            case R.id.m3:
                                option=2;
                                break;
                            case R.id.m4:
                                option=3;
                                break;
                        }
                        sort();
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
            }
        });

        TextView todoSortup = (TextView)findViewById(R.id.todoSortupdown);
        todoSortup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 오름차순, 내림차순 클릭시 옵션 변경(0 = 오름차순(빠른), 1 = 내림차순(느린))
                if(option_updown == 0)
                    option_updown = 1;
                else
                    option_updown = 0;
                sort();
            }
        });

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                Intent TodoenIntent = new Intent(TodoActivity.this, Todoenroll.class);
                                TodoenIntent.putExtra("userID", userID);
                                TodoenIntent.putExtra("subjectName", subjectName);
                                TodoActivity.this.startActivity(TodoenIntent);
                                finish();
                                break;
                            case R.id.m2:
                                Intent Todohidden = new Intent(TodoActivity.this, Todohiddenshow.class);
                                Todohidden.putExtra("userID", userID);
                                Todohidden.putExtra("subjectName", subjectName);
                                TodoActivity.this.startActivity(Todohidden);
                                finish();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
            }
        });




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                PopupMenu popup= new PopupMenu(TodoActivity.this, view);//view는 오래 눌러진 뷰를 의미
                if(list_itemArrayList.get(position).gettodoclear().equals("1"))
                    getMenuInflater().inflate(R.menu.menu1, popup.getMenu());
                else
                    getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                final int index= position;
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch(item.getItemId()) {
                            case R.id.m1:
                                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if(success) {
                                                successToast("삭제 완료");
                                                list_itemArrayList.remove(list_itemArrayList.get(index));
                                                myListAdapter.notifyDataSetChanged();
                                            }
                                            else
                                                successToast("삭제 실패");
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                todoHead2 = list_itemArrayList.get(index).getTodoHead();
                                DeletetodoRequset registerRequest = new DeletetodoRequset(userID,subjectName,todoHead2,reponseListener);
                                RequestQueue queue = Volley.newRequestQueue(TodoActivity.this);
                                queue.add(registerRequest);
                                myListAdapter.notifyDataSetChanged();
                                break;
                            case R.id.m2:
                                Intent TodoenIntent = new Intent(TodoActivity.this, TodoFixActivity.class);
                                    TodoenIntent.putExtra("userID", userID);
                                    TodoenIntent.putExtra("subjectName", subjectName);
                                    TodoenIntent.putExtra("todoHead", list_itemArrayList.get(index).getTodoHead());
                                    TodoenIntent.putExtra("todoStart", list_itemArrayList.get(index).getTodoStart());
                                    TodoenIntent.putExtra("todoEnd", list_itemArrayList.get(index).getTodoEnd());
                                    TodoenIntent.putExtra("todoEnd2", list_itemArrayList.get(index).getTodoEnd2());
                                TodoActivity.this.startActivity(TodoenIntent);
                                finish();
                                break;

                            case R.id.m3:
                                reponseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if(success) {
                                               //토스트메세지
                                                successToast("숨김 완료");
                                                list_itemArrayList.remove(list_itemArrayList.get(index));
                                                myListAdapter.notifyDataSetChanged();
                                            }
                                            else
                                                successToast("완료된 항목이 아닙니다");
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                Todohiden hiddenRequest = new Todohiden(list_itemArrayList.get(index).getTodoHead(),list_itemArrayList.get(index).getTodohidden(),userID,subjectName,reponseListener);
                                RequestQueue queue1 = Volley.newRequestQueue(TodoActivity.this);
                                queue1.add(hiddenRequest);

                        }

                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
                return true;
            }
        });
    }
    public void successToast(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
        String target;

        @Override
        protected String doInBackground(String... param) {
            try {
                String userID = param[0];
                String subjectName = param[1];
                String target = Common.addr + "todoshow.php";
                URL url = new URL(target);
                String postParameters = "userID=" + userID + "&subjectName=" + subjectName;

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
                while ((temp=bufferedReader.readLine())!=null)
                    stringBuilder.append(temp + "\n");
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
        public void onProgessUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
        public void onPostExcute(String result) {}
    }

    private void body() {
        new BackgroundTask().execute(userID,subjectName);
        myListAdapter = new TodoBaseAdaoter(TodoActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);

        int count=0;
        try {
            while (stoper==0) {sleep(10);}
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray=jsonObject.getJSONArray("response");

            String todoHead;
            while (count<jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                todoHead = object.getString("todoHead");
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date todoStart = transFormat.parse(object.getString("toStart"));
                Date todoEnd = transFormat.parse(object.getString("toEnd"));
                Date todoEnd2 = transFormat.parse(object.getString("toEnd2"));
                String todoclear=object.getString("todoclear");
                String todohidden=object.getString("todohidden");
                String todoimport=object.getString("todoimport");
                TodoitemActivity todoitem= new TodoitemActivity(todoHead,todoStart,todoEnd,todoEnd2,todoclear,todohidden,todoimport);
                list_itemArrayList.add(todoitem);
                count++;
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(TodoActivity.this, TodoActivity.class);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sort() {
        final TextView todoSortup = (TextView)findViewById(R.id.todoSortupdown);
        switch (option_updown) {
            case 0:
                switch (option) {
                    case 0:
                        todoSortup.setText("오름차순");
                        Comparator<TodoitemActivity> textDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item1.getTodoHead().compareTo(item2.getTodoHead());
                            }
                        };
                        Collections.sort(list_itemArrayList, textDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        todoSortup.setText("빠른 순");
                        Comparator<TodoitemActivity> dayDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item1.getTodoEnd().compareTo(item2.getTodoEnd());
                            }
                        };
                        Collections.sort(list_itemArrayList, dayDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        todoSortup.setText("빠른 순");
                        Comparator<TodoitemActivity> realDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item1.getTodoEnd2().compareTo(item2.getTodoEnd2());
                            }
                        };
                        Collections.sort(list_itemArrayList, realDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        todoSortup.setText("완료된 순");
                        Comparator<TodoitemActivity> itDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item2.gettodoclear().compareTo(item1.gettodoclear());
                            }
                        };
                        Collections.sort(list_itemArrayList, itDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                }
                break;
            case 1:
                switch (option)
                {
                    case 0:
                        todoSortup.setText("내림차순");
                        Comparator<TodoitemActivity> textDsc = new Comparator<TodoitemActivity>(){
                            public int compare(TodoitemActivity item1, TodoitemActivity item2){
                                return item2.getTodoHead().compareTo(item1.getTodoHead());
                            }
                        };
                        Collections.sort(list_itemArrayList, textDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        todoSortup.setText("느린 순");
                        Comparator<TodoitemActivity> dayDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item2.getTodoEnd().compareTo(item1.getTodoEnd());
                            }
                        };
                        Collections.sort(list_itemArrayList, dayDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        todoSortup.setText("느린 순");
                        Comparator<TodoitemActivity> realDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item2.getTodoEnd2().compareTo(item1.getTodoEnd2());
                            }
                        };
                        Collections.sort(list_itemArrayList, realDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        todoSortup.setText("미완료 순");
                        Comparator<TodoitemActivity> itDsc = new Comparator<TodoitemActivity>() {
                            public int compare(TodoitemActivity item1, TodoitemActivity item2) {
                                return item1.gettodoclear().compareTo(item2.gettodoclear());
                            }
                        };
                        Collections.sort(list_itemArrayList, itDsc);
                        myListAdapter.notifyDataSetChanged();
                        break;
                }
                break;


        }
    }
}
