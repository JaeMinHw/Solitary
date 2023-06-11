package org.techtown.push.myprotecter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class click_activity extends AppCompatActivity {
    Button button4;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView13;
    TextView textView14;
    TextView textView15;
    ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        button4 = findViewById(R.id.button4);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        textView15 = findViewById(R.id.textView15);

        imageView2 = findViewById(R.id.imageView2);

        imageView2.setImageResource(R.drawable.user);

        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), soli_map.class);
                startActivity(intent);
            }
        });
    }
}