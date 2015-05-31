package com.example.ksg.omawash;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
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

    public CalendarDayAdapter( Context c, ArrayList<ArrayList<ISlotItem>> weekList){
        this.weekList = weekList;
        this.context = c;
    }


    @Override
    public int getCount() {
        return daysInCalendar;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LinearLayout view = (LinearLayout) container.findViewById(R.id.calenderDay);
        TextView dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        ListView listView = (ListView) view.findViewById(R.id.listViewDay);


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);


        dateTitle.setText(weekList.get(position).get(0).getDateTitle());
        listView.setAdapter(new ListViewTimeSlotsAdapter(context, weekList.get(position)));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}


//
//int[] res = {
//        android.R.drawable.ic_dialog_alert,
//        android.R.drawable.ic_menu_camera,
//        android.R.drawable.ic_menu_compass,
//        android.R.drawable.ic_menu_directions,
//        android.R.drawable.ic_menu_gallery};
//int[] backgroundcolor = {
//        0xFF101010,
//        0xFF202020,
//        0xFF303030,
//        0xFF404040,
//        0xFF505050};
//
//
//
//
//        TextView textView = new TextView(MainActivity.this);
//        textView.setTextColor(Color.WHITE);
//        textView.setTextSize(30);
//        textView.setTypeface(Typeface.DEFAULT_BOLD);
//        textView.setText(String.valueOf(position));
//
//        ImageView imageView = new ImageView(MainActivity.this);
//        imageView.setImageResource(res[position]);
//        LayoutParams imageParams = new LayoutParams(
//                LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(imageParams);
//
//        LinearLayout layout = new LinearLayout(MainActivity.this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        LayoutParams layoutParams = new LayoutParams(
//                LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//        layout.setBackgroundColor(backgroundcolor[position]);
//        layout.setLayoutParams(layoutParams);
//        layout.addView(textView);
//        layout.addView(imageView);
//
//        final int page = position;
//        layout.setOnClickListener(new OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,
//                        "Page " + page + " clicked",
//                        Toast.LENGTH_LONG).show();
//            }});

//        container.addView(layout)