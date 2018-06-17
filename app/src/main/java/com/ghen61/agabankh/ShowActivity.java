package com.ghen61.agabankh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LG on 2018-06-13.
 */

public class ShowActivity extends AppCompatActivity{
    private TextView mTextViewResult;
    private Spinner spinner_counter;
    ListView listview;
    Button showB;
    ShowAdapter adapter;

    Intent intent = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);

        listview = (ListView) findViewById(R.id.content);



        mTextViewResult=(TextView)findViewById(R.id.result);

        showB = (Button) findViewById(R.id.submitB);

        showB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        //InsertData task = new InsertData();
        //task.execute("*");

        spinner_counter = (Spinner)findViewById(R.id.spinner_counter);
        spinner_counter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = spinner_counter.getSelectedItem().toString();
                InsertData task = new InsertData();
                task.execute(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                InsertData task = new InsertData();
                task.execute("*");
            }
        });

    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShowActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            String value[] =  result.split("&");


            adapter = new ShowAdapter();

            adapter.notifyDataSetChanged();
            listview.setAdapter(adapter);

            Log.d("데이터가 제대로 나오냐???!?????!",String.valueOf(value.length));

            Log.d("데이타가 제대로 나오냐고???!?????!",String.valueOf(value.length/7));


            mTextViewResult.setText(result);//php에서 echo 해주는 내용 출력해준다


            String data[][] = new  String[value.length/7][7];




            int index = 0;

            for(int i = 0 ; i < value.length/7; i++){

                for(int j = 0 ; j < 7 ; j++){

                    data[i][j] = value[index];
                    index++;

                }


                adapter.addShowItem(data[i][0],data[i][2],data[i][4],data[i][5],data[i][3]);

            }

            listview.setAdapter(adapter);


            if (result.equals("ok")) {
                Log.d("od", "dddd");
            }
            //Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String type = (String) params[0];
            String myAcc ="1234";

            String serverURL =  "http://wwhurin0834.dothome.co.kr/showTran.php";
            String postParameters = "myAcc=" + myAcc +"&type="+type;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
               String line = null;


                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }



                bufferedReader.close();


                return sb.toString();



            } catch (Exception e) {

                //Log.d(Tag, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}