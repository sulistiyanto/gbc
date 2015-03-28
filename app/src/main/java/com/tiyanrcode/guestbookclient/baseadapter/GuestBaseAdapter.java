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
    String urlpic = "http://"+ip+"/guestbook/images/";

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_custom_listview, parent, false);
            holder = new ViewHolder();

            holder.guest_name = (TextView) convertView.findViewById(R.id.guestname);
            holder.guest_id = (TextView) convertView.findViewById(R.id.guestid);
            holder.guest_foto = (ImageView) convertView.findViewById(R.id.guestfoto);
            new DownloadImageTask(holder.guest_foto).execute(urlpic + searchArrayList.get(position).getGuest_foto());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.guest_name.setText(searchArrayList.get(position).getGuest_name());
        holder.guest_id.setText(searchArrayList.get(position).getGuest_id());
        return convertView;
    }

    static class ViewHolder {
        TextView guest_name, guest_id;
        ImageView guest_foto;
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
                Bitmap bmp2 = Bitmap.createScaledBitmap(bitmap, 72, 72, true);
                bmImage.setImageBitmap(bmp2);
            }
        }
    }

}
