package bueenterprise.calendarday;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lassebuesvendsen on 22/04/15.
 */
public class TimeSlotItem implements ISlotItem {

    String strDateFormat;
    Calendar slotStartTime;
    Calendar slotTimeLength;
    Calendar slotEndTime;

    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;


    int room;

    public TimeSlotItem(String dateFormat, Calendar start, Calendar length)
    {
        this.slotStartTime = start;
        this.slotTimeLength = length;
        this.strDateFormat = dateFormat;
        this.timeFormat = new SimpleDateFormat("HH:mm");
        this.dateFormat = new SimpleDateFormat(dateFormat);

        room = -1;

        slotEndTime = (Calendar) slotStartTime.clone();
        slotEndTime.add(Calendar.HOUR_OF_DAY,length.get(Calendar.HOUR_OF_DAY));
        slotEndTime.add(Calendar.MINUTE,length.get(Calendar.MINUTE));
    }

    @Override
    public String getTime() {
        return timeFormat.format(slotStartTime.getTime());
    }

    @Override
    public void setStartTime(Calendar startTime) {
        this.slotStartTime = startTime;
    }

    @Override
    public String getSlotLength() {
        return timeFormat.format(slotTimeLength.getTime());
    }

    @Override
    public String getEndTime() { return timeFormat.format(slotEndTime.getTime());}
    @Override
    public void setSlotLength(Calendar length) {
        this.slotTimeLength = length;
    }

    @Override
    public String getDate(){return dateFormat.format(slotStartTime.getTime());}

    // Fake class
    @Override
    public int getReserver(){ return room; }

    @Override
    public void setReserver(int room){ this.room = room; }


    @Override
    public void setDateFormat(String dateFormat){
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }
}
