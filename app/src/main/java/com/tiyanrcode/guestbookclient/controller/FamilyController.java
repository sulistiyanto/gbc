package com.tiyanrcode.guestbookclient.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.baseadapter.FamilyBaseAdapter;
import com.tiyanrcode.guestbookclient.getdata.GetDataFamily;
import com.tiyanrcode.guestbookclient.model.Family;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
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
    String url = "http://"+ip2+"/guestbook/family_service.php";
    String urlpic = "http://"+ip2+"/guestbook/images/";
    String book_id, guest_id, guest_name, guest_foto;
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


        Bundle bundle = this.getIntent().getExtras();
        if (bundle.containsKey("book_id")){
            overridePendingTransition(R.anim.pull_in_down, R.anim.push_out_up);
            book_id = bundle.getString("book_id");
            guest_id = bundle.getString("guest_id");
            guest_name = bundle.getString("guest_name");
            guest_foto = bundle.getString("guest_foto");
            Log.d("book 1 ", book_id);
            Log.d("guest 1 ", guest_foto);
            GetDataFamily getDataFamily = new GetDataFamily();
            getDataFamily.init(FamilyController.this, jsresult, book_id, guest_id, url);
            txtName.setText(guest_name);
            new DownloadImageTask(imgFoto).execute(urlpic+guest_foto);
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
        overridePendingTransition(R.anim.pull_in_up, R.anim.push_out_down);
        finish();
    }

    public class  DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public  DownloadImageTask(ImageView bmImage){
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream inputStream = new URL(urlDisplay).openStream();
                mIcon = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                Bitmap bmp2 = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                bmImage.setImageBitmap(bmp2);
            }
        }
    }

}
