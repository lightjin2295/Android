package com.example.bottombar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import java.util.Calendar;

public class FragmentCamera extends Fragment {

    MainActivity mainActivity;

    public static FragmentCamera newInstance(){
        return new FragmentCamera();
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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_camera,container,false);
        /*Button btnSetAlarm = (Button)rootview.findViewById(R.id.btnSetAlarm);
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onChangeFragment(1);
            }
        });*/

        /*Button button3 = (Button)rootview.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onChangeFragment(0);
            }
        });*/

        return rootview;
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
