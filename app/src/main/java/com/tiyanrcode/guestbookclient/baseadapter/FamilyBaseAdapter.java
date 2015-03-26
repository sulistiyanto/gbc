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
import android.widget.ImageView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.model.Family;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/25/2015.
 */
public class FamilyBaseAdapter extends BaseAdapter {

    private  static ArrayList<Family> searchArrayList;
    private LayoutInflater mInflater;

    public FamilyBaseAdapter(Context context, ArrayList<Family> results) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_family, null);
            holder = new ViewHolder();
            holder.family_name = (TextView) convertView.findViewById(R.id.familyname);
            holder.family_sex = (TextView) convertView.findViewById(R.id.familysex);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.family_sex.setText(searchArrayList.get(position).getFamily_sex());
        holder.family_name.setText(searchArrayList.get(position).getFamily_name());
        return convertView;
    }

    static class ViewHolder {
        TextView family_name, family_sex;
    }

}
