package org.techtown.push.myprotecter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    protected void onCreate(Bundle savedInstanceState) {
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

        Toast.makeText(getApplicationContext(),"체크",Toast.LENGTH_SHORT).show();
    }
}