package com.tiyanrcode.guestbookclient.controller;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.configure.JSONParser;
import com.tiyanrcode.guestbookclient.model.Guest;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/30/2015.
 */
public class Search extends Fragment {

    View myFragmentView;
    SearchView searchView;
    Typeface typeface;
    ListView listView;
    String found = "";

    ArrayList<Guest> guests = new ArrayList<Guest>();
    ArrayList<Guest> searchGuests = new ArrayList<Guest>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        listView = (ListView) myFragmentView.findViewById(R.id.search_result);
        searchView = (SearchView) myFragmentView.findViewById(R.id.search);
        searchView.setQueryHint("Pencarian . . .");

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if ((s.length() > 3)) {
                    listView.setVisibility(myFragmentView.VISIBLE);

                }
                else {
                    listView.setVisibility(myFragmentView.INVISIBLE);
                }
                return false;
            }
        });
        return myFragmentView;
    }

    public void filterGuestArray(String newText){
        String guestName;

        searchGuests.clear();
        for (int i =0; i<guests.size();i++) {
            guestName = guests.get(i).getGuest_name().toLowerCase();
            if(guestName.contains(newText.toLowerCase())
                    || guests.get(i).getGuest_id().contains(newText)) {
                searchGuests.add(guests.get(i));
            }
        }
    }

    class mysAsyncTask extends AsyncTask<String, Void, String> {

        JSONParser jsonParser;
        JSONArray guestList;


        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}
