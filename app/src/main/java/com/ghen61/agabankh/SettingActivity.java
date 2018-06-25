package com.ghen61.agabankh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by LG on 2018-06-13.
 */

public class SettingActivity extends AppCompatActivity{

    ListView menuList;
    Intent intent = null;
    String[] item ={"계좌생성","계좌삭제","로그아웃", "회원탈퇴"};

    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);


        menuList = (ListView)findViewById(R.id.menu);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,item);

        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                switch(i){

                    case 0: intent = new Intent(SettingActivity.this,CreateActivity.class);
                            startActivity(intent);
                            finish();break;
                    case 1:
                        intent = new Intent(SettingActivity.this,DeleteActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case 2:
                        //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SharedPreferences auto = getSharedPreferences("appData", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                        editor.clear();
                        editor.commit();
                        Toast.makeText(SettingActivity.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;



                    case 3:
                        Toast.makeText(SettingActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show(); break;


                }



            }
        });


    }
}
