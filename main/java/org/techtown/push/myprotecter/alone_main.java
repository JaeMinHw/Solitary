package org.techtown.push.myprotecter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class alone_main extends AppCompatActivity implements SensorEventListener {

    Button button19;
    TextView textView9;
    TextView textView10;
    TextView textView24;
    TextView textView25;
    TextView textView26;
    ImageView imageView3;
    int backhome=0;
    private Timer timerCall;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private int COLLISION_THRESHOLD = 25000;  // 충돌 임계값

    private static SensorManager mSensorManager;
    private Sensor mAccelerometer; // 가속도 센스
    private Sensor mMagnetometer; // 자력계 센스
    float[] mGravity = null;
    float[] mGeomagnetic = null;

    String resultText = "";
    SensorManager sensorManager;
    Sensor stepCountSensor;
    float azimut, pitch, roll;
    // 현재 걸음 수
    int currentSteps = 0;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mMagnetometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        button19 = findViewById(R.id.button19);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView24 = findViewById(R.id.textView24);
        textView25 = findViewById(R.id.textView25);
        textView26 = findViewById(R.id.textView26);
        imageView3 = findViewById(R.id.imageView3);

        imageView3.setImageResource(R.drawable.user);

        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), call_emergency.class);
                startActivity(intent);
            }
        });

        Timer timer = new Timer();

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backhome++;
                if(backhome == 15) {
                    SharedPreferences sf = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.remove("loginId");
                    editor.remove("loginPwd");
                    editor.commit();
                    backhome =0;
                    Intent intent = new Intent(getApplicationContext(),alone_log.class);
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

        // 활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean on ;
                on = isScreenOn();
                Log.e("ete", "" + on);
                if(on == false) {
                    try {
                        new Task2().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else  {
                    try {
                        new Task3().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timerCall = new Timer();
        timerCall.schedule(timerTask,0,3000);

    }

    private boolean isScreenOn(){

        KeyguardManager pm = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return pm.inKeyguardRestrictedInputMode();
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


    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;

            }
            textView9.setText(String.valueOf(event.values[0]));
            Log.e("event", String.valueOf(event.values[0]));
        }



        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            mGravity = event.values;
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
        }

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                // orientation contains: azimut, pitch	and roll
                azimut = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];

                textView24.setText("x 좌표:" + String.format("%.3f", azimut));
                textView25.setText("y 좌표:" + String.format("%.3f", pitch));
                textView26.setText("z 좌표 : " + String.format("%.3f", roll));
            }
        }
        // 걸음 센서 이벤트 발생시

        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis(); // 현재시간


            // 0.1초 간격으로 가속도값을 업데이트
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                // 내가 마음대로 정한 충돌량
                double collision_detect = Math.sqrt(Math.pow(z - last_z, 2) * 100 + Math.pow(x - last_x, 2) * 10 + Math.pow(y - last_y, 2) * 10) / diffTime * 10000;


                if (collision_detect > COLLISION_THRESHOLD) {
                    //지정된 수치이상 흔들림이 있으면 실행
                    try {
                        resultText = new Task1().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("result",resultText);
//                    Toast.makeText(this, "충돌!!", Toast.LENGTH_SHORT).show();
                }
                //갱신
                last_x = x;
                last_y = y;
                last_z = z;
            }


            if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                textView9.setText("Step Count : " + String.valueOf(event.values[0]));
            }



            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity = event.values;
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mGeomagnetic = event.values;
            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetometer,
                SensorManager.SENSOR_DELAY_UI);
    }

}

class Task1 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;
    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            url = new URL("http://172.20.10.2:8000/fall_detect"); // 마지막에는 / 넣지 말기

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



class Task2 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;
    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            url = new URL("http://172.20.10.2:8000/screen"); // 마지막에는 / 넣지 말기

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


class Task3 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;
    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            url = new URL("http://172.20.10.2:8000/false"); // 마지막에는 / 넣지 말기

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