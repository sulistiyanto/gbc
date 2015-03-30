package com.tiyanrcode.guestbookclient.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.configure.JSONParser;
import com.tiyanrcode.guestbookclient.configure.SHA1Utility;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class LoginController extends ActionBarActivity {

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    EditText txtUsername, txtPassword;
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    List<NameValuePair> nameValuePairs;
    ProgressDialog progressDialog = null;
    ResponseHandler<String> responseHandler;
    String level= "User", passwordEncrypt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        txtUsername = (EditText) findViewById(R.id.txtusername);
        txtPassword = (EditText) findViewById(R.id.txtpassword);

        ImageButton btnSignIn = (ImageButton) findViewById(R.id.btnsignin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(LoginController.this, "Isi username", Toast.LENGTH_SHORT).show();
                    txtUsername.requestFocus();
                } else if (txtPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(LoginController.this, "Isi password", Toast.LENGTH_SHORT).show();
                    txtPassword.requestFocus();
                } else {
                    progressDialog = ProgressDialog.show(LoginController.this, "", "Pencarian Username");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            login();
                            finish();
                        }
                    }).start();
                }
            }
        });


    }

    void login(){
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://"+ip2+"/guestbook/login.php");

            //add data
            nameValuePairs = new ArrayList<NameValuePair>(3);
            //username
            nameValuePairs.add(new BasicNameValuePair("login_name", txtUsername.getText().toString()));
            //password
            passwordEncrypt = SHA1Utility.getSHA1(txtPassword.getText().toString().trim());
            nameValuePairs.add(new BasicNameValuePair("login_password", passwordEncrypt));
            //level
            nameValuePairs.add(new BasicNameValuePair("login_level", level));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //ekxecute httpPost
            httpResponse = httpClient.execute(httpPost);
            responseHandler = new BasicResponseHandler();
            final String response = httpClient.execute(httpPost, responseHandler);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
            if (response.equalsIgnoreCase("User Found")){
                startActivity(new Intent(LoginController.this, BookIdController.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            } else {
                showAlert();
            }
        } catch (Exception e){
            progressDialog.dismiss();
        }
    }

    void showAlert(){
        LoginController.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginController.this);
                builder.setTitle("Gagal Masuk");
                builder.setMessage("Username tidak ditemukan").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
