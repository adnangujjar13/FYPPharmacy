package com.example.adnangujjar.fyp;

/**
 * Created by Adnan Gujjar on 7/11/2017.
 */
import com.google.gson.annotations.SerializedName;


public class ServerResponse {
    // variable name should be same as in the json response from php
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    String getMessage()
    {
        return message;
    }

    boolean getSuccess()
    {
        return success;
    }

}
