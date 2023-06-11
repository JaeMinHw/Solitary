package org.techtown.push.myprotecter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class succ_log extends AppCompatActivity {
    int backhome=0;
    Button button2;
    Button button3;
    ImageView imageView;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView11;
    TextView textView16;
    TextView textView17;
    TextView textView18;
    TextView textView19;
    TextView textView20;
    TextView textView21;
    TextView textView22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succ_log);

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        imageView = findViewById(R.id.imageView);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView11 = findViewById(R.id.textView11);
        textView16 = findViewById(R.id.textView16);
        textView17 = findViewById(R.id.textView17);
        textView18 = findViewById(R.id.textView18);
        textView19 = findViewById(R.id.textView19);
        textView20 = findViewById(R.id.textView20);
        textView21 = findViewById(R.id.textView21);
        textView22 = findViewById(R.id.textView22);

        imageView.setImageResource(R.drawable.user);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), click_activity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), soli_map.class);
                startActivity(intent);
            }
        });
        Timer timer = new Timer();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backhome++;
                if(backhome == 15) {
                    SharedPreferences sf = getSharedPreferences("pro_sharedPreferences",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.remove("pro_loginId");
                    editor.remove("pro_loginPwd");
                    editor.commit();
                    backhome =0;
                    Intent intent = new Intent(getApplicationContext(),protect_log.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        backhome=0;
                    }
                };
                timer.schedule(timerTask,8000);

            }
        });
    }
    private long backpressedTime = 0;

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }

    }
}