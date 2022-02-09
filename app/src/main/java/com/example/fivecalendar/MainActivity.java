package com.example.fivecalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getFilesDir(), "calendario.bin");
        Calendario calendario = Calendario.getInstance(file);
    }

    public void abrirCalendario(View view) {
        Intent i = new Intent(MainActivity.this, CalendarioActivity.class);
        startActivity(i);
    }

    public void verSemana(View view) {
        Intent i = new Intent(MainActivity.this, SemanaActivity.class);
        startActivity(i);
    }

    public void anadirTarea(View view) {
        Intent i = new Intent(MainActivity.this, NewTareaActivity.class);
        startActivity(i);
    }

    public void abrirHorario(View view) {
        Intent i = new Intent(MainActivity.this, HorarioActivity.class);
        startActivity(i);
    }

    public void showHelp(View view) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_Dialog);
        AlertDialog.Builder builderAD = new AlertDialog.Builder(newContext);
        builderAD.setTitle("AYUDA");
        builderAD.setMessage(R.string.texto_ayuda);
        builderAD.setPositiveButton("OK", null);
        builderAD.create().show();
    }

}