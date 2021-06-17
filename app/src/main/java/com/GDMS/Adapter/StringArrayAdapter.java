package com.GDMS.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.GDMS.R;
import com.GDMS.models.ReasonList.RReason;

import java.util.ArrayList;


public class StringArrayAdapter extends BaseAdapter {
    Context context;
    ArrayList<RReason> list;
    LayoutInflater inflter;

    public StringArrayAdapter(Context applicationContext, ArrayList<RReason> list) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);

        TextView textView = view.findViewById(R.id.tvSpinerItem);
        View view1 = view.findViewById(R.id.view);
        if (i == 0) {

            view1.setVisibility(View.GONE);
        }else{
            textView.setText(list.get(i).getRreasondesc());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        // If this is the initial dummy entry, make it hidden
        if (position == 0) {
            TextView tv = new TextView(context);
            tv.setHeight(0);
            tv.setVisibility(View.GONE);
            view = tv;
        } else {
            // Pass convertView as null to prevent reuse of special case views
            view = super.getDropDownView(position, null, parent);
        }

        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
        parent.setVerticalScrollBarEnabled(false);
        return view;
    }
}
