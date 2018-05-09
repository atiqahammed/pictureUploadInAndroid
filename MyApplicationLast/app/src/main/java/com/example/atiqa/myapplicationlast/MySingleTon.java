package com.example.atiqa.myapplicationlast;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleTon {
    private static MySingleTon instance;
    private RequestQueue requestQueue;
    private static Context context;


    private MySingleTon(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }


    private RequestQueue getRequestQueue(){
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public static synchronized MySingleTon getInstance(Context context) {
        if(instance==null)
            instance = new MySingleTon(context);
        return instance;
    }

    public<T> void addRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
