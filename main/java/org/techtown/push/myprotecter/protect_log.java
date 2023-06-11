package org.techtown.push.myprotecter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class protect_log extends AppCompatActivity {
    TextView id;
    TextView id2;
    EditText idEdit;
    EditText idEdit2;
    Button button5;
    String pro_loginId,pro_loginPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_log);
        id = findViewById(R.id.id);
        id2 = findViewById(R.id.id2);
        idEdit2 = findViewById(R.id.idEdit2);
        idEdit = findViewById(R.id.idEdit);

        button5 = findViewById(R.id.button5);

        SharedPreferences sharedPreferences = getSharedPreferences("pro_sharedPreferences", Activity.MODE_PRIVATE);

        pro_loginId = sharedPreferences.getString("pro_loginId", null);
        pro_loginPwd = sharedPreferences.getString("pro_loginPwd", null);

        if(pro_loginId != null && pro_loginPwd != null) {

            Toast.makeText(getApplicationContext(), pro_loginId + "님 자동로그인 입니다!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), succ_log.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("id", pro_loginId);
            startActivity(intent);
            finish();

        }else if(pro_loginId == null && pro_loginPwd == null) {
            button5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.e("test","dsadas");
                    SharedPreferences sharedPreferences = getSharedPreferences("pro_sharedPreferences", Activity.MODE_PRIVATE);

                    SharedPreferences.Editor autoLogin = sharedPreferences.edit();

                    autoLogin.putString("pro_loginId", idEdit2.getText().toString());
                    autoLogin.putString("pro_loginPwd", idEdit.getText().toString());

                    autoLogin.commit();
                    Toast.makeText(getApplicationContext(), idEdit2.getText().toString()+"님 환영합니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), succ_log.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();


                }
            });

        }

        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), succ_log.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), choose_login.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}