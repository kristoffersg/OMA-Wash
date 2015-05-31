package com.example.ksg.omawash;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lassebuesvendsen on 28/05/15.
 */
public class TimeSlotFactory implements ISlotFactory {

    String dateFormat;


    public TimeSlotFactory(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    /*
        The slots should at least be 10 min
    */

    public ArrayList<ArrayList<ISlotItem>> getWeekList(Calendar start, int Amount, Calendar length)
    {
        Calendar newStart;
        Calendar newLengt;

        ArrayList<ArrayList<ISlotItem>> weekList = new ArrayList<>();
        for (int i = 0; i < 7; i++ )
        {
            newStart = (Calendar)start.clone();
            newLengt = (Calendar)length.clone();

            newStart.add(Calendar.HOUR_OF_DAY,24*i);
            newLengt.add(Calendar.HOUR_OF_DAY, 24 * i);

            weekList.add(getTimeSlotItemList(newStart,Amount,newLengt));
        }
        return weekList;
    }

    @Override
    public ArrayList<ISlotItem> getTimeSlotItemList(Calendar start, int Amount, Calendar length) {

        Calendar endTime;
        Calendar startTime = start;
        Calendar slotTimeLength = length;
        int slotAmount = Amount;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");


        ArrayList<ISlotItem> list = new ArrayList<>();

        Calendar maxStartTime = Calendar.getInstance();
        maxStartTime.set(Calendar.HOUR_OF_DAY,23);
        maxStartTime.set(Calendar.MINUTE,50);


        if (startTime.get(Calendar.HOUR_OF_DAY)<23 && startTime.get(Calendar.MINUTE)<50)
        {

            int hours  = slotAmount * slotTimeLength.get(Calendar.HOUR_OF_DAY);
            int minutes    = slotAmount * slotTimeLength.get(Calendar.MINUTE);
            hours = hours + (minutes / 60) + startTime.get(Calendar.HOUR_OF_DAY);
            minutes = (minutes % 60) + startTime.get(Calendar.MINUTE);

            Log.e("Total time", Integer.toString(hours) + ":" + Integer.toString(minutes));
            if(hours <= 24 && minutes <= 0)
            {
                endTime = Calendar.getInstance();
                endTime.set(startTime.get(Calendar.YEAR),
                        startTime.get(Calendar.MONTH),
                        startTime.get(Calendar.DAY_OF_MONTH),
                        hours,
                        minutes);

                ISlotItem item;
                for (int i = 0; i < slotAmount; i++)
                {
                    Calendar newSlot = (Calendar)startTime.clone();
                    int addHours = i * slotTimeLength.get(Calendar.HOUR_OF_DAY);
                    int addMinutes = i * slotTimeLength.get(Calendar.MINUTE);
                    newSlot.add(Calendar.HOUR_OF_DAY,addHours);
                    newSlot.add(Calendar.MINUTE, addMinutes);

                    Log.i("startCalendar", formatter.format(newSlot.getTime()));

                    item =  new TimeSlotItem(dateFormat,newSlot,slotTimeLength);
                    list.add(item);
                }
                return list;

            } else
            {
                // Failed
                Log.e("", "");
                return null;
            }
        }
        else
        {
            // Failed
            Log.e("","");
            return null;
        }

    }


}
