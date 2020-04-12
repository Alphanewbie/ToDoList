package com.azure.home.todolist;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-12-05.
 */

public class Todohiden extends StringRequest {
    static private String URL = Common.addr + "todohidden.php";

    private Map<String,String> parameters;

    public Todohiden(String todoHead, String todohidden,String userID, String subjectName,
                          Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("todoHead",todoHead);
        if(todohidden.equals("1")) {
            parameters.put("todohidden","0");
        }
        else {
            parameters.put("todohidden", "1");
        }
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
    }
    public Map<String,String> getParams() {
        return parameters;
    }
}
