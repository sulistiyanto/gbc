package com.tiyanrcode.guestbookclient.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.baseadapter.FamilyBaseAdapter;
import com.tiyanrcode.guestbookclient.configure.DownloadImageTask;
import com.tiyanrcode.guestbookclient.getdata.GetDataFamily;
import com.tiyanrcode.guestbookclient.model.Family;

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
    TextView txtCount, txtName;
    ImageView imgFoto;
    CheckBox checkName;
    String url = "http://"+ip2+"/guestbook/family_service.php";
    String urlpic = "http://"+ip2+"/guestbook/images/";
    String book_id, guest_id, guest_name, guest_foto, guest_presence;
    FamilyBaseAdapter familyBaseAdapter;

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

                txtCount.setText(""+listView.getAdapter().getCount());
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_up, R.anim.push_out_down);
        finish();
    }

}
