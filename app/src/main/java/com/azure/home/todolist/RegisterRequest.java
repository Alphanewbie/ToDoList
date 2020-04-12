package com.azure.home.todolist;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 2017-11-07.
 */

public class RegisterRequest extends StringRequest {

    static private String URL = Common.addr + "Register.php";

    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userpassword, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        URL = Common.addr + "Register.php";
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userpassword);
    }

    public Map<String,String> getParams() {
        return parameters;
    }
}
