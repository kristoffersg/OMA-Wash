package bueenterprise.calendarday;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lassebuesvendsen on 25/05/15.
 */
public class ListScrollSyncer
        implements AbsListView.OnScrollListener, View.OnTouchListener, GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;
    private Set<ListView> listSet = new HashSet<ListView>();
    private ListView currentTouchSource;

    private int currentOffset = 0;
    private int currentPosition = 0;

    public void addList(ListView list) {
        listSet.add(list);
        list.setOnTouchListener(this);
        list.setSelectionFromTop(currentPosition, currentOffset);

        if (gestureDetector == null)
            gestureDetector = new GestureDetector(list.getContext(), this);
    }

    public void removeList(ListView list) {
        listSet.remove(list);
    }

    public boolean onTouch(View view, MotionEvent event) {
        ListView list = (ListView) view;

        if (currentTouchSource != null) {
            list.setOnScrollListener(null);
            return gestureDetector.onTouchEvent(event);
        } else {
            list.setOnScrollListener(this);
            currentTouchSource = list;

            for ( ListView list_  : listSet)
                if (list_ != currentTouchSource)
                    list_.dispatchTouchEvent(event);

            currentTouchSource = null;
            return false;
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.getChildCount() > 0) {
            currentPosition = view.getFirstVisiblePosition();
            currentOffset = view.getChildAt(0).getTop();
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        scrolling = scrollState != SCROLL_STATE_IDLE;
    }

    // GestureDetector callbacks
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    public boolean onDown(MotionEvent e) {
        return !scrolling;
    }

    public void onLongPress(MotionEvent e) {
    }

    public void onShowPress(MotionEvent e) {
    }

    private boolean scrolling;




}
