package com.ghen61.agabankh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteActivity extends AppCompatActivity implements com.ghen61.agabankh.DeleteAdapter.ListBtnClickListener{



    private static final String TAG = "TEST";
    Intent intent = null;
    Intent Main;

    TextView name;

    ArrayList<HashMap<String, String>> mArrayList;


    //사용자의 아이디가 들어가있는 변수. 백엔드 친구들 이걸 사용하세요!!
    String userID;

    ImageButton setting;
    String mJsonString;

    //밖에서 선언하게 햇숩니ㅏㄷㅇ 밑에서 필요해서
    ListView listView;
    com.ghen61.agabankh.DeleteAdapter deleteAdapter;
    ArrayList<ListViewItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delete);

       items = new ArrayList<ListViewItem>();
        deleteAdapter = new com.ghen61.agabankh.DeleteAdapter(this,R.layout.layout_item2,items,this);


        listView = (ListView)findViewById(R.id.deleteList);
        listView.setAdapter(deleteAdapter);

        mArrayList = new ArrayList<>();



        Log.d(TAG, "결과~~~~~~~~~~~삭제~~~~~~~~~~~~~");

        name = (TextView)findViewById(R.id.name);



        //login 페이지에서 값을 받아와서 넘김
      //  Main=getIntent();
        //userID  = Main.getStringExtra("loginID");
     //   userID="aaaaa";//아이디 값 받아올거면 이렇게 해주심 됩니덩
       // name.setText(userID+"님");

      //  setting = (ImageButton)findViewById(R.id.setting);




        //  loadItemsFrom(items);



        /*
         *
         * intent.putExtra("value","문자열");
         *
         * */




        DeleteActivity.GetData task = new DeleteActivity.GetData();
        task.execute("http://wwhurin0834.dothome.co.kr/agabank/getAccount.php?id="+var.ID);

    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DeleteActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                // mTextViewResult.setText(errorString);
            }
            else {
                Log.d(TAG, "response  - " + "helfkjadklfjkladjfkladfadf");
                mJsonString = result;
                showResult();
            }
        }



        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("webnautes");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                //여기서 이제 해시맵이랑 값 넣어둘테니 빼다 씌시오...
                String id = item.getString("id");
                String account = item.getString("account");
                String mlimit = item.getString("mlimit");
                String slimit = item.getString("slimit");
                String moungAccount = item.getString("mountAmount");
                String balance = item.getString("balance");

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("id", id);
                hashMap.put("account", account);
                hashMap.put("mlimit", mlimit);
                hashMap.put("slimit", slimit);
                hashMap.put("moungAccount", moungAccount);
                hashMap.put("balance", balance);


                if(moungAccount.equals("")){

                    moungAccount = "0";

                }

                Toast.makeText(this, "잔액:"+moungAccount+"!", Toast.LENGTH_SHORT).show();

                deleteAdapter.addItem(account,moungAccount+"원",slimit+"원",mlimit+"원");
                listView.setAdapter(deleteAdapter);

                Log.d("~~~~~~~~~~~", moungAccount);




                mArrayList.add(hashMap);
            }
/*
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );

            mlistView.setAdapter(adapter);*/

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

/*
    private  boolean loadItemsFrom(ArrayList<ListViewItem> list) {


        ListViewItem item;

        int i;

        if (list == null) {

            list = new ArrayList<ListViewItem>();

            i = 1;

            item = new ListViewItem();
            item.setAccount("21111-11111");
            item.setMoney("1");
            item.setOnetime("5");
            item.setMonth("4");
            i++;

            list.add(item);

        }
        return true;
    }
*/

    @Override
    public void onListBtnClick(String type){

        String values[] = type.split("/");


        switch (values[0]){

            case "show": intent = new Intent(DeleteActivity.this, ShowActivity.class);
                intent.putExtra("acc",String.valueOf(values[1]));
                startActivity(intent); break;

            case "send": intent = new Intent(DeleteActivity.this, SendActivity.class);
                intent.putExtra("acc",String.valueOf(values[1]));
                startActivity(intent); break;

            case "delete": deleteCheck(); break;


        }
    }

    private void deleteCheck() {

        //만약 계좌의 잔액이 0이면 계좌 삭제
        //아니면 안내창

        int count, checked ;
        count = deleteAdapter.getCount() ;

        if (count > 0) {
            // 현재 선택된 아이템의 position 획득.
            checked = listView.getCheckedItemPosition();

            if (checked > -1 && checked < count) {
                // 아이템 삭제
                items.remove(checked) ;

                // listview 선택 초기화.
                listView.clearChoices();

                // listview 갱신.
                deleteAdapter.notifyDataSetChanged();
            }
        }


        finish();

    }
}

