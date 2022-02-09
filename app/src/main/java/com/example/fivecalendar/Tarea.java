package com.example.fivecalendar;

import java.util.Calendar;
import java.util.Objects;

public class Tarea extends Evento {

	private Calendar fecha;
	
	public Tarea(String nombre, String descripcion, Calendar fecha, Calendar horaInicio, Calendar horaFin) {
		super(nombre, descripcion, horaInicio, horaFin);
		Horario.updateFecha(fecha);
		this.fecha = fecha;
	}

	public Tarea(String nombre, Calendar fecha, Calendar horaInicio, Calendar horaFin) {
		super(nombre, "", horaInicio, horaFin);
		Horario.updateFecha(fecha);
		this.fecha = fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public boolean before(Tarea tarea) {
		Horario.updateFecha(fecha);
		Horario.updateFecha(tarea.fecha);
		return fecha.before(tarea.fecha) || (fecha.getTimeInMillis() == tarea.fecha.getTimeInMillis()) && super.before(tarea);
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Tarea tarea = (Tarea) o;
		return fecha.equals(tarea.fecha);
	}

}
