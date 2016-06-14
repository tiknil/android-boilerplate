package com.tiknil.boilerplate.model.webservices.webservices_utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Classe che gestisce la traduzione delle stringhe in date e viceversa nel decoding tramite JSON
 *
 * @TiKnil
 */
public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private static final String TAG = DateAdapter.class.getSimpleName();

    private DateFormat dateFormat = null;

    public DateAdapter(String dateFormat, Locale locale, TimeZone timeZone) {
        this.dateFormat = new SimpleDateFormat(dateFormat, locale);
        this.dateFormat.setTimeZone(timeZone);
    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type,
                                              JsonSerializationContext jsonSerializationContext) {
        synchronized (dateFormat) {
            String dateFormatAsString = dateFormat.format(date);
            return new JsonPrimitive(dateFormatAsString);
        }
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                         JsonDeserializationContext jsonDeserializationContext) {
        try {
            synchronized (dateFormat) {
                return dateFormat.parse(jsonElement.getAsString());
            }
        } catch (ParseException e) {
            throw new JsonSyntaxException(jsonElement.getAsString(), e);
        }
    }
}
