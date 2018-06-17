package com.ghen61.agabankh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LG on 2018-06-17.
 */

public class ShowAdapter extends BaseAdapter {


    private ArrayList<ShowItem> showlist = new ArrayList<ShowItem>();

    public ShowAdapter() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        dateText.setText(showItem.getDate());

        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_showitem, parent, false);

        }

        TextView dateText = (TextView) convertView.findViewById(R.id.nameText);
        TextView moneyText = (TextView) convertView.findViewById(R.id.moneyText);
        TextView typeText = (TextView) convertView.findViewById(R.id.typeText);

        ShowItem showItem = showlist.get(position);

        dateText.setText(showItem.getDate());
        moneyText.setText(showItem.getMoney());
        dateText.setText(showItem.getType());

        return convertView;

    }


        return null;
    }
}
