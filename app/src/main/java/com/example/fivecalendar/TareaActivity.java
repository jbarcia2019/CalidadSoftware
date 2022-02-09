package com.example.fivecalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class TareaActivity extends AppCompatActivity {

    private Tarea tarea;
    private int tarea_index;
    private int[] fecha = new int[3];
    private int[] horaInicio = new int[2];
    private int[] horaFin = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        Toolbar toolbar = findViewById(R.id.toolbarTarea);
        TextView descripcionTV = findViewById(R.id.descripcionTareaTV);
        TextView fechaTV = findViewById(R.id.fechaTVTareaActivity);
        TextView horaTV = findViewById(R.id.horaTVTareaActivity);

        tarea = (Tarea) getIntent().getExtras().get("tarea");
        tarea_index = (int) getIntent().getExtras().get("index_calendario");
        fecha[0] = tarea.getFecha().get(Calendar.DAY_OF_MONTH);
        fecha[1] = tarea.getFecha().get(Calendar.MONTH);
        fecha[2] = tarea.getFecha().get(Calendar.YEAR);
        horaInicio[0] = tarea.getHoraInicio().get(Calendar.HOUR_OF_DAY);
        horaInicio[1] = tarea.getHoraInicio().get(Calendar.MINUTE);
        horaFin[0] = tarea.getHoraFin().get(Calendar.HOUR_OF_DAY);
        horaFin[1] = tarea.getHoraFin().get(Calendar.MINUTE);

        toolbar.setTitle(tarea.getNombre());
        String descripcion = tarea.getDescripcion();
        if(descripcion.equals("")) {
            TextView titDescripcion = findViewById(R.id.titDescripcionTarea);
            titDescripcion.setVisibility(View.INVISIBLE);
            descripcionTV.setVisibility(View.INVISIBLE);
        } else {
            descripcionTV.setText(descripcion);
        }
        fechaTV.setText(StringCreator.fechaString(fecha));
        String strHoraTV = "De " + StringCreator.horaString(horaInicio) + " a " + StringCreator.horaString(horaFin);
        horaTV.setText(strHoraTV);

    }

    public void editarTarea(View v) {

        Intent intent = new Intent(TareaActivity.this, NewTareaActivity.class);
        intent.putExtra("index_calendario", tarea_index);
        startActivity(intent);
        finish();

    }

    private void eliminarTarea(int index) {
        Calendario calendario = Calendario.getInstance();
        calendario.eliminarTarea(index);
        calendario.guardar(new File(getFilesDir(), "calendario.bin"));
        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_LONG).show();
        finish();
    }

    public void eliminarTarea(View v) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_Dialog);
        AlertDialog.Builder builderAD = new AlertDialog.Builder(newContext);
        builderAD.setTitle("Eliminar Tarea");
        builderAD.setMessage("¿Estás seguro de que quieres eliminar esta tarea?");
        builderAD.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                eliminarTarea((int) getIntent().getExtras().get("index_calendario"));
            }
        });
        builderAD.setNegativeButton("Cancelar", null);
        builderAD.create().show();
    }

}