package com.example.ksg.omawash;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private ArrayList<ArrayList<ISlotItem>> weekList;
    private ArrayList<ListViewTimeSlotsAdapter> listViewAdapters;
    private Context context;
//    LinearLayout view;
    View view;
    LayoutInflater inflater;
    IDayViewPagerContainer dayViewPagerContainer;

    public CalendarDayAdapter( IDayViewPagerContainer dayViewPagerContainer , Context c, ArrayList<ArrayList<ISlotItem>> weekList){
        this.weekList = weekList;
        this.context = c;
        this.dayViewPagerContainer = dayViewPagerContainer;
        this.listViewAdapters = new ArrayList<>();
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return daysInCalendar;
    }


    public void updateCalendarView(ArrayList<ArrayList<ISlotItem>> list,int dayPos) {
        this.weekList = list;
        listViewAdapters.get(dayPos).notifyDataSetChanged();
//        this.notifyDataSetChanged();
    }

    public void setWeekList(ArrayList<ArrayList<ISlotItem>> weekList) {
        this.weekList = weekList;
        for( ListViewTimeSlotsAdapter abe : listViewAdapters )
        {
            abe.notifyDataSetChanged();
        }

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ( object );
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

//        if(view == null)
//        {
////            view = (LinearLayout) inflater.inflate(R.layout.fragment_day,null);
//            view = inflater.inflate(R.layout.fragment_day, container, false);
//        }
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        TextView dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        ListView listView = (ListView) view.findViewById(R.id.listViewDay);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: Pass the item on

                ISlotItem item = (ISlotItem) adapterView.getItemAtPosition(i);
                Log.i("ListViewAdapter", "Date : " + item.getDate() + " Time: " + item.getTime());
                dayViewPagerContainer.onDayCalendarItemClicked(item, position, i);
            }
        });

        dateTitle.setText(weekList.get(position).get(0).getDateTitle());
        dateTitle.setTextColor(context.getResources().getColor(R.color.primary_dark_material_light));

        listViewAdapters.add(new ListViewTimeSlotsAdapter(context, weekList.get(position)));
        listView.setAdapter(listViewAdapters.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    public interface IDayViewPagerContainer{
        void onDayCalendarItemClicked( ISlotItem item, int dayPos, int timePos );
    }

}
