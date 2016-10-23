package cz.muni.fi.pa165.utils;

/**
 * Class for storing constant values used throughout the application
 *
 * @author Martin Vrábel
 * @version 23.10.2016 19:46
 */
public final class Constants {

    /**
     * Regex pattern for validation of email addresses compliant with RFC822
     */
    public static final String EMAIL_REGEX_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
            "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f]" +
            ")*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9" +
            "]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\" +
            "x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    /**
     * Regex pattern for validation of phone numbers used in Slovak and Czech Republic
     */
    public static final String PHONE_NUMBER_REGEX_PATTERN = "^\\+?[0-9\\-\\ ]+$";


    private Constants() { }
}
