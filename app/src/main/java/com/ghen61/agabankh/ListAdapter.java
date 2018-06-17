package com.ghen61.agabankh;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by LG on 2018-06-13.
 */

public class ListAdapter extends BaseAdapter implements View.OnClickListener {


    //버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener{

        void onListBtnClick(String type);

    }

    //생성자로부터 전달된 resource id 값을 저장
    int resourceId;
    Context content;
    int resource;
    ArrayList<ListViewItem> list;


    //생성자로부터 전달된 ListBtnClickListener 저장
    private ListBtnClickListener listBtnClickListener;

    //리스트뷰 어댑터 생성자, 마지막에 ListAdapter 추가
    ListAdapter(Context content, int resource, ArrayList<ListViewItem> list, ListBtnClickListener clickListener){

        this.content=content;
        this.resource = resource;
        this.list=list;
        this.resourceId =resource;
        this.listBtnClickListener=clickListener;

    }






    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    public ArrayList<ShowItem> listShowItme = new ArrayList<ShowItem>();



    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final  Context context = parent.getContext();

        //생성자로부터 저장된 리소스에 해당하는 레이아웃을 inflate하여 convertVIew 참조획득.

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId,parent,false);

        }

        //화면에 표시될 View(Layout이 inflate)로부터 위젯에 대한 참조 획득
        final TextView acc = (TextView) convertView.findViewById(R.id.accNumber);
        final TextView money = (TextView) convertView.findViewById(R.id.money);
        final TextView month = (TextView) convertView.findViewById(R.id.month);
        final TextView once = (TextView) convertView.findViewById(R.id.once);

        final ListViewItem listitem = (ListViewItem) getItem(position);

        acc.setText(listitem.getAccount());
        money.setText(listitem.getMoney());
        once.setText(listitem.getOnetime());
        month.setText(listitem.getMonth());


        //계좌번호를 같이 넘거야하는데,,,,
        Button showB = (Button) convertView.findViewById(R.id.showB);
        showB.setTag("show/"+acc.getText());
        showB.setOnClickListener(this);


        Button sendB = (Button) convertView.findViewById(R.id.sendB);
        sendB.setTag("send/"+acc.getText());
        sendB.setOnClickListener(this);


        return convertView;

/*
       final int pos = position;
       final Context context = parent.getContext();

       if(convertView == null){

           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.layout_item,parent,false);

       }


        TextView acc = (TextView) convertView.findViewById(R.id.accNumber);
        TextView money = (TextView) convertView.findViewById(R.id.money);
        TextView month = (TextView) convertView.findViewById(R.id.month);
        TextView once = (TextView) convertView.findViewById(R.id.once);


        ListViewItem listViewItem = listViewItemList.get(position);

        acc.setText(listViewItem.getAccount());
        money.setText(listViewItem.getMoney());
        once.setText(listViewItem.getOnetime());
        month.setText(listViewItem.getMonth());


        return convertView;

        */



    }

    public void addItem(String acc, String moeny, String once, String month) {
        ListViewItem item = new ListViewItem();
        item.setAccount(acc);
        item.setMoney(moeny);
        item.setOnetime(once);
        item.setMonth(month);

        listViewItemList.add(item);
    }



    public void clearItem(){
        listViewItemList.clear();
    }



    //2:56 새로 추가한 액티비티
    @Override
    public void onClick(View view) {

        if(this.listBtnClickListener != null){

            this.listBtnClickListener.onListBtnClick((String) view.getTag());

        }

    }
}

