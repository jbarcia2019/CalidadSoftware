package com.example.fivecalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class NewTareaActivity extends AppCompatActivity {

    private static final String TAG = "NewTareaActivity";
    private EditText nombre, descripcion;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView displayFecha, displayHoraInicio, displayHoraFin;

    private int indexCalendario;
    private int[] fecha = new int[3];
    private int[] horaInicio = new int[2];
    private int[] horaFin = new int[2];

    private void actualizarDisplayFecha() {
        displayFecha.setText(StringCreator.fechaString(fecha));
    }

    private void actualizarDisplayHora(TextView display, int[] hora) {
        display.setText(StringCreator.horaString(hora));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tarea);

        Bundle extras = getIntent().getExtras();
        Calendar calendar = Calendar.getInstance();

        nombre = findViewById(R.id.editTextTextPersonName2);
        descripcion = findViewById(R.id.editTextTextMultiLine);
        displayHoraInicio = findViewById(R.id.editTextTime);
        displayHoraFin = findViewById(R.id.editTextTime2);
        displayFecha = findViewById(R.id.tvDatePicker);

        if (extras == null || !extras.containsKey("index_calendario")) {
            indexCalendario = -1;
            horaInicio[0] = calendar.get(Calendar.HOUR_OF_DAY);
            horaInicio[1] = calendar.get(Calendar.MINUTE);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            horaFin[0] = calendar.get(Calendar.HOUR_OF_DAY);
            horaFin[1] = calendar.get(Calendar.MINUTE);
            if (extras == null || !extras.containsKey("fecha")) {
                fecha[2] = calendar.get(Calendar.YEAR);
                fecha[1] = calendar.get(Calendar.MONTH);
                fecha[0] = calendar.get(Calendar.DAY_OF_MONTH);
            } else {
                fecha = (int[]) extras.get("fecha");
            }
        } else {
            indexCalendario = (int) extras.get("index_calendario");
            Calendario calendario = Calendario.getInstance();
            Tarea tarea = calendario.getTarea(indexCalendario);
            nombre.setText(tarea.getNombre());
            descripcion.setText(tarea.getDescripcion());
            fecha[2] = tarea.getFecha().get(Calendar.YEAR);
            fecha[1] = tarea.getFecha().get(Calendar.MONTH);
            fecha[0] = tarea.getFecha().get(Calendar.DAY_OF_MONTH);
            horaInicio[0] = tarea.getHoraInicio().get(Calendar.HOUR_OF_DAY);
            horaInicio[1] = tarea.getHoraInicio().get(Calendar.MINUTE);
            horaFin[0] = tarea.getHoraFin().get(Calendar.HOUR_OF_DAY);
            horaFin[1] = tarea.getHoraFin().get(Calendar.MINUTE);
        }

        actualizarDisplayFecha();
        actualizarDisplayHora(displayHoraInicio, horaInicio);
        actualizarDisplayHora(displayHoraFin, horaFin);

    }

    public void guardarTarea(View v) {
        if(!nombre.getText().toString().equals("")) {
            Calendar fechaTarea = Calendar.getInstance();
            fechaTarea.set(fecha[2], fecha[1], fecha[0]);
            Calendar horaInicioTarea = Calendar.getInstance();
            horaInicioTarea.set(Calendar.HOUR_OF_DAY, horaInicio[0]);
            horaInicioTarea.set(Calendar.MINUTE, horaInicio[1]);
            Calendar horaFinTarea = Calendar.getInstance();
            horaFinTarea.set(Calendar.HOUR_OF_DAY, horaFin[0]);
            horaFinTarea.set(Calendar.MINUTE, horaFin[1]);
            try {
                Tarea NewTarea = new Tarea(nombre.getText().toString(), descripcion.getText().toString(), fechaTarea, horaInicioTarea, horaFinTarea);
                Calendario cal = Calendario.getInstance();
                if (indexCalendario != -1) {
                    cal.eliminarTarea(indexCalendario);
                    getIntent().putExtra("tarea", NewTarea);
                }
                cal.agregarTarea(NewTarea);
                cal.guardar(new File(getFilesDir(), "calendario.bin"));
                this.finish();
            } catch (IncorrectHoursException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Por favor, ponle nombre a tu tarea", Toast.LENGTH_LONG).show();
        }
    }

    public void abrirFecha(View v) {

        DatePickerDialog dialog = new DatePickerDialog(
                NewTareaActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha[0] = dayOfMonth;
                        fecha[1] = month;
                        fecha[2] = year;
                        actualizarDisplayFecha();
                    }
                },
                fecha[2], fecha[1], fecha[0]);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void abrirHoraInicio(View view) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_Dialog);
        TimePickerDialog tmd = new TimePickerDialog(newContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaInicio[0] = hourOfDay;
                horaInicio[1] = minute;
                actualizarDisplayHora(displayHoraInicio, horaInicio);
            }
        }, horaInicio[0], horaInicio[1], true);
        tmd.show();
    }

    public void abrirHoraFin(View view) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_Dialog);
        TimePickerDialog tmd = new TimePickerDialog(newContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaFin[0] = hourOfDay;
                horaFin[1] = minute;
                actualizarDisplayHora(displayHoraFin, horaFin);
            }
        }, horaFin[0], horaFin[1], true);
        tmd.show();
    }

}