package com.example.ksg.omawash;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_SECTION = "sectionNumber";
    private static final String ARG_LIST   = "slotList";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ArrayList<ArrayList<ISlotItem>> list;

    private ISlotReserver slotReserver;

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
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    public static DayFragment newInstance( int sectionNumber,  ArrayList<ArrayList<ISlotItem>> list) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, ""); // should not be here
        args.putInt(ARG_SECTION, sectionNumber);
        args.putSerializable(ARG_LIST, list);

        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            list = (ArrayList<ArrayList<ISlotItem>>) getArguments().getSerializable(ARG_LIST);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.calendar_day_view_pager, container, false);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.viewPager);

        pager.setAdapter(new CalendarDayAdapter(this, getActivity().getApplicationContext(), list));

//        ListView listView;
//        String date = list.get(0).get(0).getDateTitle();
//
//        TextView dateTv = (TextView) rootView.findViewById(R.id.dateTitle);
//        dateTv.setText(date);
//
//        listView = (ListView) rootView.findViewById(R.id.listViewDay);
//        ListViewTimeSlotsAdapter adapter = new ListViewTimeSlotsAdapter(getActivity()
//                .getApplicationContext(),list.get(0));
//
//        listView.setAdapter(adapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
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
