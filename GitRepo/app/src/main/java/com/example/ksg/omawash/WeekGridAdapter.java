package com.example.ksg.omawash;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lassebuesvendsen on 22/04/15.
 *
 */
public class WeekGridAdapter extends BaseAdapter {

    Context context;
    TimeSlotItem slotTime;
    ArrayList<ISlotItem> list;
    int count = 25*8;

    public WeekGridAdapter(Context c, ArrayList<ISlotItem> list){
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
        {
            return list.get(position);
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
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.week_slot,null);
        }

        TextView timeDateRoom = (TextView) view.findViewById(R.id.timeDateRoom);
        TextView info = (TextView) view.findViewById(R.id.info);

        int offSetRow = row-1;

        if (row == 0)
        {


            view.setBackground(context.getResources().getDrawable(R.drawable.line_bottom_thick));
            switch (column)
            {

                case 0 :
                    timeDateRoom.setText("");
                    info.setText("");
                    view.setBackground(context.getResources().getDrawable(R.drawable.line_bottom_right_thick));

                    break;
                case 1 :
                    timeDateRoom.setText(context.getString(R.string.mon));
                    info.setText(list.get(row).getDate());
                    break;
                case 2:
                    timeDateRoom.setText(context.getString(R.string.tue));
                    info.setText(list.get(row).getDate());
                    break;
                case 3:
                    timeDateRoom.setText(context.getString(R.string.wed));
                    info.setText(list.get(row).getDate());

                    break;
                case 4:
                    timeDateRoom.setText(context.getString(R.string.thu));
                    info.setText(list.get(row).getDate());

                    break;
                case 5:
                    timeDateRoom.setText(context.getString(R.string.fri));
                    info.setText(list.get(row).getDate());

                    break;
                case 6:
                    timeDateRoom.setText(context.getString(R.string.sat));
                    info.setText(list.get(row).getDate());

                    break;
                case 7:
                    timeDateRoom.setText(context.getString(R.string.sun));
                    info.setText(list.get(row).getDate());

                    break;
            }


//            slotTime.setText("");//list.get(position).getTime());
//            slotEndTime.setText("");
//            reserver.setText("");
//
//            view.setBackground(context.getResources().getDrawable(R.drawable.line_right_thick));
            //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_right_thick));
        }
        else if(column == 0 )
        {
            timeDateRoom.setText(list.get(offSetRow).getTime());//list.get(position).getTime());
            info.setText("Ends " + list.get(offSetRow).getEndTime());
            timeDateRoom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            view.setBackground(context.getResources().getDrawable(R.drawable.line_right_thick));
            //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_right_thick));

        } else
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.line_right));

            info.setText("");//list.get(position).getTime());
            timeDateRoom.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            if (list.get(offSetRow).getReserver() <= 0)
            {
                //Log.i("Reserved check","Not reserved, room " + Integer.toString(list.get(position).getReserver()));
                timeDateRoom.setText(context.getString(R.string.notReserved));
            } else{
                //Log.i("Reserved check","Reserved, room " + Integer.toString(list.get(position).getReserver()) + "Time " + list.get(position).getTime() );
                timeDateRoom.setText(context.getString(R.string.reserved) + " " + list.get(offSetRow).getReserver());
            }

        }



        return view;
    }
}
