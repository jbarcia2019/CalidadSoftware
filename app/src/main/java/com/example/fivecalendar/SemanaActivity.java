package com.example.fivecalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SemanaActivity extends AppCompatActivity {

    /*private Button BotonLun, BotonMar, BotonMie, BotonJue, BotonVie, BotonSab, BotonDom;
    private List<Tarea> tareas;
    private List<List<Tarea>> tareasDia = new ArrayList<>();*/
    private Calendar fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semana);

        fecha = Calendar.getInstance();
//        Calendario calendario = Calendario.getInstance();

        /*for(int i=0; i<7; i++) tareasDia.add(new ArrayList<>());

        tareas = calendario.getTareasSemana(fecha.get(Calendar.DAY_OF_MONTH), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR));*/

        /*lunes[0] = calendar.get(Calendar.MONDAY);
        lunes[1] = calendar.get(Calendar.MONTH);
        lunes[2] = calendar.get(Calendar.YEAR);
        martes[0] = calendar.get(Calendar.TUESDAY);
        martes[1] = calendar.get(Calendar.MONTH);
        martes[2] = calendar.get(Calendar.YEAR);
        miercoles[0] = calendar.get(Calendar.WEDNESDAY);
        miercoles[1] = calendar.get(Calendar.MONTH);
        miercoles[2] = calendar.get(Calendar.YEAR);
        jueves[0] = calendar.get(Calendar.THURSDAY);
        jueves[1] = calendar.get(Calendar.MONTH);
        jueves[2] = calendar.get(Calendar.YEAR);
        viernes[0] = calendar.get(Calendar.FRIDAY);
        viernes[1] = calendar.get(Calendar.MONTH);
        viernes[2] = calendar.get(Calendar.YEAR);
        sabado[0] = calendar.get(Calendar.SATURDAY);
        sabado[1] = calendar.get(Calendar.MONTH);
        sabado[2] = calendar.get(Calendar.YEAR);
        domingo[0] = calendar.get(Calendar.SUNDAY);
        domingo[1] = calendar.get(Calendar.MONTH);
        domingo[2] = calendar.get(Calendar.YEAR);

        tareasLun = calendario.getTareasDia(lunes[0], lunes[1], lunes[2]);
        tareasMar = calendario.getTareasDia(martes[0], martes[1], martes[2]);
        tareasMie = calendario.getTareasDia(miercoles[0], miercoles[1], miercoles[2]);
        tareasJue = calendario.getTareasDia(jueves[0], jueves[1], jueves[2]);
        tareasVie = calendario.getTareasDia(viernes[0], viernes[1], viernes[2]);
        tareasSab = calendario.getTareasDia(sabado[0], sabado[1], sabado[2]);
        tareasDom = calendario.getTareasDia(domingo[0], domingo[1], domingo[2]);*/

        /*BotonLun = findViewById(R.id.Lunes);
        BotonMar = findViewById(R.id.Martes);
        BotonMie = findViewById(R.id.Miercoles);
        BotonJue = findViewById(R.id.Jueves);
        BotonVie = findViewById(R.id.Viernes);
        BotonSab = findViewById(R.id.Sabado);
        BotonDom = findViewById(R.id.Domingo);*/
    }

    public void verLunes(View view) {

        /*final AlertDialog.Builder tareasAlertialog = new AlertDialog.Builder(this);
        List<String> tareas = new ArrayList<>();

        for(int i=0; i<tareasDia.get(0).size(); i++) {
            tareas.add(tareasDia.get(0).get(i).getNombre());
        }

        tareasAlertialog.setTitle("Tareas del Lunes");
        tareasAlertialog.setItems
        tareasAlertialog.setItems(tareasLun, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    fecha.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else {
                    fecha.add(Calendar.DAY_OF_YEAR, -6);
                }
                int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
                Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
                intent.putExtra("fecha", afecha);
                startActivity(intent);
            }
        });*/
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            fecha.add(Calendar.DAY_OF_YEAR, -6);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }

    public void verMartes(View view) {
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        } else {
            fecha.add(Calendar.DAY_OF_YEAR, -5);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }

    public void verMiercoles(View view) {
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        } else {
            fecha.add(Calendar.DAY_OF_YEAR, -4);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }

    public void verJueves(View view) {
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        } else {
            fecha.add(Calendar.DAY_OF_YEAR, -3);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }

    public void verViernes(View view) {
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        } else {
            fecha.add(Calendar.DAY_OF_YEAR, -2);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }

    public void verSabado(View view) {
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        } else {
            fecha.add(Calendar.DAY_OF_YEAR, -1);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }

    public void verDomingo(View view) {
        if(fecha.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            fecha.add(Calendar.DAY_OF_YEAR, 1);
        }
        int[] afecha = {fecha.get(Calendar.DATE), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)};
        Intent intent = new Intent(SemanaActivity.this, DiaActivity.class);
        intent.putExtra("fecha", afecha);
        startActivity(intent);
    }
}