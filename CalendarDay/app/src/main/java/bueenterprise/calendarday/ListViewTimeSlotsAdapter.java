package bueenterprise.calendarday;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by lassebuesvendsen on 22/04/15.
 */
public class ListViewTimeSlotsAdapter extends BaseAdapter {

    Context context;
    ISlotItem slotTime;
    ArrayList<ISlotItem> list;

    public ListViewTimeSlotsAdapter(Context c, ArrayList<ISlotItem> list){
        this.context = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list != null )
        {
            return list.size();

        } else return 0;
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

        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.time_slot,null);
        }
        TextView reservered = (TextView) view.findViewById(R.id.reservedTextView);
        TextView slotTime = (TextView) view.findViewById(R.id.timeTextView);
        TextView slotEndTime = (TextView) view.findViewById(R.id.endTimeTextView);

        slotTime.setText(list.get(position).getTime());
        slotEndTime.setText( "Time ends at " + list.get(position).getEndTime());

        if (list.get(position).getReserver() <= 0)
        {
            //Log.i("Reserved check","Not reserved, room " + Integer.toString(list.get(position).getReserver()));
            reservered.setText(context.getString(R.string.notReserved));
            //reservered.setBackgroundResource(R.drawable.oval);
        } else{
            //Log.i("Reserved check","Reserved, room " + Integer.toString(list.get(position).getReserver()) + "Time " + list.get(position).getTime() );
            reservered.setText(context.getString(R.string.reserved) + " " + list.get(position).getReserver());
        }

        return view;
    }
}
