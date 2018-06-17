package com.ghen61.agabankh;

import android.content.Context;
import android.util.Log;
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

    private ArrayList<ShowItem> showItemlist = new ArrayList<ShowItem>();


    public ShowAdapter() {


    }


    @Override
    public int getCount() {
        return showItemlist.size();
    }

    @Override
    public Object getItem(int i) {
        return showItemlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_showitem, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView dateText = (TextView) convertView.findViewById(R.id.dateText);
        TextView spendText = (TextView) convertView.findViewById(R.id.spendText);
        TextView typeText = (TextView) convertView.findViewById(R.id.typeText);
        TextView restText = (TextView) convertView.findViewById(R.id.rest);
        TextView nameText =(TextView) convertView.findViewById(R.id.nameText);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ShowItem showItem = showItemlist.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        dateText.setText(showItem.getDate());
        spendText.setText(showItem.getSpend());
        typeText.setText(showItem.getType());
        restText.setText(showItem.getRest());
        nameText.setText(showItem.getName());

        return convertView;
    }

    public void addShowItem(String data, String name, String spend, String rest, String type) {
        ShowItem item = new ShowItem();

        item.setDate(data);
        item.setName(name);
        item.setSpend(spend);
        item.setRest(rest);
        item.setType(type);

        showItemlist.add(item);
    }

}
