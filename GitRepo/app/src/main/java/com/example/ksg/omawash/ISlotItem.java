package com.example.ksg.omawash;

import java.lang.String;import java.util.Calendar;

/**
 * Created by lassebuesvendsen on 29/05/15.
 */
public interface ISlotItem {
    String getTime();

    void setStartTime(Calendar startTime);

    String getSlotLength();

    String getEndTime();

    void setSlotLength(Calendar length);

    String getDate();

    // Fake class
    int getReserver();

    void setReserver(int room);

    void setDateFormat(String dateFormat);
}
