package com.example.fivecalendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calendario implements Serializable {

    private List<Tarea> tareas = new ArrayList<>();
    private Horario horario;
    private static Calendario instance;

    private Calendario() {
        /*Calendar fecha = Calendar.getInstance();
        Horario.updateFecha(fecha);
        Calendar horaInicio = Calendar.getInstance();
        Calendar horaFin = Calendar.getInstance();
        horaFin.add(Calendar.HOUR, 2);
        agregarTarea(new Tarea("Demo", fecha, horaInicio, horaFin));*/
    }

    public static Calendario getInstance() {
        return getInstance(null);
    }

    public static Calendario getInstance(File file) {
        if (instance == null){
            if (file != null) {
                try {
                    cargar(file);
                } catch (FileNotFoundException e) {
                    instance = new Calendario();
                    instance.guardar(file);
                }
            } else {
                instance = new Calendario();
            }
        }
        return instance;
    }

    public static void cargar(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            instance = (Calendario) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar(File file) {
        try {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException fnfe) {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
            } finally {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(instance);
                objectOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public void agregarTarea(Tarea tarea) {
        int index = 0;
        while(index < tareas.size() && tareas.get(index).before(tarea)) {
            index++;
        }
        if(index < tareas.size() && tareas.get(index).equals(tarea)) return;
        tareas.add(index, tarea);
    }

    public void eliminarTarea(int index) {
        tareas.remove(index);
    }

    public Tarea getTarea(int index) {
        return tareas.get(index);
    }

    public List<Tarea> getTareasDia(int dia, int mes, int anio) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(anio, mes, dia);
        Horario.updateFecha(fecha);
        List<Tarea> tareas = new ArrayList<>();
        int index = 0;
        while(index < this.tareas.size() && this.tareas.get(index).getFecha().before(fecha)) {
            index++;
        }
        //fecha.add(Calendar.DAY_OF_YEAR, 1);
        while(index < this.tareas.size() && !fecha.before(this.tareas.get(index).getFecha())) {
            tareas.add(this.tareas.get(index));
            index++;
        }
        return tareas;
    }

    public List<Tarea> getTareasDia(int dia, int mes, int anio, List<Integer> indexes) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(anio, mes, dia);
        Horario.updateFecha(fecha);
        List<Tarea> tareas = new ArrayList<>();
        int index = 0;
        while(index < this.tareas.size() && this.tareas.get(index).getFecha().before(fecha)) {
            index++;
        }
        //fecha.add(Calendar.DAY_OF_YEAR, 1);
        while(index < this.tareas.size() && !fecha.before(this.tareas.get(index).getFecha())) {
            tareas.add(this.tareas.get(index));
            indexes.add(index);
            index++;
        }
        return tareas;
    }

    public List<Tarea> getTareasSemana(int dia, int mes, int anio) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(anio, mes, dia);
        if(fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            fecha.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            fecha.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            fecha.add(Calendar.DAY_OF_YEAR, 1);
        }
        Horario.updateFecha(fecha);
        List<Tarea> tareas = new ArrayList<>();
        int index = 0;
        while(index < this.tareas.size() && this.tareas.get(index).getFecha().before(fecha)) {
            index++;
        }
        fecha.add(Calendar.WEEK_OF_YEAR, 1);
        while(index < this.tareas.size() && this.tareas.get(index).getFecha().before(fecha)) {
            tareas.add(this.tareas.get(index));
            index++;
        }
        return tareas;
    }

    public List<Tarea> getTareasMes(int mes, int anio) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(anio, mes, 1);
        Horario.updateFecha(fecha);
        List<Tarea> tareas = new ArrayList<>();
        int index = 0;
        while(index < this.tareas.size() && this.tareas.get(index).getFecha().before(fecha)) {
            index++;
        }
        fecha.add(Calendar.MONTH, 1);
        while(index < this.tareas.size() && this.tareas.get(index).getFecha().before(fecha)) {
            tareas.add(this.tareas.get(index));
            index++;
        }
        return tareas;
    }

    public void actualizarHorario() {
        for(Clase clase: horario.getClases()) {
            Calendar fecha = (Calendar) horario.getFechaInicio().clone();
            if(fecha.get(Calendar.DAY_OF_WEEK) > clase.getDiaSemana()) {
                fecha.add(Calendar.DAY_OF_MONTH, 7);
            }
            fecha.set(Calendar.DAY_OF_WEEK, clase.getDiaSemana());
            while(fecha.before(horario.getFechaFin())) {
                agregarTarea(new Tarea(clase.getNombre(), clase.getDescripcion(), (Calendar) fecha.clone(), clase.getHoraInicio(), clase.getHoraFin()));
                fecha.add(Calendar.DAY_OF_MONTH, 7);
            }
        }
    }

}
