package cz.muni.fi.pa165.utils;

import java.time.format.DateTimeFormatter;

/**
 * Class for storing constant values used throughout the application
 *
 * @author Martin Vrábel
 * @version 23.10.2016 19:46
 */
public final class Constants {

    /**
     * Regex pattern for validation of phone numbers used in Slovak and Czech Republic
     */
    public static final String PHONE_NUMBER_REGEX_PATTERN = "^\\+?[0-9\\-\\ ]{0,}$";

    /**
     * Default {@link java.time.LocalDateTime} formatter for string representation
     */
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    private Constants() { }
}
