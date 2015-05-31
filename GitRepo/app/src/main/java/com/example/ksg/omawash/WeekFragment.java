package com.example.ksg.omawash;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_SECTION = "sectionNumber";
    private static final String ARG_LIST   = "slotList";


    // TODO: Rename and change types of parameters
    private String mParam1 = "";
    private int sectionNumber;
    private ArrayList<ArrayList<ISlotItem>> list;

    private OnFragmentInteractionListener mListener;

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
            list    = (ArrayList<ArrayList<ISlotItem>>) getArguments().getSerializable(ARG_LIST);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.week_fragment, container, false);
        GridView gridView;

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        WeekGridAdapter adapter = new WeekGridAdapter(getActivity().getApplicationContext(),list);
        gridView.setAdapter(adapter);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)gridView.getLayoutParams();
        linearParams.width=225*8;
        gridView.setLayoutParams(linearParams);
        gridView.setColumnWidth(225);


        // TODO: Define event then WeekCalendar is Clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ISlotItem item = (ISlotItem) adapterView.getItemAtPosition(position);
                if(item != null)
                    Log.i("WeekCalendar","Date :" + item.getDate() + " Time :" + item.getTime());
                else Log.i("WeekCalendar"," == null");

            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((LoginFragment.IMenuBarTitle) activity)
                    .changeMenuBarTitle(getArguments().getInt(ARG_SECTION));
//            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
