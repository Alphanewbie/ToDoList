package com.azure.home.todolist;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-12-05.
 */

public class Todoimport extends StringRequest {
    static private String URL = Common.addr + "todoimport.php";

    private Map<String,String> parameters;

    public Todoimport(String todoHead,String todoimport,String userID, String subjectName,
                     Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("todoHead",todoHead);
        parameters.put("todoimport",todoimport);
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
    }
    public Map<String,String> getParams() {
        return parameters;
    }
}
