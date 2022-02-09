package com.example.fivecalendar;

public class IncorrectHoursException extends RuntimeException {

    public IncorrectHoursException() {
        super("Hora final menor que hora inicio");
    }

}
