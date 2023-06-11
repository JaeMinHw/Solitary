package org.techtown.push.myprotecter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class alone_log extends AppCompatActivity {
    Button button;
    TextView textView;
    TextView textView2;
    TextView textView3;
    EditText editText2;
    EditText editText3;

    String loginId,loginPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone_log);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

        loginId = sharedPreferences.getString("loginId", null);
        loginPwd = sharedPreferences.getString("loginPwd", null);

        if(loginId != null && loginPwd != null) {

            Toast.makeText(getApplicationContext(), loginId + "님 자동로그인 입니다!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), alone_main.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("id", loginId);
            startActivity(intent);
            finish();

        }else if(loginId == null && loginPwd == null) {
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

                    SharedPreferences.Editor autoLogin = sharedPreferences.edit();

                    autoLogin.putString("loginId", editText3.getText().toString());
                    autoLogin.putString("loginPwd", editText2.getText().toString());

                    autoLogin.commit();
                    Toast.makeText(getApplicationContext(), editText3.getText().toString()+"님 환영합니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), alone_main.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();


                }
            });

        }


    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), choose_login.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}