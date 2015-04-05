package com.tiyanrcode.guestbookclient.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.baseadapter.GuestBaseAdapter;
import com.tiyanrcode.guestbookclient.getdata.GetDataGuest;
import com.tiyanrcode.guestbookclient.model.Guest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class ViewController extends ActionBarActivity implements SearchView.OnQueryTextListener{

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    Guest guest;
    ArrayList<Guest> guests = new ArrayList<Guest>();
    ListView listView;
    SearchView mSearchView;
    String url = "http://"+ip2+"/guestbook/guest_service.php";
    String book_id;
    GuestBaseAdapter guestBaseAdapter;
    Boolean status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_guest);
        mSearchView=(SearchView) findViewById(R.id.search2);
        listView =(ListView) findViewById(R.id.listMenu);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle.containsKey("book_id")){
            book_id = bundle.getString("book_id");
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            GetDataGuest getDataGuest = new GetDataGuest();
            getDataGuest.init(ViewController.this, jsresult, book_id, url);
        }

        listView.setTextFilterEnabled(true);
        setupSearchView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(ViewController.this, FamilyController.class);
                bundle.putString("book_id", book_id);
                bundle.putString("guest_id", guests.get(position).getGuest_id());
                bundle.putString("guest_name", guests.get(position).getGuest_name());
                bundle.putString("guest_foto", guests.get(position).getGuest_foto());
                bundle.putString("guest_presence", guests.get(position).getGuest_presence());
                intent.putExtras(bundle);
                startActivity(intent);
                status = true;
            }
        });
    }

    private void setupSearchView()
    {
        //mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Pencarian");
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

            guestBaseAdapter = new GuestBaseAdapter(ViewController.this, guests);
            listView.setAdapter(guestBaseAdapter);
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
            case R.id.action_about:
                Intent intent = new Intent(ViewController.this, AboutController.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                Bundle bundle = new Bundle();
                Intent intent2 = new Intent(this, ViewController.class);
                bundle.putString("book_id", book_id);
                intent2.putExtras(bundle);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);

        }
        return true;
    }


}
