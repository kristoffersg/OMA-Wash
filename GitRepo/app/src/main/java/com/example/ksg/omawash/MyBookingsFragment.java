package com.example.ksg.omawash;

import android.app.Activity;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MyBookingsFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_POS = "position";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int position;
    private SimpleDateFormat formatter;

    private OnFragmentInteractionListener mListener;
    private ArrayList<ArrayList<ISlotItem>> weekList;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private BookingAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static MyBookingsFragment newInstance(String param1, String param2) {
        MyBookingsFragment fragment = new MyBookingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static MyBookingsFragment newInstance( int pos, ArrayList<ArrayList<ISlotItem>> list) {
        MyBookingsFragment fragment = new MyBookingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "");
        args.putString(ARG_PARAM2, "");
        args.putSerializable("list",list);
        args.putInt(ARG_POS, pos);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyBookingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            position = getArguments().getInt(ARG_POS);
            weekList = (ArrayList<ArrayList<ISlotItem>>) getArguments().getSerializable("list");
        }

        // TODO: Change Adapter to display your content
        mAdapter = new BookingAdapter(getActivity(), new ArrayList<ISlotItem>());
        getBookings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybookingsfragment, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);


        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LoginFragment.IMenuBarTitle) activity)
                .changeMenuBarTitle(getArguments().getInt(ARG_POS));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }

    private void getBookings(){
        ParseQuery<ParseObject> query = new ParseQuery<>("TimeSlots");
        ParseObject userPointer = ParseObject
                .createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo("reserver", userPointer);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Calendar tmpDay = Calendar.getInstance();
                int daysFromToday = 0;
                formatter = new SimpleDateFormat(("EEEE"));

                ArrayList<ArrayList<ISlotItem>> tmpWeekList = weekList;
                ArrayList<ISlotItem> tmpBookings = new ArrayList<>();
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

                    for (ISlotItem item : tmpWeekList.get(daysFromToday)) {
                        if (item.getTime().equals(booking.getString("startTime"))) {
                            item.setReserver(booking.getInt("room"));
                            tmpBookings.add(item);
                            break;
                        }
                    }
                }
                updateLisView(tmpBookings);
            }
        });
    }

    private void updateLisView(ArrayList<ISlotItem> list)
    {
        mAdapter.setBookingList(list);
        mAdapter.notifyDataSetChanged();
    }


}
