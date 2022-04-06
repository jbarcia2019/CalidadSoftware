package com.example.fivecalendar;

import java.util.Calendar;
import java.util.Date;
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

	public long getCountDown(){
		Date dateNow = new Date();
		Calendar calendar = Calendar.getInstance();
		Horario.updateFecha(calendar);

		Date dateRestar = calendar.getTime();

		long longFecha = fecha.getTime().getTime();
		long longFechaSec = longFecha / 1000;

		long longHora = getHoraInicio().getTime().getTime();
		long longHoraSec = longHora / 1000;

		long longRestar = dateRestar.getTime();
		long longRestarSec = longRestar / 1000;

		long longNow = dateNow.getTime();
		long longNowSec = longNow / 1000;

		long diffSec =  (longFechaSec + (longHoraSec - longRestarSec))- longNowSec;
		return diffSec;
	}

}
