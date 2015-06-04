package com.example.ksg.omawash;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class WeekFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_SECTION = "sectionNumber";
    private static final String ARG_LIST   = "slotList";


    // TODO: Rename and change types of parameters
    private String mParam1 = "";
    private int sectionNumber;
    private ArrayList<ArrayList<ISlotItem>> weekList;
    private ISlotReserver slotReserver;
    private WeekGridAdapter weekAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LandscapeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekFragment newInstance(String param1, String param2
            , ArrayList<ArrayList<ISlotItem>> list)
    {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_LIST, list);

        fragment.setArguments(args);
        return fragment;
    }
    public static WeekFragment newInstance( int sectionNumber , ArrayList<ArrayList<ISlotItem>> list) {
        Log.e("WeekFragment","newInstance");
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, ""); // should not be here
        args.putInt(ARG_SECTION, sectionNumber);
        args.putSerializable(ARG_LIST, list);

        fragment.setArguments(args);
        return fragment;
    }

    public WeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weekList    = (ArrayList<ArrayList<ISlotItem>>) getArguments().getSerializable(ARG_LIST);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onBookingReceiver, new IntentFilter("OnISlotItemChanged"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bookingsReceiver, new IntentFilter("bookingsReceived"));

        Log.e("WeekFragment","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("WeekFragment","onCreateView");


        View rootView = inflater.inflate(R.layout.week_fragment, container, false);
        GridView gridView;

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        weekAdapter = new WeekGridAdapter(getActivity().getApplicationContext(),weekList);

        gridView.setAdapter(weekAdapter);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)gridView.getLayoutParams();
        linearParams.width=225*8;
        gridView.setLayoutParams(linearParams);
        gridView.setColumnWidth(225);


        // TODO: Define event then WeekCalendar is Clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int time    = position / 8 -1;
                int day     = position % 8 -1;
                ISlotItem item = (ISlotItem) adapterView.getItemAtPosition(position);
                if(item != null){
                    slotReserver.onISlotItemRequested(item, day, time);
                    Log.i("WeekCalendar","Date :" + item.getDate() + " Time :" + item.getTime());

                } else Log.i("WeekCalendar"," == null");

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
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

    private BroadcastReceiver onBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("WeekFragment","Broadcast Received");

            int dayPos = intent.getIntExtra("daypos",-1);
            int timePos = intent.getIntExtra("timepos",-1);

            if(dayPos != -1){
                try {
                    ISlotItem item = (ISlotItem) intent.getSerializableExtra("item");
                    Log.e("WeekFragment Received","dayPos: "+dayPos+" timePos: "
                            +timePos+" Room : " + item.getReserver());
                    weekAdapter.notifyDataSetChanged();
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
                weekAdapter.notifyDataSetChanged();

            } catch (ClassCastException e) {
                throw new ClassCastException("Problem with cast to ISlotItem");
            }

        }
    };

}
