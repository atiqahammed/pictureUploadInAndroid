package com.example.atiqa.myapplicationlast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Button upload, choose;
    private ImageView view;
    private EditText text;

    private static int IMG_REQUEST = 1;

    private Bitmap bitmap;
    private String uploadURL = "http://192.168.1.100/image.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = (Button)findViewById(R.id.button2);
        choose = (Button)findViewById(R.id.button);
        view = (ImageView)findViewById(R.id.image);
        text = (EditText)findViewById(R.id.editText);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                //text.setText("Hello");
                //text.setVisibility(View.VISIBLE);

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
    }




    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == IMG_REQUEST  && resultCode  == RESULT_OK && data !=null) {

                Uri path = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    view.setImageBitmap(bitmap);
                    view.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    }


    private void uploadImage() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*
                try {
                    //JSONObject jesonObject = new JSONObject(response);
                    //String res = jesonObject.getString("response");
                    //Toast.makeText(MainActivity.this, res, Toast.LENGTH_LONG).show();
                    //view.setImageResource(0);
                    //view.setVisibility(View.GONE);
                    //text.setText("");
                    //text.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", text.getText().toString().trim());
                params.put("image", imageToString(bitmap));

                return params;
            }
        };
        MySingleTon.getInstance(MainActivity.this).addRequestQueue(stringRequest);

    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);



    }
}
