package bueenterprise.calendarday;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lassebuesvendsen on 22/04/15.
 */
public class TimeSlotItem {

    private String title;
    private String description;
    Calendar slotStartTime;
    Calendar slotTimeLength;
    Calendar slotEndTime;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    int room;

    public TimeSlotItem(Calendar start, Calendar length)
    {
        this.slotStartTime = start;
        this.slotTimeLength = length;

        room = 83;

        slotEndTime = (Calendar) slotStartTime.clone();
        slotEndTime.add(Calendar.HOUR_OF_DAY,length.get(Calendar.HOUR_OF_DAY));
        slotEndTime.add(Calendar.MINUTE,length.get(Calendar.MINUTE));
    }

    public String getTime() {
        return timeFormat.format(slotStartTime.getTime());
    }

    public void setStartTime(Calendar startTime) {
        this.slotStartTime = startTime;
    }

    public String getSlotLength() {
        return timeFormat.format(slotTimeLength.getTime());
    }

    public String getEndTime() { return timeFormat.format(slotEndTime.getTime());}
    public void setSlotLength(Calendar length) {
        this.slotTimeLength = length;
    }

    // Fake class
    public int getReserver(){ return room; }

    public void setReserver(int room){ this.room = room; }
}
