package com.tiyanrcode.guestbookclient.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiyanrcode.guestbookclient.R;
import com.tiyanrcode.guestbookclient.configure.DownloadImageTask;
import com.tiyanrcode.guestbookclient.model.Guest;
import com.tiyanrcode.guestbookclient.model.GuestSearch;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class GuestSeacrhBaseAdapter extends BaseAdapter implements Filterable {

    final String ip = "192.168.165.1";
    final String ip2 = "10.0.2.2";
    ArrayList<Guest> listGuest;
    ArrayList<Guest> mStringFilterList;
    private LayoutInflater mInflater;
    String urlpic = "http://"+ip2+"/guestbook/images/";
    Context context;
    ValueFilter valueFilter;


    public GuestSeacrhBaseAdapter(Context context, ArrayList<Guest> results) {
        this.context = context;
        listGuest =results;
        mStringFilterList = listGuest;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listGuest.size();
    }

    @Override
    public Object getItem(int position) {
        return listGuest.get(position);
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
            new DownloadImageTask(holder.guest_foto).execute(urlpic + listGuest.get(position).getGuest_foto());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.guest_name.setText(listGuest.get(position).getGuest_name());
        holder.guest_id.setText(listGuest.get(position).getGuest_id());
        holder.guest_prresence.setText(listGuest.get(position).getGuest_presence());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    static class ViewHolder {
        TextView guest_name, guest_id, guest_prresence;
        ImageView guest_foto;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<GuestSearch> filterList = new ArrayList<GuestSearch>();
                for (int i = 0; i < mStringFilterList.size(); i++){
                    if ((mStringFilterList.get(i).getGuest_name().toLowerCase())
                            .contains(constraint.toString().toLowerCase())) {

                       /* GuestSearch guest = new GuestSearch();
                        guest.setGuest_name((mStringFilterList.get(i).getGuest_name()));
                        guest.setGuest_name((mStringFilterList.get(i).getGuest_name()));
                        filterList.add(guest);*/
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listGuest = (ArrayList<Guest>) results.values;
            notifyDataSetChanged();
        }
    }
}
