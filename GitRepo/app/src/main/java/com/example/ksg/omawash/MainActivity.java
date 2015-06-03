package com.example.ksg.omawash;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.facebook.FacebookSdk;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        LoginFragment.ILoginFragment, LoginFragment.IMenuBarTitle,ISlotReserver{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    private CharSequence mTitle;
    //Change if logged in or not
    private boolean loggedIn = false;


    // Enum to keep track of the device orientation + field
    public enum PhoneMode {PORTRAIT, LANDSCAPE}
    private PhoneMode phoneMode;



    // ViewPager
//    ViewPager pager;
//    DayFragmentAdapter pagerAdapter;

    // Formatter time for the timeSlots
    SimpleDateFormat formatter;

    // Interface to the timeSlotFactory
    ISlotFactory timeSlotFac;

    // List of interfaces to the timeSlots
    ArrayList<ISlotItem> slotItemList;
    ArrayList<ArrayList<ISlotItem>> weekList;
    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        LocalBroadcastManager.getInstance(this).registerReceiver(bookingsReceiver, new IntentFilter("bookingsReceived"));

        // Enable Local Datastore.
        Parse.initialize(this, getString(R.string.application_id), getString(R.string.client_key));
        ParseFacebookUtils.initialize(this);
        setContentView(R.layout.activity_main);

        // Generated
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), loggedIn);

    }

    @Override
    public void onLoginSucces() {
//        onNavigationDrawerItemSelected(0);
//        mNavigationDrawerFragment.changeLogInStatus(true);
//        createParseUser();
//        loggedIn = true;
//        mNavigationDrawerFragment.changeLogInStatus(loggedIn);
    }

    // TODO: Check what should be done, if log in is canceled, maybe change the current fragment
    @Override
    public void onLoginCancel() {


    }

    // TODO: Maybe display a dialog
    @Override
    public void onLoginError() {

    }

    @Override
    public void changeMenuBarTitle(int sectionNumber) {
        onSectionAttached(sectionNumber);
    }

    public void onButtonPressed(View view) {
        createParseUser();
 	}

    @Override
    public void onISlotItemRequested(final ISlotItem item, final int dayPos, final int timePos) {
        Log.i("SlotReserver", "Slot Time: " + item.getTime() + " Date: " + item.getDate());

        if((ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getInt("room") >0))
        {
            formatter = new SimpleDateFormat("EEEE");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TimeSlots");
            query.whereEqualTo("day",formatter.format(item.getStartCalendar().getTime()));
            query.whereEqualTo("startTime", item.getTime());

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        if (list.size() != 0) {
//                            for (ParseObject slot : list) {
//                                if (slot.get("room") == null) { // Only if room is not stated
//                                    slot.put("room", ParseUser.getCurrentUser().get("room"));
//                                    slot.put("reserver", ParseObject
//                                            .createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
//
//                                    Log.e("Parse Object", "The time:" + slot.get("startTime"));
//
//                                    slot.saveEventually(new SaveCallback() {
//                                        @Override
//                                        public void done(ParseException e) {
//                                            if (e == null) {
//                                                Log.i("Parse Object", " Saved");
//                                                Toast.makeText(getApplicationContext(), "Reservation at "
//                                                        + item.getDate() + ", " + item.getTime() + " has been saved"
//                                                        , Toast.LENGTH_LONG).show();
//                                                item.setReserver(ParseUser.getCurrentUser().getInt("room"));
////                                            weekList.get(dayPos).set(timePos, item);
//                                                broadcastChangedISlotItem(item, dayPos, timePos);
//                                            } else {
//                                                Log.e("Parse Object", "Error " + e.toString());
//                                            }
//                                        }
//                                    });
//                                } else {
                            Log.i("Parse Object", "Time taken!");
                            Toast.makeText(getApplicationContext(), "Error: Time taken! Sorry...", Toast.LENGTH_SHORT).show();
//                                }
//                            }
                        } else {
                            ParseObject newCloudBooking = new ParseObject("TimeSlots");
                            newCloudBooking.put("room",ParseUser.getCurrentUser().getInt("room"));
                            formatter = new SimpleDateFormat("EEEE");
                            newCloudBooking.put("day",formatter.format(item.getStartCalendar().getTime()));
                            newCloudBooking.put("startTime",item.getTime());
                            newCloudBooking.put("reserver", ParseObject
                                    .createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
                            newCloudBooking.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Log.i("Parse Object", " Saved");
                                        Toast.makeText(getApplicationContext(), "Reservation at "
                                                + item.getDate() + ", " + item.getTime() + " has been saved"
                                                , Toast.LENGTH_LONG).show();
                                        item.setReserver(ParseUser.getCurrentUser().getInt("room"));
//                                            weekList.get(dayPos).set(timePos, item);
                                        broadcastChangedISlotItem(item, dayPos, timePos);
                                    } else {
                                        Log.e("Parse Object", "Error " + e.toString());
                                    }
                                }
                            });
                        }
                    } else Log.e("Parse", "couldn't list timeSlots" + e.toString());
                }
            });

        } else onNavigationDrawerItemSelected(3);
    }

    public void createParseUser(){

        final List<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        permissions.add("user_friends");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                    mNavigationDrawerFragment.changeLogInStatus(false, 0);
                } else if (parseUser.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    mNavigationDrawerFragment.changeLogInStatus(true, 0);
//                    onNavigationDrawerItemSelected(0);
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    mNavigationDrawerFragment.changeLogInStatus(true, 0);
//                    onNavigationDrawerItemSelected(0);

                }
            }
        });
    }

    private void updateFragmentState(Configuration newPhoneMode)
    {
        //ArrayList<ISlotItem> itemArrayList; // param
        if (Configuration.ORIENTATION_LANDSCAPE == newPhoneMode.orientation){
            phoneMode = PhoneMode.LANDSCAPE;
        } else phoneMode = PhoneMode.PORTRAIT;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
//        if(loginFragment != null) {
//            loginFragment.onActivityResult(requestCode, resultCode, data);
//        }
//        Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
//
//        if (fragment.getTag().equals("loginfragment")){
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        Log.e("onSelect", " " +fragmentManager.getBackStackEntryCount());

        switch(position){
            case 0:
                if (phoneMode == PhoneMode.PORTRAIT){ // If portrait use DayFragment

                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, DayFragment.newInstance( position+1 ,weekList))
                            .commit();

                } else { // Else use landscape
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, WeekFragment.newInstance( position+1, weekList))
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

//                ParseUser.getCurrentUser().logOut();
                if (ParseUser.getCurrentUser() != null ) {
                    Log.i("loglog", " Log in logging out");
                    Toast.makeText(this, "Logged out", Toast.LENGTH_LONG);
                    mNavigationDrawerFragment.changeLogInStatus(false,0);
////                    mNavigationDrawerFragment.changeLogInStatus(true);
                    ParseUser.getCurrentUser().logOut();
                    Log.i("loglog", "Just logged out");

                    if( ParseUser.getCurrentUser() == null )
                    {
                        mNavigationDrawerFragment.changeLogInStatus(false,0);
                    }
                } else
                {
                    loginFragment = LoginFragment.newInstance(position +1);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, loginFragment)
                            .commit();
//                    mNavigationDrawerFragment.changeLogInStatus(false);
                    Log.i("loglog", "Logged out, about to log in");

                    createParseUser(); // Loges on if already created
                    if( ParseUser.getCurrentUser() != null )
                    {
                        Log.i("loglog", "Just logged in, about to change menu text'");
                        mNavigationDrawerFragment.changeLogInStatus(true,0);

                    } else Log.i("loglog", "didn't change menu text");

                }
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

        slotItemList    = timeSlotFac.getTimeSlotItemList((Calendar) start.clone(), 24, (Calendar) length.clone());
        weekList        = timeSlotFac.getWeekList((Calendar) start.clone(), 24, (Calendar) length.clone());
        getBookingsInCloud();

