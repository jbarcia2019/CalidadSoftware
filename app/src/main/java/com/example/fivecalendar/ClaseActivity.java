package com.example.fivecalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class ClaseActivity extends AppCompatActivity {

    private static final String TAG = "NewTareaActivity";
    private EditText nombre, descripcion;
    private TextView displayHoraInicio, displayHoraFin;
    private AppCompatSpinner spinner;

    private int indexHorario;
    private int diaSemana;
    private int[] horaInicio = new int[2];
    private int[] horaFin = new int[2];

    private void actualizarDisplayHora(TextView display, int[] hora) {
        display.setText(StringCreator.horaString(hora));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase);

        Bundle extras = getIntent().getExtras();

        nombre = findViewById(R.id.editTextNameClase);
        descripcion = findViewById(R.id.editTextDescripcionClase);
        displayHoraInicio = findViewById(R.id.editTextHoraInicioClase);
        displayHoraFin = findViewById(R.id.editTextHoraFinClase);

        if (extras == null || !extras.containsKey("index_horario")) {

            indexHorario = -1;

            FloatingActionButton deleteButton = findViewById(R.id.floatingActionButtonClaseDelete);
            deleteButton.hide();

            Calendar calendar = Calendar.getInstance();
            horaInicio[0] = calendar.get(Calendar.HOUR_OF_DAY);
            horaInicio[1] = calendar.get(Calendar.MINUTE);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            horaFin[0] = calendar.get(Calendar.HOUR_OF_DAY);
            horaFin[1] = calendar.get(Calendar.MINUTE);
            diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

        } else {

            indexHorario = (int) extras.get("index_horario");

            Calendario calendario = Calendario.getInstance();
            Clase clase = calendario.getHorario().getClase(indexHorario);

            nombre.setText(clase.getNombre());
            descripcion.setText(clase.getDescripcion());
            horaInicio[0] = clase.getHoraInicio().get(Calendar.HOUR_OF_DAY);
            horaInicio[1] = clase.getHoraInicio().get(Calendar.MINUTE);
            horaFin[0] = clase.getHoraFin().get(Calendar.HOUR_OF_DAY);
            horaFin[1] = clase.getHoraFin().get(Calendar.MINUTE);
            diaSemana = clase.getDiaSemana();

        }

        actualizarDisplayHora(displayHoraInicio, horaInicio);
        actualizarDisplayHora(displayHoraFin, horaFin);


        spinner = findViewById(R.id.diaSemanaSpinnerClase);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection((diaSemana + 5) % 7);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaSemana = (position + 1) % 7 + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection((diaSemana + 5) % 7);

            }
        });

    }

    public void guardarClase(View v) {
        if(!nombre.getText().toString().equals("")) {
            Calendar horaInicioClase = Calendar.getInstance();
            horaInicioClase.set(Calendar.HOUR_OF_DAY, horaInicio[0]);
            horaInicioClase.set(Calendar.MINUTE, horaInicio[1]);
            Calendar horaFinClase = Calendar.getInstance();
            horaFinClase.set(Calendar.HOUR_OF_DAY, horaFin[0]);
            horaFinClase.set(Calendar.MINUTE, horaFin[1]);
            try {
                Clase newClase = new Clase(nombre.getText().toString(), descripcion.getText().toString(), diaSemana, horaInicioClase, horaFinClase);
                Calendario cal = Calendario.getInstance();
                if (indexHorario != -1) {
                    cal.getHorario().eliminarClase(indexHorario);
                    getIntent().putExtra("clase", newClase);
                }
                cal.getHorario().agregarClase(newClase);
                cal.guardar(new File(getFilesDir(), "calendario.bin"));
                this.finish();
            } catch (IncorrectHoursException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Por favor, ponle nombre a tu clase", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarClase(int index) {
        Calendario calendario = Calendario.getInstance();
        calendario.getHorario().eliminarClase(index);
        calendario.guardar(new File(getFilesDir(), "calendario.bin"));
        Toast.makeText(this, "Clase eliminada", Toast.LENGTH_LONG).show();
        finish();
    }

    public void eliminarClase(View v) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Theme_FiveCalendar_Dialog);
        AlertDialog.Builder builderAD = new AlertDialog.Builder(newContext);
        builderAD.setTitle("Eliminar Clase");
        builderAD.setMessage("¿Estás seguro de que quieres eliminar esta clase?");
        builderAD.setNegativeButton("Cancelar", null);
        builderAD.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                eliminarClase((int) getIntent().getExtras().get("index_horario"));
            }
        });
        builderAD.create().show();
    }

    public void back(View v) {
        finish();
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