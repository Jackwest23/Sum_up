package com.example.sumup;




import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.sumup.databinding.SetnotifBinding;

import java.sql.Time;
import java.util.Calendar;

public class setnotif extends Fragment {
    private SetnotifBinding binding;
    DatePicker datePicker;
    TimePicker timePicker;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SetnotifBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CreateChannel();
        datePicker = getView().findViewById(R.id.datePicker);
        timePicker = getView().findViewById(R.id.timePicker);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),broadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
             long formatTime= getTime();
             alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+ formatTime,pendingIntent);
            }
        });

    }
    public void CreateChannel(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES .O){
            NotificationChannel channel = new NotificationChannel("Sumup notification", "Sum up notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationManagerCompat Mangercomp = NotificationManagerCompat.from(getContext());
            Mangercomp.createNotificationChannel(channel);
        }
    }
    public long getTime(){
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        Calendar calendar= Calendar.getInstance();
        calendar.set(year,month,day,hour,minute);
        return calendar.getTimeInMillis();
    }

}

