package com.example.timepicker;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bottombar.FragmentSearch;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private AlarmManager mAlarmManager;
    private TextView txtViewAlarm;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        //< start with actual Time >

        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Calendar cal=Calendar.getInstance();
        int intHour=cal.get(Calendar.HOUR_OF_DAY);
        int intMinute=cal.get(Calendar.MINUTE);

        //</ start with actual Time >

        /*return new TimePickerDialog( getContext(), (TimePickerDialog.OnTimeSetListener) this,intHour,intMinute,true);*/
        return new TimePickerDialog( getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(),intHour,intMinute,true);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int intHourOfDay, int intMinute) {


        // 설정된 시간
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, intHourOfDay);
        calendar.set(Calendar.MINUTE, intMinute);

        // 알람이 동작되면 MainActivity를 실행하도록 동작 정의
        // 여기서 브로드캐스트나 서비스를 실행할 수도 있음
        Intent intent = new Intent(getContext(), FragmentSearch.class);
        PendingIntent operation = PendingIntent.getActivity(getContext(), 0, intent, 0);
//        txtViewAlarm.setText(intHourOfDay);

        // 설정된 시간에 기기가 슬립상태에서도 알람이 동작되도록 설정
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), operation);


    }






}

