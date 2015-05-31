package com.example.ksg.omawash;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lassebuesvendsen on 31/05/15.
 */
public class CalendarDayAdapter extends PagerAdapter {

    int daysInCalendar = 7;
    LinearLayout dayView = null;
    private ArrayList<ArrayList<ISlotItem>> weekList;
    private Context context;
//    LinearLayout view;
    View view;
    LayoutInflater inflater;

    public CalendarDayAdapter( Context c, ArrayList<ArrayList<ISlotItem>> weekList){
        this.weekList = weekList;
        this.context = c;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return daysInCalendar;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ( (LinearLayout) object );
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        if(view == null)
//        {
////            view = (LinearLayout) inflater.inflate(R.layout.fragment_day,null);
//            view = inflater.inflate(R.layout.fragment_day, container, false);
//        }
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        TextView dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        ListView listView = (ListView) view.findViewById(R.id.listViewDay);

//
//        LinearLayout layout = new LinearLayout(context);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);


        dateTitle.setText(weekList.get(position).get(0).getDateTitle());
        dateTitle.setTextColor(context.getResources().getColor(R.color.primary_dark_material_light));
        listView.setAdapter(new ListViewTimeSlotsAdapter(context, weekList.get(position)));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}