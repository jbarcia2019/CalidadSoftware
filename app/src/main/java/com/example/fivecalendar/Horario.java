package com.example.fivecalendar;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Horario implements Serializable {

	private Calendar fechaInicio, fechaFin;
	private List<Clase> clases;

	static void updateFecha(Calendar fecha) {
		fecha.set(Calendar.HOUR_OF_DAY, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MILLISECOND, 0);
	}

	public Horario(Calendar fechaInicio, Calendar fechaFin) {
		setFechas(fechaInicio, fechaFin);
		clases = new ArrayList<>();
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public Calendar getFechaFin() {
		return fechaFin;
	}

	public void setFechas(Calendar fechaInicio, Calendar fechaFin) {
		updateFecha(fechaInicio);
		updateFecha(fechaFin);
		if (fechaInicio.before(fechaFin)) {
			this.fechaInicio = fechaInicio;
			this.fechaFin = fechaFin;
		} else {
			throw new IncorrectDatesException();
		}
	}

	public void setFechaInicio(Calendar fecha) {
		setFechas(fecha, fechaFin);
	}

	public void setFechaFin(Calendar fecha) {
		setFechas(fechaInicio, fecha);
	}

	public List<Clase> getClases() {
		return clases;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}

	public void agregarClase(Clase clase) {
		int index = 0;
		while (index < clases.size() && clases.get(index).before(clase)) {
			index++;
		}
		clases.add(index, clase);
	}

	public Clase getClase(int index) {
		return clases.get(index);
	}

	public void eliminarClase(int index) {
		clases.remove(index);
	}

}
