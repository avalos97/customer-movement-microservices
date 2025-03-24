package com.devsu.res.customer_service.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilidad para el manejo de fechas y horas.
 */
public class DateUtil {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

    /**
     * Formatea un objeto LocalDateTime al formato definido.
     *
     * @param dateTime El objeto LocalDateTime a formatear.
     * @return Una cadena con la fecha y hora formateadas.
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    /**
     * Parsea una cadena que representa una fecha y hora al objeto LocalDateTime.
     *
     * @param dateTimeStr La cadena a parsear.
     * @return El objeto LocalDateTime resultante.
     */
    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}