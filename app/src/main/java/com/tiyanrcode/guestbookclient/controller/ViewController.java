package com.tiyanrcode.guestbookclient.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.configure.RoundImage;
import com.tiyanrcode.guestbookclient.getdata.GetDataGuest;
import com.tiyanrcode.guestbookclient.model.Guest;
import com.tiyanrcode.guestbookclient.baseadapter.GuestBaseAdapter;
import com.tiyanrcode.guestbookclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class ViewController extends ActionBarActivity {

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    Guest guest;
    BookIdController book = new BookIdController();
    ArrayList<Guest> guests = new ArrayList<Guest>();
    ListView listView;
    String url = "http://"+ip2+"/guestbook/guest_service.php";
    String book_id;
    GuestBaseAdapter guestBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_guest);
        listView =(ListView) findViewById(R.id.listMenu);

       // bookID = (TextView) findViewById(R.id.bookID);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle.containsKey("book_id")){
            book_id = bundle.getString("book_id");
           // bookID.setText(book_id);
            GetDataGuest getDataGuest = new GetDataGuest();
            getDataGuest.init(ViewController.this, jsresult, book_id, url);
        }
    }

    public GetDataGuest.JsonObjectResult jsresult = new GetDataGuest.JsonObjectResult() {
        @Override
        public void gotJsonObject(JSONObject object) {
            try {
                JSONArray jsonArray = object.getJSONArray("guest");
                for (int i = 0; i < jsonArray.length(); i++) {
                    guest = new Guest();
                    guest.setGuest_id(jsonArray.getJSONObject(i).getString("guest_id"));
                    guest.setGuest_name(jsonArray.getJSONObject(i).getString("guest_name"));
                    guest.setGuest_foto(jsonArray.getJSONObject(i).getString("guest_foto"));
                    guests.add(guest);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            guestBaseAdapter = new GuestBaseAdapter(ViewController.this, guests);
            listView.setAdapter(guestBaseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(ViewController.this, FamilyController.class);
                    bundle.putString("book_id", book_id);
                    bundle.putString("guest_name", guests.get(position).getGuest_name());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_about:
                Intent intent = new Intent(ViewController.this, AboutController.class);
                startActivity(intent);
                return true;
            case  R.id.action_refresh:
               /* listView.setAdapter(null);
                guestBaseAdapter.updateAdapter(guests);
                GetDataGuest getDataGuest = new GetDataGuest();
                getDataGuest.init(ViewController.this, jsresult, book_id, url);
                guestBaseAdapter.updateAdapter(guests);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}