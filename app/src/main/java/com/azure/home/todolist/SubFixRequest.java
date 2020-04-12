package com.azure.home.todolist;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-12-04.
 */

public class SubFixRequest extends StringRequest {
    static private String URL = Common.addr + "subFix.php";

    private Map<String,String> parameters;

    public SubFixRequest(String userID,String subjectfixName,String subjectDay,String subjectPro,String subjectyear,String subjectName,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("subjectfixName",subjectfixName);
        parameters.put("subjectDay",subjectDay);
        parameters.put("subjectPro",subjectPro);
        parameters.put("subjectyear",subjectyear);
        parameters.put("subjectName",subjectName);
    }
    public Map<String,String> getParams() {
        return parameters;
    }
}
