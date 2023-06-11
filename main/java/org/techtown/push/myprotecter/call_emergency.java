package org.techtown.push.myprotecter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class call_emergency extends AppCompatActivity {
    Button button6;
    Button button7;
    TextView textView11;
    TextView textView12;

    String resultText ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_emergency);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);

        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 예 버튼을 눌러 보호자 핸드폰으로 알림 보내주기
                try {
                    resultText = new Task().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.e("resultText",""+resultText);

                if(resultText.equals("Success"))
                {
                    Intent intent = new Intent(getApplicationContext(), alone_main.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), alone_main.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


}

class Task extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;
    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            url = new URL("http://172.20.10.2:8000/emergnecyButton"); // 마지막에는 / 넣지 말기

            Log.e("Address Tag : ",""+url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.e("receiveMsg : ", receiveMsg);

                reader.close();
            }
            else if(conn.getResponseCode() == 404) {
                Log.e("Mytag","what");
            }
            else {
                Log.e("결과", conn.getResponseCode() + "Error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            Log.e("Mytag","내용: "+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }
}