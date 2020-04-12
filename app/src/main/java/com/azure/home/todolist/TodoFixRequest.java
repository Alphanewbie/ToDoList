package com.azure.home.todolist;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-12-05.
 */

public class TodoFixRequest extends StringRequest {
    static private String URL = Common.addr + "todoFix.php";

    private Map<String,String> parameters;

    public TodoFixRequest(String fixtodoHead,String toStart,String toEnd,String toEnd2,String todoclear,
                               String todoimport, String todoHead,String userID, String subjectName,
                               Response.Listener<String> listener){
    super(Request.Method.POST,URL,listener,null);
    parameters = new HashMap<>();
        parameters.put("fixtodoHead",fixtodoHead);
        parameters.put("toStart",toStart);
        parameters.put("toEnd",toEnd);
        parameters.put("toEnd2",toEnd2);
        parameters.put("todoclear",todoclear);
        parameters.put("todoimport",todoimport);
        parameters.put("todoHead",todoHead);
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
}
    public Map<String,String> getParams() {
        return parameters;
    }
}
