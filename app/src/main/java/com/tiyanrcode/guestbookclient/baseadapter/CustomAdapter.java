package com.tiyanrcode.guestbookclient.baseadapter;

import android.app.Activity;
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
import com.tiyanrcode.guestbookclient.model.GuestSearch;

import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/31/2015.
 */
public class CustomAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<GuestSearch> guestlist;
    ArrayList<GuestSearch> mStringFilterList;
    ValueFilter valueFilter;

    CustomAdapter(Context context, ArrayList<GuestSearch> guestlist){
        this.context = context;
        this.guestlist = guestlist;
        mStringFilterList = guestlist;
    }

    @Override
    public int getCount() {
        return guestlist.size();
    }

    @Override
    public Object getItem(int position) {
        return guestlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return guestlist.indexOf(getItemViewType(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = null;
        if (convertView == null) {
            TextView guestName = (TextView) convertView.findViewById(R.id.name);
            TextView guestId = (TextView) convertView.findViewById(R.id.code);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.flag);

            GuestSearch guest = guestlist.get(position);

            guestName.setText(guest.getGuest_name());
            guestId.setText(guest.getGuest_id());
            // imageView.setImageResource(guest.setGuest_foto());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
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

                        GuestSearch guest = new GuestSearch();
                        guest.setGuest_name((mStringFilterList.get(i).getGuest_name()));
                        guest.setGuest_name((mStringFilterList.get(i).getGuest_name()));
                        filterList.add(guest);
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
            guestlist = (ArrayList<GuestSearch>) results.values;
            notifyDataSetChanged();
        }
    }
}
