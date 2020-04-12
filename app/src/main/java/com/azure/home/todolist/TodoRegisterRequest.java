package com.azure.home.todolist;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TodoRegisterRequest extends StringRequest {
    static private String URL = Common.addr + "todoRegister.php";

    private Map<String,String> parameters;

    public TodoRegisterRequest(String todoHead,String toStart,String toEnd,String toEnd2,String todoclear,String todoimport,String userID,String subjectName,Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("todoHead",todoHead);
        parameters.put("toStart",toStart);
        parameters.put("toEnd",toEnd);
        parameters.put("toEnd2",toEnd2);
        parameters.put("todoclear",todoclear);
        parameters.put("todoimport",todoimport);
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
    }
    public Map<String,String> getParams() {
        return parameters;
    }
}
