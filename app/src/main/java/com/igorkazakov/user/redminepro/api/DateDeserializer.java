package com.igorkazakov.user.redminepro.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateDeserializer implements JsonDeserializer<Date> {

    private ThreadLocal<DateFormat> mDateFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private ThreadLocal<DateFormat> mDateTimeFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        }
    };

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        synchronized (this) {

            String element = json.getAsJsonPrimitive().getAsString();
            Date date = null;

            try {
                date = mDateFormat.get().parse(element);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        try {
            date = mDateTimeFormat.get().parse(element);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            return date;
        }
    }

}
