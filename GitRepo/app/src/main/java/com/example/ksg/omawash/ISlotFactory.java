package com.example.ksg.omawash;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lassebuesvendsen on 29/05/15.
 */
public interface ISlotFactory {
    ArrayList<ArrayList<ISlotItem>> getWeekList(Calendar start, int Amount, Calendar length);
    ArrayList<ISlotItem> getTimeSlotItemList(Calendar start, int Amount, Calendar length);
}
