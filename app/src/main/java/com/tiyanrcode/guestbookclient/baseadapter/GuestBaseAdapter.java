package com.tiyanrcode.guestbookclient.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.configure.DownloadImageTask;
import com.tiyanrcode.guestbookclient.model.Guest;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class GuestBaseAdapter extends BaseAdapter{

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    private  static ArrayList<Guest> searchArrayList;
    private LayoutInflater mInflater;
    String urlpic = "http://"+ip2+"/guestbook/images/";

    public GuestBaseAdapter(Context context, ArrayList<Guest> results) {
        searchArrayList =results;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_custom_listview, parent, false);
            holder = new ViewHolder();

            holder.guest_name = (TextView) convertView.findViewById(R.id.guestname);
            holder.guest_id = (TextView) convertView.findViewById(R.id.guestid);
            holder.guest_prresence = (TextView) convertView.findViewById(R.id.guestpresence);
            holder.guest_foto = (ImageView) convertView.findViewById(R.id.guestfoto);
            new DownloadImageTask(holder.guest_foto).execute(urlpic + searchArrayList.get(position).getGuest_foto());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.guest_name.setText(searchArrayList.get(position).getGuest_name());
        holder.guest_id.setText(searchArrayList.get(position).getGuest_id());
        holder.guest_prresence.setText(searchArrayList.get(position).getGuest_presence());
        return convertView;
    }

    static class ViewHolder {
        TextView guest_name, guest_id, guest_prresence;
        ImageView guest_foto;
    }

}