//        ISlotItem item;
//        String weekday = "EEEE";
//        String hourOfDay = "HH:mm";
//        String day;
//        String hour;
//
//        for (int i=0; i < 7; i++){
//            for (int k = 0; k < 24; k++){
//                item = weekList.get(i).get(k);
//                formatter = new SimpleDateFormat(weekday);
//                day = formatter.format(item.getStartCalendar().getTime());
//                formatter = new SimpleDateFormat(hourOfDay);
//                hour = formatter.format(item.getStartCalendar().getTime());
//
//                ParseObject bookings = new ParseObject("Bookings");
//                bookings.put("Weekday", day);
//                bookings.put("StartTime", hour);
//                bookings.saveInBackground();
//                try {
//                    wait(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Log.e("Fejl","fejl");
//                }
//
//
//            }
//        }

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
                if (loggedIn)
                {
                    mTitle = getString(R.string.title_section5);

                } else mTitle = getString(R.string.title_section4);

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

    private void getBookingsInCloud()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TimeSlots");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                Calendar tmpDay = Calendar.getInstance();
                int daysFromToday = 0;
                formatter = new SimpleDateFormat(("EEEE"));
                ArrayList<ArrayList<ISlotItem>> tmpWeekList = weekList;
                for (ParseObject booking : list) {
                    // Check how many days current is from today
                    String cloudDay = booking.getString("day");
                    for (int i = 0; i < 7; i++) {
                        tmpDay.add(Calendar.HOUR_OF_DAY, i * 24);

                        if (cloudDay.equals(formatter.format(tmpDay.getTime()))) {
                            daysFromToday = i;
                            tmpDay = Calendar.getInstance();
                            break;
                        }
                        tmpDay = Calendar.getInstance();
                    }
                    String timeItem = "";
                    String timeBooking = "";
                    for (ISlotItem item : tmpWeekList.get(daysFromToday)) {
                        if (item.getTime().equals(booking.getString("startTime"))) {
                            timeItem = item.getTime();
                            timeBooking = booking.getString("startTime");
                            item.setReserver(booking.getInt("room"));
                            tmpWeekList.get(daysFromToday)
                                    .set(tmpWeekList.get(daysFromToday).indexOf(item), item);
                            break;
                        }
                    }
                }
                broadcastBookings(tmpWeekList);
            }
        });
    }

    private void broadcastChangedISlotItem(ISlotItem item, int dayPos, int timePos)
    {
        Intent intent = new Intent("OnISlotItemChanged");
        intent.putExtra("item", (Serializable) item);
        intent.putExtra("daypos",dayPos);
        intent.putExtra("timepos",timePos);
        // Sends the Intent with the LocalBroadcastManager
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
    private void broadcastBookings(ArrayList<ArrayList<ISlotItem>> list)
    {
        Intent intent = new Intent("bookingsReceived");
        intent.putExtra("list", (Serializable) list);;
        // Sends the Intent with the LocalBroadcastManager
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


    private BroadcastReceiver bookingsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("Main", "bookings Received");

            try {
                Log.i("Main receive B", "Try cast");

                ArrayList<ArrayList<ISlotItem>> items = (ArrayList<ArrayList<ISlotItem>>) intent.getSerializableExtra("list");
                weekList = items;
            } catch (ClassCastException e) {
                throw new ClassCastException("Problem with cast to ISlotItem");
            }

        }
    };

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
