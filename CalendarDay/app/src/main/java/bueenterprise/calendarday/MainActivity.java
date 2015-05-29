package bueenterprise.calendarday;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    GridView gridView;
    SimpleDateFormat formatter;
    ISlotFactory timeSlotFac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ISlotItem> itemList;
        timeSlotFac = new TimeSlotFactory(getString(R.string.dateFormat));
        formatter = new SimpleDateFormat("HH:mm");

        // Setup then then the booking starts and ends during a day
        Calendar length = Calendar.getInstance();
        length.set(Calendar.HOUR_OF_DAY, 1);
        length.set(Calendar.MINUTE, 00);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 00);

        Log.i("startCalendar", formatter.format(start.getTime()));
        Log.i("lengthCalendar", formatter.format(length.getTime()));

        // Get the configuration
        itemList = timeSlotFac.getTimeSlotItemList((Calendar)start.clone(),24,(Calendar)length.clone());


        Configuration configInfo = getResources().getConfiguration();

        if (configInfo.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            gridView = (GridView) findViewById(R.id.gridView);
            WeekGridAdapter adapter = new WeekGridAdapter(this.getApplicationContext(),itemList);
            gridView.setAdapter(adapter);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)gridView.getLayoutParams();
            linearParams.width=225*8;
            gridView.setLayoutParams(linearParams);
            gridView.setColumnWidth(225);

//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearGridContainer);
//            linearLayout.getWidth();

        } else if (configInfo.orientation == Configuration.ORIENTATION_PORTRAIT)
        {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        The slots should at least be 10 min
    */
    public ArrayList<TimeSlotItem> getTimeSlotItemList(Calendar start, int Amount, Calendar length) {

        Calendar endTime = Calendar.getInstance();
        Calendar startTime = start;
        Calendar slotTimeLength = length;
        int slotAmount = Amount;


        ArrayList<TimeSlotItem> list = new ArrayList<TimeSlotItem>();

        Calendar maxStartTime = Calendar.getInstance();
        maxStartTime.set(Calendar.HOUR_OF_DAY,23);
        maxStartTime.set(Calendar.MINUTE,50);


        if (startTime.get(Calendar.HOUR_OF_DAY)<23 && startTime.get(Calendar.MINUTE)<50)
        {


            int hours  = slotAmount * slotTimeLength.get(Calendar.HOUR_OF_DAY);
            int minutes    = slotAmount * slotTimeLength.get(Calendar.MINUTE);
            hours = hours + (minutes / 60) + startTime.get(Calendar.HOUR_OF_DAY);
            minutes = (minutes % 60) + startTime.get(Calendar.MINUTE);

            Log.e("Total time",Integer.toString(hours) + ":" + Integer.toString(minutes));
            if(hours <= 24 && minutes <= 0)
            {
                endTime = Calendar.getInstance();
                endTime.set(startTime.get(Calendar.YEAR),
                        startTime.get(Calendar.MONTH),
                        startTime.get(Calendar.DAY_OF_MONTH),
                        hours,
                        minutes);

                TimeSlotItem item;
                for (int i = 0; i < slotAmount; i++)
                {
                    Calendar newSlot = (Calendar)startTime.clone();
                    int addHours = i * slotTimeLength.get(Calendar.HOUR_OF_DAY);
                    int addMinutes = i * slotTimeLength.get(Calendar.MINUTE);
                    newSlot.add(Calendar.HOUR_OF_DAY,addHours);
                    newSlot.add(Calendar.MINUTE,addMinutes);

                    Log.i("startCalendar", formatter.format(newSlot.getTime()));

                    item = new TimeSlotItem(getString(R.string.dateFormat),newSlot,slotTimeLength);
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
