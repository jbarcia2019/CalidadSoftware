package com.example.fivecalendar;

import java.util.Calendar;

public class Clase extends Evento {

	private int diaSemana;

	public Clase(String nombre, String descripcion, int diaSemana, Calendar horaInicio, Calendar horaFin) {
		super(nombre, descripcion, horaInicio, horaFin);
		this.diaSemana = diaSemana;
	}

	public Clase(String nombre, int diaSemana, Calendar horaInicio, Calendar horaFin) {
		super(nombre, "", horaInicio, horaFin);
		this.diaSemana = diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getDiaSemana() {
		return diaSemana;
	}

	public boolean before(Clase clase) {
		if(diaSemana < clase.diaSemana) {
			return true;
		} else if(diaSemana > clase.diaSemana) {
			return false;
		}
		return super.before(clase);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Clase clase = (Clase) o;
		return diaSemana == clase.diaSemana;
	}

}
