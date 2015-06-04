package com.example.ksg.omawash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lassebuesvendsen on 04/06/15.
 */
public class BookingAdapter extends BaseAdapter {



    ArrayList<ISlotItem> bookingList;
    Context context;

    public BookingAdapter(Context c ,ArrayList<ISlotItem> bookingList)
    {
        context = c;
        this.bookingList = bookingList;

    }

    public void setBookingList(ArrayList<ISlotItem> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public int getCount() {
        return bookingList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_booking_item,null);
        }

        TextView day = (TextView) view.findViewById(R.id.bookingDay);
        TextView time = (TextView) view.findViewById(R.id.bookTime);
        TextView date = (TextView) view.findViewById(R.id.bookingDate);

        day.setText(bookingList.get(i).getWeekDay());
        time.setText(bookingList.get(i).getTime());
        date.setText(bookingList.get(i).getDate());

        return view;
    }
}
