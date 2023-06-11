package org.techtown.push.myprotecter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView id;
    TextView id2;
    TextView id3;
    TextView pw;
    TextView pw2;
    EditText idEdit;
    EditText idEdit2;
    EditText idEdit3;
    EditText pwEdit;
    EditText pwEdit2;
    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        id2 = findViewById(R.id.id2);
        pw2 = findViewById(R.id.pw2);
        id3 = findViewById(R.id.id3);
        idEdit2 = findViewById(R.id.idEdit2);
        idEdit3 = findViewById(R.id.idEdit3);
        idEdit = findViewById(R.id.idEdit);
        pwEdit = findViewById(R.id.pwEdit);
        pwEdit2 = findViewById(R.id.pwEdit2);

        button = findViewById(R.id.button);

        idEdit2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), choose_login.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }


}