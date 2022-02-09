package com.example.fivecalendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        cal = findViewById(R.id.calendarView);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int[] fechaArr = {dayOfMonth, month, year};
                Intent intent = new Intent(CalendarioActivity.this, DiaActivity.class);
                intent.putExtra("fecha", fechaArr);
                startActivity(intent);
            }
        });
    }
}