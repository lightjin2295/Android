package com.example.bottombar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottombar.FragmentCall;
import com.example.bottombar.FragmentCamera;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch = new FragmentSearch();
    private FragmentCamera fragmentCamera = new FragmentCamera();
    private FragmentCall fragmentCall = new FragmentCall();
    String[] strings = {"One","Two","Three","Four","aapl","Tsla","NasDaq"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss(); // 초기화면설정

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    //하단 BottomBar
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.searchItem:
                    transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();
                    break;
                case R.id.cameraItem:
                    transaction.replace(R.id.frameLayout, fragmentCamera).commitAllowingStateLoss();
                    break;
                case R.id.callItem:
                    transaction.replace(R.id.frameLayout, fragmentCall).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    /*public void onChangeFragment(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentSearch).commit();
        }else if(index ==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentCamera).commit();
        }
    }*/


    //해당 instnace를 가지고 해당화면으로 이동시
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }

    public void timePickerdialog(){

        DialogFragment timePickerDialog =new com.example.timepicker.TimePickerFragment();
        timePickerDialog.show(getSupportFragmentManager(),"time picker");
        show();
    }

    public void onTimeSet(TimePicker timePicker, int intHourOfDay, int intMinute){

        TextView txtViewPicked = (TextView)findViewById(R.id.txtViewAlarm);
        txtViewPicked.setText(("TimePicked=" + intHourOfDay + ":" + intMinute));

    }

    private void show() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        // 필수 항목
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림 제목");
        builder.setContentText("알림 세부 텍스트");

        // 액션 정의
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        txtViewAlarm.setText("Alarm");


        // 클릭 이벤트 설정
        builder.setContentIntent(pendingIntent);

        // 큰 아이콘 설정
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(largeIcon);

        // 색상 변경
        builder.setColor(Color.RED);

        // 기본 알림음 사운드 설정
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        // 진동설정: 대기시간, 진동시간, 대기시간, 진동시간 ... 반복 패턴
        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true);

        // 알림 매니저
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 오레오에서는 알림 채널을 매니저에 생성해야 한다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // 알림 통지
        manager.notify(1, builder.build());
    }



}