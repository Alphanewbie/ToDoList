package com.azure.home.todolist;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by HOME on 2017-12-02.
 */

public class DeletesubRequset extends StringRequest {
    static private String URL = Common.addr + "delete.php";

    private Map<String,String> parameters;

    public DeletesubRequset(String userID, String subjectName, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
    }

    public Map<String,String> getParams() {
        return parameters;
    }
}
