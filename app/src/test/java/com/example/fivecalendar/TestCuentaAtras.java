package com.example.fivecalendar;

import org.junit.Test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TestCuentaAtras {
    @Test
    public void cuenta_isCorrect() throws ParseException {
        Date dateNow = new Date();

        Calendar calendarInitial = Calendar.getInstance();
        calendarInitial.setTime(dateNow);
        calendarInitial.add(Calendar.DATE, +1);

        Calendar calendarHoraInicio = Calendar.getInstance();
        calendarHoraInicio.setTime(calendarInitial.getTime());

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.add(Calendar.MONTH, +1);
        calendarFinal.add(Calendar.HOUR_OF_DAY, +1);

        Tarea sol = new Tarea(
                "Test",
                "",
                calendarInitial,
                calendarHoraInicio,
                calendarFinal
        );

        long expectedDays = TimeUnit.DAYS.toMillis(1);
        long expectedSeconds = expectedDays / 1000;

        assertEquals(sol.getCountDown(), expectedSeconds);
    }
}