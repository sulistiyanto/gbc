package com.tiyanrcode.guestbookclient.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.baseadapter.GuestBaseAdapter;
import com.tiyanrcode.guestbookclient.baseadapter.GuestSeacrhBaseAdapter;
import com.tiyanrcode.guestbookclient.getdata.GetDataGuest;
import com.tiyanrcode.guestbookclient.model.Guest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/30/2015.
 */
public class SearchController extends ActionBarActivity implements SearchView.OnQueryTextListener{

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    ArrayList<Guest> guests;
    GuestSeacrhBaseAdapter adapter;
    Guest guest;
    String url = "http://"+ip2+"/guestbook/guest_service.php";
    String book_id;
    GuestBaseAdapter guestBaseAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        getSupportActionBar().hide();
        listView =(ListView) findViewById(R.id.listsearch);
        overridePendingTransition(R.anim.pull_in_down, R.anim.push_out_up);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle.containsKey("book_id")){
            book_id = bundle.getString("book_id");
            GetDataGuest getDataGuest = new GetDataGuest();
            getDataGuest.init(SearchController.this, jsresult, book_id, url);
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
                    guest.setGuest_presence(jsonArray.getJSONObject(i).getString("guest_presence"));
                    guests.add(guest);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            guestBaseAdapter = new GuestBaseAdapter(SearchController.this, guests);
            listView.setAdapter(guestBaseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(SearchController.this, FamilyController.class);
                    bundle.putString("book_id", book_id);
                    bundle.putString("guest_id", guests.get(position).getGuest_id());
                    bundle.putString("guest_name", guests.get(position).getGuest_name());
                    bundle.putString("guest_foto", guests.get(position).getGuest_foto());
                    bundle.putString("guest_presence", guests.get(position).getGuest_presence());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    onStop();
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_up, R.anim.push_out_down);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return false;
    }
}
