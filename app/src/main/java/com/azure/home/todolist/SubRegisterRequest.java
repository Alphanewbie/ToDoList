package com.azure.home.todolist;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-11-25.
 */

public class SubRegisterRequest extends StringRequest {
    static private String URL = Common.addr + "subRegister.php";

    private Map<String,String> parameters;

    public SubRegisterRequest(String userID,String subjectName,String subjectDay,String subjectPro,String subjectyear,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("subjectName",subjectName);
        parameters.put("subjectDay",subjectDay);
        parameters.put("subjectPro",subjectPro);
        parameters.put("subjectyear",subjectyear);
    }
    public Map<String,String> getParams() {
        return parameters;
    }
}
