package com.azure.home.todolist;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-12-05.
 */

public class TodoClear extends StringRequest {
    static private String URL = Common.addr + "todoclear.php";

    private Map<String,String> parameters;

    public TodoClear(String todoHead,String todoclear,String userID, String subjectName,
                      Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("todoHead",todoHead);
        parameters.put("todoclear",todoclear);
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
    }
    public Map<String,String> getParams() {
        return parameters;
    }
}
