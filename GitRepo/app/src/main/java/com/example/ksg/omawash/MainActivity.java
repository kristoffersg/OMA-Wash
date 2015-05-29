package com.example.ksg.omawash;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    // Formatter time for the timeSlots
    SimpleDateFormat formatter;

    // Interface to the timeSlotFactory
    ISlotFactory timeSlotFac;

    // List of interfaces to the timeSlots
    ArrayList<ISlotItem> slotItemList;

    // Enum to keep track of the device orientation + field
    public enum PhoneMode {PORTRAIT, LANDSCAPE}
    private PhoneMode phoneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Generated
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    private void updateFragmentState(Configuration newPhoneMode)
    {
        //ArrayList<ISlotItem> itemArrayList; // param
        if (Configuration.ORIENTATION_LANDSCAPE == newPhoneMode.orientation){
            phoneMode = PhoneMode.LANDSCAPE;
        } else phoneMode = PhoneMode.PORTRAIT;

//        if (phoneMode == PhoneMode.PORTRAIT){ // If portrait use DayFragment
//
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container, DayFragment.newInstance(itemArrayList))
//                    .commit();
//
//        } else { // Else use landscape
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container, WeekFragment.newInstance(itemArrayList))
//                    .commit();
//        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch(position){
            case 0:
                if (phoneMode == PhoneMode.PORTRAIT){ // If portrait use DayFragment

                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, DayFragment.newInstance(slotItemList))
                            .commit();

                } else { // Else use landscape
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, WeekFragment.newInstance(slotItemList))
                            .commit();
                }
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, LoginFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    @Override
    public void initNavigationItems() {
        // Initiation
        timeSlotFac = new TimeSlotFactory(getString(R.string.dateFormat));
        formatter = new SimpleDateFormat(getString(R.string.timeFormat));

        // Setup then then the booking starts and ends during a day
        Calendar length = Calendar.getInstance();
        length.set(Calendar.HOUR_OF_DAY, 1);
        length.set(Calendar.MINUTE, 00);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 00);

        Log.i("startCalendar", formatter.format(start.getTime()));
        Log.i("lengthCalendar", formatter.format(length.getTime()));

        updateFragmentState(getResources().getConfiguration());

        slotItemList = timeSlotFac.getTimeSlotItemList((Calendar)start.clone(),24,(Calendar)length.clone());


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

        int number;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(getArguments() != null)
            {
                number = getArguments().getInt(ARG_SECTION_NUMBER);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView tv = (TextView) rootView.findViewById(R.id.section_label);
            tv.setText(tv.getText()+" "+number);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
