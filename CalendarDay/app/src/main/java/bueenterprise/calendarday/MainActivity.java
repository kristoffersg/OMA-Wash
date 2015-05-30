package bueenterprise.calendarday;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    SimpleDateFormat formatter;
    ISlotFactory timeSlotFac;
    ArrayList<ISlotItem> slotItemList;
    public enum PhoneMode {PORTRAIT, LANDSCAPE}
    private PhoneMode phoneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        slotItemList = timeSlotFac.getTimeSlotItemList((Calendar)start.clone(),24,(Calendar)length.clone());

        updateFragmentState(slotItemList, getResources().getConfiguration());

        //            GridView gridView;

//            gridView = (GridView) findViewById(R.id.gridView);
//            WeekGridAdapter adapter = new WeekGridAdapter(this.getApplicationContext(),itemList);
//            gridView.setAdapter(adapter);
//            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)gridView.getLayoutParams();
//            linearParams.width=225*8;
//            gridView.setLayoutParams(linearParams);
//            gridView.setColumnWidth(225);

//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearGridContainer);
//            linearLayout.getWidth();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateFragmentState(slotItemList, newConfig);

    }

    private void updateFragmentState(ArrayList<ISlotItem> itemArrayList, Configuration newPhoneMode)
    {
        if (Configuration.ORIENTATION_LANDSCAPE == newPhoneMode.orientation){
            phoneMode = PhoneMode.LANDSCAPE;
        } else phoneMode = PhoneMode.PORTRAIT;

        if (phoneMode == PhoneMode.PORTRAIT){ // If portrait use DayFragment

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, DayFragment.newInstance(itemArrayList))
                    .commit();

        } else { // Else use landscape
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, WeekFragment.newInstance(itemArrayList))
                    .commit();
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


}
