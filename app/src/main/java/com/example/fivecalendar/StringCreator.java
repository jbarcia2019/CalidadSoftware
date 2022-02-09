package com.example.fivecalendar;

import java.util.Calendar;
import java.util.Locale;

public class StringCreator {

    public static String fechaString(int[] fecha) {
        if (fecha != null && fecha.length == 3) return String.format(Locale.getDefault(), "%02d", fecha[0]) + "/" + String.format(Locale.getDefault(), "%02d", fecha[1] + 1) + "/" + String.format(Locale.getDefault(), "%02d", fecha[2]);
        return "";
    }

    public static String horaString(int[] hora) {
        if (hora != null && hora.length == 2) return String.format(Locale.getDefault(), "%02d", hora[0]) + ":" + String.format(Locale.getDefault(), "%02d", hora[1]);
        return "";
    }

    public static char letraDia(int dia) {
        switch (dia) {
            case Calendar.SUNDAY:
                return 'D';
            case Calendar.MONDAY:
                return 'L';
            case Calendar.TUESDAY:
                return 'M';
            case Calendar.WEDNESDAY:
                return 'X';
            case Calendar.THURSDAY:
                return 'J';
            case Calendar.FRIDAY:
                return 'V';
            case Calendar.SATURDAY:
                return 'S';
            default:
                return '?';
        }
    }

}
