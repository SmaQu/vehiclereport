package com.alastor.vehiclereport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DataUtils {
    public static String getData(long timeInMillis) {
        final SimpleDateFormat formatter =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return formatter.format(calendar.getTime());
    }
}
