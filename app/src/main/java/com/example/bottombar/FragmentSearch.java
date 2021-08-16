package com.example.bottombar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.app.AlarmManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class FragmentSearch extends DialogFragment {

    MainActivity mainActivity;

    public FloatingActionButton floatingActionButton;

    //instance를 해당 화면마다 선언
    public static FragmentSearch newInstance(){
        return new FragmentSearch();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_search,container,false);
//        Button btnSetAlarm = (Button)rootview.findViewById(R.id.btnSetAlarm);
        CountDownTimer countDownimer;

//        Button button3  =(Button)rootview.findViewById(R.id.button3);
        TextView usTimer = (TextView)rootview.findViewById(R.id.usTimer);
        TextView usText = (TextView)rootview.findViewById(R.id.usText);

        FloatingActionButton setStockAlarm = (FloatingActionButton)rootview.findViewById(R.id.setStockAlarm);
//        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //버튼 클릭시 원하는 instance화면으로 이동
//                ((MainActivity)getActivity()).replaceFragment(FragmentCamera.newInstance());
//            }
//        });

//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).timePickerdialog();
//            }
//        });


        //검색화면으로 이동
        setStockAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StockActivity.class);
                startActivity(intent);
            }
        });

        countDownimer = new CountDownTimer(20000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                //시간 및 남은시간 설정
                usTimer.setText(getTime());
                usText.setText(getText());
            }
            @Override
            public void onFinish() {

            }

        };
        countDownimer.start();


        return rootview;
    }

    //남은시간 텍스트 설정
    private String getText(){

        Calendar openTime = new GregorianCalendar();
        Calendar closeTime = new GregorianCalendar();
        Calendar currentTime = new GregorianCalendar();
        Date date = new Date();

        currentTime.setTime(date);

//        System.out.println("현재시간"+" "+currentTime.getTime());


        openTime.set(Calendar.HOUR_OF_DAY,22);
        openTime.set(Calendar.MINUTE,30);
        openTime.set(Calendar.SECOND,00);
        //개장시간

        closeTime.set(Calendar.HOUR_OF_DAY,05);
        closeTime.set(Calendar.MINUTE,30);
        closeTime.set(Calendar.SECOND,00);
        //폐장시간

        //기준을 오픈시간으로한다고 치면 .


        //만약에 현재시간이랑 currentTime을 비교해서 현재시간 > CurrentTime 크면 Text는 폐장 남은시간으로변경
        //열시반 ~ 다섯시반
//        System.out.println("현재시간"+" "+currentTime.getTimeInMillis());
//        System.out.println("개장시간"+" "+openTime.getTimeInMillis());
//        System.out.println(currentTime.getTimeInMillis()- openTime.getTimeInMillis());
        if(currentTime.getTimeInMillis() > openTime.getTimeInMillis()) {
            return "폐장 남은시간";
        }else{
            return "개장 남은시간";
        }
    }


    //남은시간 텍스트 설정
    private String getTime(){
//        TimeZone tz;
//        TimeZone ktz;
//        tz = TimeZone.getTimeZone("America/New_York");
//        ktz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정
//        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat aFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        AlarmManager alarmManager;
//        aFormat.setTimeZone(tz);
//        mFormat.setTimeZone(ktz);

//        Calendar calendar = new GregorianCalendar();
//        Calendar Acalendar = new GregorianCalendar();
//        Calendar Kcalendar = new GregorianCalendar();

        Calendar settingTime = new GregorianCalendar();
        Calendar openTime = new GregorianCalendar();
        Calendar closeTime = new GregorianCalendar();
        Calendar currentTime = new GregorianCalendar();

        Date date = new Date();
        currentTime.setTime(date);

        //개장시간
        openTime.set(Calendar.HOUR_OF_DAY,22);
        openTime.set(Calendar.MINUTE,30);
        openTime.set(Calendar.SECOND,00);

        //폐장시간
//        closeTime.set(Calendar.DAY,currentTime.DATE + 1);

        closeTime.add(Calendar.DAY_OF_MONTH, 1);
        closeTime.set(Calendar.HOUR_OF_DAY,05);
        closeTime.set(Calendar.MINUTE,30);
        closeTime.set(Calendar.SECOND,00);

//        System.out.println("한국시간" +" "+ baseCal.getTime());
//        System.out.println("미국시간"+" "+targetCal.getTime());
//        System.out.println("목표시간"+" "+Ccalendar.getTime());


        long diffSec = (openTime.getTimeInMillis() - currentTime.getTimeInMillis()) / 1000;
        long diffSec2 = (currentTime.getTimeInMillis() - closeTime.getTimeInMillis()) / 1000;
//        long diffDays = diffSec / (24*60*60);


//        System.out.println("diffSec" + Ccalendar.getTimeInMillis());
//        System.out.println("diffSec" + diffSec);
//        System.out.println("diffDays"+" "+diffDays);

//        targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));

        int oHourTime = (int)Math.floor((double)(diffSec/3600));
        int oMinTime = (int)Math.floor((double)(((diffSec - (3600 * oHourTime)) / 60)));
        int oSecTime = (int)Math.floor((double)(((diffSec - (3600 * oHourTime)) - (60 * oMinTime))));

        int cHourTime = (int)Math.floor((double)(diffSec2/3600));
        int cMinTime = (int)Math.floor((double)(((diffSec2 - (3600 * cHourTime)) / 60)));
        int cSecTime = (int)Math.floor((double)(((diffSec2 - (3600 * cHourTime)) - (60 * cMinTime))));

        String oHour = String.format("%02d", oHourTime);
        String oMin = String.format("%02d", oMinTime);
        String oSec = String.format("%02d", oSecTime);

        String cHour = String.format("%02d", cHourTime);
        String cMin = String.format("%02d", cMinTime);
        String cSec = String.format("%02d", cSecTime);

//        System.out.println("현재시간"+" "+currentTime.getTime());
//        System.out.println("개장시간"+" "+openTime.getTime());
//        System.out.println("폐장시간"+" "+closeTime.getTime());

        if(currentTime.getTimeInMillis() > openTime.getTimeInMillis()) {
            if (cHour.equals("00")) {
                return cMin + " 분남음";
            } else {
                return cHour + "시간" + cMin + "분남음";
            }
        }else if(currentTime.getTimeInMillis() < openTime.getTimeInMillis()){
            if (oHour.equals("00")) {
                return oMin + " 분남음";
            } else {
                return oHour + "시간" + oMin + "분남음";
            }
        }

//        return  a_hour + " 시간 " +a_min + " 분 "+ a_sec + "초 남았습니다.";
    return "1";
    }
    /*public void BtnAlarmOnClick(View view) {

        txtViewAlarm = (TextView)findViewById(R.id.txtViewAlarm);
        //-------< BtnAlarmOnClick >--------
        //*click Button to set Time
        DialogFragment timePickerDialog =new com.example.timepicker.TimePickerFragment();
        timePickerDialog.show(getSupportFragmentManager(),"time picker");

//        show();
        //-------</ BtnAlarmOnClick >--------
    }

    public void onTimeSet(TimePicker timePicker, int intHourOfDay, int intMinute){

        TextView txtViewPicked = (TextView)findViewById(R.id.txtViewAlarm);
        txtViewPicked.setText(("TimePicked=" + intHourOfDay + ":" + intMinute));

    }*/




}
