package com.example.fivecalendar;

import java.io.Serializable;
import java.util.Calendar;

public abstract class Evento implements Serializable {

	private Calendar horaInicio, horaFin;
	private String nombre;
	private String descripcion;

	static void updateHora(Calendar hora) {
		Calendar auxCal = Calendar.getInstance();
		hora.set(auxCal.get(Calendar.YEAR), auxCal.get(Calendar.MONTH), auxCal.get(Calendar.DATE));
	}

	public Evento(String nombre, String descripcion, Calendar horaInicio, Calendar horaFin) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		updateHora(horaInicio);
		updateHora(horaFin);
		setHoras(horaInicio, horaFin);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setHoras(Calendar horaInicio, Calendar horaFin) {
		updateHora(horaInicio);
		updateHora(horaFin);
		if(horaInicio.before(horaFin)) {
			this.horaInicio = horaInicio;
			this.horaFin = horaFin;
		} else {
			throw new IncorrectHoursException();
		}
	}

	public void setHoraInicio(Calendar hora) {
		setHoras(hora, horaFin);
	}

	public void setHoraFin(Calendar hora) {
		setHoras(horaInicio, hora);
	}

	public Calendar getHoraInicio() {
		return horaInicio;
	}

	public Calendar getHoraFin() {
		return horaFin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Evento evento = (Evento) o;
		return horaInicio.equals(evento.horaInicio) && horaFin.equals(evento.horaFin) && nombre.equals(evento.nombre);
	}

	protected boolean before(Evento evento) {
		updateHora(evento.getHoraInicio());
		updateHora(horaInicio);
		return horaInicio.before(evento.getHoraInicio());
	}

}
