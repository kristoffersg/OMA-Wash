package com.example.ksg.omawash;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by lassebuesvendsen on 22/04/15.
 *
 */
public class WeekGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<ArrayList<ISlotItem>> list;
    int count = 25*8;

    public WeekGridAdapter(Context c, ArrayList<ArrayList<ISlotItem>> list){
        this.context = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        if(list != null)
        {   int slot     = position / 8 -1;
            int day  = position % 8 -1;
            if( slot < 0 || day < 0 )
            {
                return null;
            } else {
                return list.get(day).get(slot);
            }
        }
        else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Calculate position for grid with 7 columns
        int row     = position / 8;
        int column  = position % 8;

        View view = convertView;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this
                    .context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.week_slot,null);
        }

        TextView timeDateRoom = (TextView) view.findViewById(R.id.timeDateRoom);
        TextView info = (TextView) view.findViewById(R.id.info);

        int offSetRow = row-1;

        if (row == 0)
        {

            if ( column != 0)
            {
                timeDateRoom.setText(list.get(column-1).get(row).getWeekDay());
                info.setText(list.get(column-1).get(row).getDateTitle());
                view.setBackground(context.getResources().getDrawable(R.drawable.line_bottom_thick));
                timeDateRoom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            } else {

                timeDateRoom.setText("");
                info.setText("");
                view.setBackground(context.getResources().getDrawable(R.drawable.line_bottom_right_thick));

            }
        }
        else if( column == 0 )
        {

            timeDateRoom.setText(list.get(column).get(offSetRow).getTime());
            info.setText("Ends " + list.get(column).get(offSetRow).getEndTime());
            timeDateRoom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            view.setBackground(context.getResources().getDrawable(R.drawable.line_right_thick));


        } else
        {
            int offSetColumn = column-1;
            view.setBackground(context.getResources().getDrawable(R.drawable.line_right));

            info.setText("");
            timeDateRoom.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            if (list.get(offSetColumn).get(offSetRow).getReserver() <= 0)
            {
                timeDateRoom.setText(context.getString(R.string.notReserved));
            } else{
                timeDateRoom.setText(context.getString(R.string.reserved) + " " + list.get(offSetColumn).get(offSetRow).getReserver());
            }

        }



        return view;
    }
}
