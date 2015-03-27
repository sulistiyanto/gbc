package com.tiyanrcode.guestbookclient.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.baseadapter.FamilyBaseAdapter;
import com.tiyanrcode.guestbookclient.baseadapter.GuestBaseAdapter;
import com.tiyanrcode.guestbookclient.getdata.GetDataFamily;
import com.tiyanrcode.guestbookclient.getdata.GetDataGuest;
import com.tiyanrcode.guestbookclient.model.Count;
import com.tiyanrcode.guestbookclient.model.Family;
import com.tiyanrcode.guestbookclient.model.Guest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/25/2015.
 */
public class FamilyController extends ActionBarActivity {

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    Family family;
    ArrayList<Family> families = new ArrayList<Family>();
    ListView listView;
    TextView txtCount;
    String url = "http://"+ip2+"/guestbook/family_service.php";
    String book_id, guest_name;
    FamilyBaseAdapter familyBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family);
        getSupportActionBar().hide();
        listView = (ListView) findViewById(R.id.listMenu1);
        txtCount = (TextView) findViewById(R.id.count);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle.containsKey("book_id")){
            book_id = bundle.getString("book_id");
            guest_name = bundle.getString("guest_name");
            Log.d("book 1 ", book_id);
            Log.d("guest 1 ", guest_name);
            GetDataFamily getDataFamily = new GetDataFamily();
            getDataFamily.init(FamilyController.this, jsresult, book_id, guest_name, url);
        }
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
                e.printStackTrace();

            }
            familyBaseAdapter = new FamilyBaseAdapter(FamilyController.this, families);
            listView.setAdapter(familyBaseAdapter);
            int total = listView.getAdapter().getCount();
            txtCount.setText(""+ (total + 1));
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
