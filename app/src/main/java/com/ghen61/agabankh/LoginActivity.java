package com.ghen61.agabankh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {


    EditText idEdit;
    EditText pwEdit;
    Button submit;
    TextView singup;
    CheckBox checkBox;
    Intent login = null;

    private boolean saveLoginData;

    //사용자가 입력한 아이디와 비밀번호
    String id;
    String pw;


    //DB에서 확인을 위한 아이디와 비밀번호
    String idDB = "aaaaa";
    String pwDB = "aaaaa";

    //자동로그인
    private SharedPreferences appData;

    TextView mTextViewResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();


        idEdit = (EditText)findViewById(R.id.id);
        pwEdit = (EditText)findViewById(R.id.pw);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        mTextViewResult=(TextView)findViewById(R.id.result);


        submit = (Button)findViewById(R.id.loginB);
        singup = (TextView)findViewById(R.id.singup);

        if (saveLoginData) {
            if(id!=null&&!(id.equals(""))) {
                var.ID=id;
                var.PW=pw;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                idEdit.setText(id);
                pwEdit.setText(pw);
                checkBox.setChecked(saveLoginData);
                finish();
            }
        }


        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(login);
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = idEdit.getText().toString();
                pw = pwEdit.getText().toString();
                Toast.makeText(LoginActivity.this, id+"/"+pw+"/"+idDB+"/"+pwDB, Toast.LENGTH_SHORT).show();
                var.ID=id;
                var.PW=pw;
                LoginActivity.InsertData task = new LoginActivity.InsertData();
                task.execute(id, pw);

            }
        });

    }

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", idEdit.getText().toString().trim());
        editor.putString("PWD", pwEdit.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pw = appData.getString("PWD", "");
    }


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
           //
//            mTextViewResult.setText(result);//php에서 echo 해주는 내용 출력해준다
            if (result.equals("ok")) {
                Log.d("od", "dddd");
                save();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //intent.putExtra("id", IDD);
                startActivity(intent);
                finish();
            }
            //Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[0];
            String pass = (String) params[1];

            String serverURL = "http://wwhurin0834.dothome.co.kr/agabank/login.php";
            String postParameters = "id=" + id + "&pass=" + pass;


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

                //Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }
}
