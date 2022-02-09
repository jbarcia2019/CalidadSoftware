package com.example.fivecalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DiaActivity extends AppCompatActivity {

    private int[] fecha;
    private String fechaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia);
        fecha = (int[]) getIntent().getExtras().get("fecha");
        fechaString = StringCreator.fechaString(fecha);
        Toolbar toolbar = findViewById(R.id.toolbarDia);
        toolbar.setTitle("Tareas del día " + fechaString);
        actualizarTareas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarTareas();
    }

    private void actualizarTareas() {

        Calendario calendario = Calendario.getInstance();
        List<Integer> indexes = new ArrayList<>();
        List<Tarea> tareas = calendario.getTareasDia(fecha[0], fecha[1], fecha[2], indexes);
        LinearLayout layout = findViewById(R.id.scroll_view_linear_layout);
        layout.removeAllViewsInLayout();
        ContextThemeWrapper newContext;

        if(tareas.size() == 0) {
            newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_PlainText);
            AppCompatTextView noTareasText = new AppCompatTextView(newContext);
            noTareasText.setText("No tienes tareas el día " + fechaString);
            layout.addView(noTareasText);
        } else {
            newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_TareaButton);
            for (int i=0; i<tareas.size(); i++) {
                Tarea tarea = tareas.get(i);
                int index = indexes.get(i);
                AppCompatButton button = new AppCompatButton(newContext);
                int[] horaInicio = {tarea.getHoraInicio().get(Calendar.HOUR_OF_DAY), tarea.getHoraInicio().get(Calendar.MINUTE)};
                int[] horaFin = {tarea.getHoraFin().get(Calendar.HOUR_OF_DAY), tarea.getHoraFin().get(Calendar.MINUTE)};
                String texto = StringCreator.horaString(horaInicio) + " - " + StringCreator.horaString(horaFin) + "    " + tarea.getNombre();
                button.setText(texto);
                button.setGravity(Gravity.START);
                button.setGravity(Gravity.CENTER_VERTICAL);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DiaActivity.this, TareaActivity.class);
                        intent.putExtra("tarea", tarea);
                        intent.putExtra("index_calendario", index);
                        startActivity(intent);
                    }
                });
                layout.addView(button);
            }
        }
    }

    public void anadirTarea(View view) {
        Intent intent = new Intent(DiaActivity.this, NewTareaActivity.class);
        intent.putExtra("fecha", fecha);
        startActivity(intent);
    }
}