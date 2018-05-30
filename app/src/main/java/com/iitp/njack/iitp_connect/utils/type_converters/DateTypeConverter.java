package com.iitp.njack.iitp_connect.utils.type_converters;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.LocalDateTime;

public class DateTypeConverter {
    @TypeConverter
    public static LocalDateTime toDate(Long timestamp) {
        //TODO: DO ACTUAL TYPE CONVERSIONs
        return LocalDateTime.now();
    }

    @TypeConverter
    public static Long toTimestamp(LocalDateTime date) {
        return 1L;
    }
}
