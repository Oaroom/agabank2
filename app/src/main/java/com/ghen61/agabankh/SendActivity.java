package com.ghen61.agabankh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LG on 2018-06-13.
 */

public class SendActivity extends AppCompatActivity {

    Button button1;
    EditText name;
    EditText acc;
    EditText mEditTextMoney;
    Spinner spinner;
    TextView myAcc;

    Intent intent = null;

    String sendName;
    String sendMoney;
    String user;

    protected void onCreate(Bundle savedInstanceState) {

        Intent accIntent = getIntent();

        // 계좌번호가 들어가 있는 변수. 백엔드 친구들 이걸 이용하세요~!~
        String userAcc = accIntent.getStringExtra("acc");
        user = userAcc;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_send);

        button1=(Button)findViewById(R.id.submitB);
        name=(EditText)findViewById(R.id.name);
        acc=(EditText)findViewById(R.id.acc);
        mEditTextMoney=(EditText)findViewById(R.id.money);
        spinner=(Spinner)findViewById(R.id.spend);
        myAcc = (TextView)findViewById(R.id.myAcc);

        myAcc.setText(userAcc);


        Button buttonInsert = (Button)findViewById(R.id.submitB);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(acc.getText() == null || mEditTextMoney.getText() == null){

                    Toast.makeText(SendActivity.this, "계좌번호와 거래금액을 입력해주세요!!", Toast.LENGTH_SHORT).show();

                }else {

                    String myacc = "1234"; //잠깐 임시 값이 들어왔아
                    String reAcc = acc.getText().toString();
                    String money = mEditTextMoney.getText().toString();
                    String type = spinner.getSelectedItem().toString();

                    sendName = name.getText().toString();

                    InsertData task = new InsertData();
                    task.execute(user, reAcc, money, type);


                    intent = new Intent(SendActivity.this, NoticeActivity.class);
                    intent.putExtra("name", sendName);
                    intent.putExtra("money", money);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SendActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);//php에서 echo 해주는 내용 출력해준다
            if (result.equals("ok")) {
                Log.d("od", "dddd");

            }
            //Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String myacc = (String) params[0];
            String reAcc = (String) params[1];
            String money = (String) params[2];
            String type = (String) params[3];
            String serverURL =  "http://wwhurin0834.dothome.co.kr/sent.php";
            String postParameters = "myAcc=" + myacc + "&receiverAcc=" + reAcc+"&money="+money+"&type="+type;

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