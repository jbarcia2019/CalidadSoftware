package com.example.fivecalendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HorarioActivity extends AppCompatActivity {

    private TextView fechaIniTV, fechaFinTV;
    private int[] fechaIni, fechaFin;

    private void actualizarClases() {

        Calendario calendario = Calendario.getInstance();
        List<Integer> indexes = new ArrayList<>();
        List<Clase> clases = calendario.getHorario().getClases();
        for(int i=0; i<clases.size(); i++) indexes.add(i);
        LinearLayout layout = findViewById(R.id.scroll_view_horario_vertical_linear_layout);

        layout.removeAllViewsInLayout();
        ContextThemeWrapper newContext;

        if(clases.size() == 0) {
            newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_PlainText);
            AppCompatTextView noClasesText = new AppCompatTextView(newContext);
            noClasesText.setWidth(layout.getWidth());
            noClasesText.setPadding(10, 0, 10, 10);
            noClasesText.setTextSize(20);
            noClasesText.setGravity(Gravity.CENTER);
            noClasesText.setText("No tienes Clases guardadas en tu horario");
            layout.addView(noClasesText);
        } else {
            newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_TareaButton);
            for (int i=0; i<clases.size(); i++) {
                Clase clase = clases.get(i);
                int index = indexes.get(i);
                AppCompatButton button = new AppCompatButton(newContext);
                int[] horaInicio = {clase.getHoraInicio().get(Calendar.HOUR_OF_DAY), clase.getHoraInicio().get(Calendar.MINUTE)};
                int[] horaFin = {clase.getHoraFin().get(Calendar.HOUR_OF_DAY), clase.getHoraFin().get(Calendar.MINUTE)};
                String texto = StringCreator.letraDia(clase.getDiaSemana()) + "\t\t\t\t" + StringCreator.horaString(horaInicio) + " - " + StringCreator.horaString(horaFin) + "    " + clase.getNombre();
                button.setText(texto);
                button.setGravity(Gravity.START);
                button.setGravity(Gravity.CENTER_VERTICAL);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HorarioActivity.this, ClaseActivity.class);
                        intent.putExtra("clase", clase);
                        intent.putExtra("index_horario", index);
                        startActivity(intent);
                    }
                });
                layout.addView(button);
            }
        }
    }

    private void actualizarDisplayFecha(TextView display, int[] fecha) {
        display.setText(StringCreator.fechaString(fecha));
    }

    private void cargarLayoutNormal() {
        setContentView(R.layout.activity_horario);
        fechaIniTV = findViewById(R.id.tvPickerFechaInicioHorario);
        fechaFinTV = findViewById(R.id.tvPickerFechaFinHorario);
        Calendario calendario = Calendario.getInstance();
        Calendar fIni = calendario.getHorario().getFechaInicio();
        Calendar fFin = calendario.getHorario().getFechaFin();
        fechaIni = new int[]{fIni.get(Calendar.DATE), fIni.get(Calendar.MONTH), fIni.get(Calendar.YEAR)};
        fechaFin = new int[]{fFin.get(Calendar.DATE), fFin.get(Calendar.MONTH), fFin.get(Calendar.YEAR)};
        actualizarDisplayFecha(fechaIniTV, fechaIni);
        actualizarDisplayFecha(fechaFinTV, fechaFin);
        actualizarClases();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendario calendario = Calendario.getInstance();
        if(calendario.getHorario() == null) {
            setContentView(R.layout.activity_horario_null);
        } else {
            cargarLayoutNormal();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendario calendario = Calendario.getInstance();
        if(calendario.getHorario() != null) actualizarClases();
    }

    public void crearHorario(View v) {
        Calendario calendario = Calendario.getInstance();
        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.add(Calendar.WEEK_OF_YEAR, 1);
        calendario.setHorario(new Horario(fechaInicio, fechaFin));
        calendario.guardar(new File(getFilesDir(), "calendario.bin"));
        cargarLayoutNormal();
    }

    public void anadirClase(View v) {
        Intent intent = new Intent(HorarioActivity.this, ClaseActivity.class);
        startActivity(intent);
    }

    public void actualizarCalendario(View v) {
        Calendario calendario = Calendario.getInstance();
        calendario.actualizarHorario();
        calendario.guardar(new File(getFilesDir(), "calendario.bin"));
        Toast.makeText(HorarioActivity.this, "Horario Sincronizado", Toast.LENGTH_SHORT).show();
    }

    public void abrirFechaInicio(View v) {

        DatePickerDialog dialog = new DatePickerDialog(
                HorarioActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            Horario.updateFecha(calendar);
                            Calendario calendario = Calendario.getInstance();
                            Horario horarioAux = new Horario(calendar, calendario.getHorario().getFechaFin());
                            calendario.getHorario().getFechaInicio().set(year, month, dayOfMonth);
                            calendario.guardar(new File(getFilesDir(), "calendario.bin"));
                            fechaIni[0] = dayOfMonth;
                            fechaIni[1] = month;
                            fechaIni[2] = year;
                            actualizarDisplayFecha(fechaIniTV, fechaIni);
                        } catch (IncorrectDatesException exception) {
                            Toast.makeText(HorarioActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                fechaIni[2], fechaIni[1], fechaIni[0]);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void abrirFechaFin(View v) {

        DatePickerDialog dialog = new DatePickerDialog(
                HorarioActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Calendario calendario = Calendario.getInstance();
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            Horario.updateFecha(calendar);
                            Horario horarioAux = new Horario(calendario.getHorario().getFechaInicio(), calendar);
                            calendario.getHorario().getFechaFin().set(year, month, dayOfMonth);
                            calendario.guardar(new File(getFilesDir(), "calendario.bin"));
                            fechaFin[0] = dayOfMonth;
                            fechaFin[1] = month;
                            fechaFin[2] = year;
                            actualizarDisplayFecha(fechaFinTV, fechaFin);
                        } catch (IncorrectDatesException exception) {
                            Toast.makeText(HorarioActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                fechaFin[2], fechaFin[1], fechaFin[0]);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

}