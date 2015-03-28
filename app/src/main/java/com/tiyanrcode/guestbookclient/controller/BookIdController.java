package com.tiyanrcode.guestbookclient.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tiyanrcode.guestbookclient.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class BookIdController extends ActionBarActivity {

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    ImageButton btnView;
    EditText txtBookId;
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    List<NameValuePair> nameValuePairs;
    ResponseHandler<String> responseHandler;
    private static long back_pressed_time;
    private static long PERIOD = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_id);
        getSupportActionBar().hide();

        btnView = (ImageButton) findViewById(R.id.btnview);
        txtBookId = (EditText) findViewById(R.id.txtbookid);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkBookId();
                    }
                }).start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed_time + PERIOD > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar!", Toast.LENGTH_SHORT).show();
        back_pressed_time = System.currentTimeMillis();
    }

    void checkBookId(){
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://"+ip+"/guestbook/book_id.php");
            //add data
            nameValuePairs = new ArrayList<NameValuePair>();
            //username
            nameValuePairs.add(new BasicNameValuePair("book_id", txtBookId.getText().toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //ekxecute httpPost
            httpResponse = httpClient.execute(httpPost);
            responseHandler = new BasicResponseHandler();
            final String response = httpClient.execute(httpPost, responseHandler);
            if (response.equalsIgnoreCase("Book ID Found")){
                Bundle bundle = new Bundle();
                Intent intent = new Intent(BookIdController.this, ViewController.class);
                bundle.putString("book_id", txtBookId.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                showAlert();
            }
        } catch (Exception e){
            Log.e("Error", e.getMessage());
        }
    }

    void showAlert(){
        BookIdController.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookIdController.this);
                builder.setTitle("Gagal Mencari");
                builder.setMessage("ID buku tamu tidak ditemukan").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
