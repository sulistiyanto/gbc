package com.tiyanrcode.guestbookclient.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.baseadapter.FamilyBaseAdapter;
import com.tiyanrcode.guestbookclient.configure.DownloadImageTask;
import com.tiyanrcode.guestbookclient.configure.JSONParser;
import com.tiyanrcode.guestbookclient.configure.SHA1Utility;
import com.tiyanrcode.guestbookclient.getdata.GetDataFamily;
import com.tiyanrcode.guestbookclient.model.Family;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sulistiyanto on 3/25/2015.
 */
public class FamilyController extends ActionBarActivity {

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    Family family;
    ArrayList<Family> families = new ArrayList<Family>();
    ListView listView;
    TextView txtCount, txtName;
    ImageView imgFoto;
    CheckBox checkName;
    ImageButton update;
    String url = "http://"+ip2+"/guestbook/family_service.php";
    String urlpic = "http://"+ip2+"/guestbook/images/";
    String urlUpdate = "http://"+ip2+"/guestbook/update_guest.php";
    String book_id, guest_id, guest_name, guest_foto, guest_presence;
    FamilyBaseAdapter familyBaseAdapter;
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    List<NameValuePair> nameValuePairs;
    ResponseHandler<String> responseHandler;
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family);
        getSupportActionBar().hide();
        listView = (ListView) findViewById(R.id.listMenu1);
        txtCount = (TextView) findViewById(R.id.count);
        txtName = (TextView) findViewById(R.id.guestname2);
        imgFoto = (ImageView) findViewById(R.id.imgfoto);
        checkName = (CheckBox) findViewById(R.id.checkname);
        update = (ImageButton) findViewById(R.id.update);
        Bundle bundle = this.getIntent().getExtras();

        if (bundle.containsKey("book_id")) {
            overridePendingTransition(R.anim.pull_in_down, R.anim.push_out_up);
            book_id = bundle.getString("book_id");
            guest_id = bundle.getString("guest_id");
            guest_name = bundle.getString("guest_name");
            guest_foto = bundle.getString("guest_foto");
            guest_presence = bundle.getString("guest_presence");
            GetDataFamily getDataFamily = new GetDataFamily();
            getDataFamily.init(FamilyController.this, jsresult, book_id, guest_id, url);
            txtName.setText(guest_name);
            new DownloadImageTask(imgFoto).execute(urlpic + guest_foto);
            if (guest_presence.equals("Ya")) {
                checkName.setChecked(true);
            }
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(FamilyController.this, "", "Update Data...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        update();
                        finish();
                    }
                }).start();
            }
        });
    }

    public GetDataFamily.JsonObjectResult jsresult = new GetDataFamily.JsonObjectResult() {
        @Override
        public void gotJsonObject(JSONObject object) {
            try {
                JSONArray jsonArray = object.getJSONArray("family");
                for (int i = 0; i < jsonArray.length(); i++) {
                    family = new Family();
                    family.setFamily_name(jsonArray.getJSONObject(i).getString("family_name"));
                    family.setFamily_sex(jsonArray.getJSONObject(i).getString("family_sex"));
                    families.add(family);
                }
            } catch (JSONException e) {
                Log.d("coba", e.getMessage());
                //e.printStackTrace();
            }
            familyBaseAdapter = new FamilyBaseAdapter(FamilyController.this, families);
            listView.setAdapter(familyBaseAdapter);
            int total = listView.getAdapter().getCount();
            txtCount.setText("" + (1 + total));
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_up, R.anim.push_out_down);
        finish();
    }

    private void update(){
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(urlUpdate);
            //add data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            //username
            nameValuePairs.add(new BasicNameValuePair("guest_id", guest_id ));
            //level
            String presence;
            if (checkName.isChecked()){
                presence = "Ya";
            } else {
                presence = "Tidak";
            }
            nameValuePairs.add(new BasicNameValuePair("guest_presence", presence));
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
            if (response.equalsIgnoreCase("Updated Sucessfully")){
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FamilyController.this, ViewController.class);
                bundle.putString("book_id", book_id);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_up, R.anim.push_out_down);
            }
        } catch (Exception e){
            Log.d("kod", e.getMessage());
            Log.i("koi", e.getMessage());
            Log.e("koe", e.getMessage());
        }
    }

}
