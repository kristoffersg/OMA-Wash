package com.example.ksg.omawash;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class DayFragment extends Fragment implements CalendarDayAdapter.IDayViewPagerContainer{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_SECTION = "sectionNumber";
    private static final String ARG_LIST   = "slotList";

    private String mParam1;
    private ArrayList<ArrayList<ISlotItem>> weekList;

    private ISlotReserver slotReserver;
    private CalendarDayAdapter pagerAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayFragment newInstance(String param1, String param2, ArrayList<ISlotItem> list) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    public static DayFragment newInstance( int sectionNumber,  ArrayList<ArrayList<ISlotItem>> list) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION, sectionNumber);
        args.putSerializable(ARG_LIST, list);

        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            weekList = (ArrayList<ArrayList<ISlotItem>>) getArguments().getSerializable(ARG_LIST);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onBookingChangedReceiver, new IntentFilter("OnISlotItemChanged"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bookingsReceiver, new IntentFilter("bookingsReceived"));

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.calendar_day_view_pager, container, false);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.viewPager);

        pagerAdapter = new CalendarDayAdapter(this, getActivity().getApplicationContext(), weekList);
        pager.setAdapter(pagerAdapter);

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
//            mListener = (OnFragmentInteractionListener) activity;
            ((LoginFragment.IMenuBarTitle) activity)
                    .changeMenuBarTitle(getArguments().getInt(ARG_SECTION));
            slotReserver = (ISlotReserver) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        slotReserver = null;
    }

    @Override
    public void onDayCalendarItemClicked(ISlotItem item, int dayPos, int timePos) {
        slotReserver.onISlotItemRequested(item, dayPos, timePos);
    }

    private BroadcastReceiver onBookingChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("DayFragment","Broadcast Received");

            int dayPos = intent.getIntExtra("daypos",-1);
            int timePos = intent.getIntExtra("timepos",-1);

            if(dayPos != -1){
                try {
                    ISlotItem item = (ISlotItem) intent.getSerializableExtra("item");
                    Log.e("DayFragment Received", "dayPos: " + dayPos + " timePos: "
                            + timePos + " Room : " + item.getReserver());
                    weekList.get(dayPos).set(timePos, item);
                    pagerAdapter.updateCalendarView(weekList,dayPos);

                } catch (ClassCastException e) {
                    throw new ClassCastException("Problem with cast to ISlotItem");
                }

            }
        }
    };

    private BroadcastReceiver bookingsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("DayFragment", "bookings Received");

            try {
                Log.i("DayFragment receive B", "Try cast");

                ArrayList<ArrayList<ISlotItem>> items = (ArrayList<ArrayList<ISlotItem>>) intent.getSerializableExtra("list");
                weekList = items;
                pagerAdapter.setWeekList(weekList);

            } catch (ClassCastException e) {
                throw new ClassCastException("Problem with cast to ISlotItem");
            }

        }
    };

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
