package com.example.ksg.omawash;

import com.example.ksg.omawash.ISlotItem;

/**
 * Created by lassebuesvendsen on 31/05/15.
 */
public interface ISlotReserver {
    void onISlotItemRequested(final ISlotItem item, int dayPos, int timePos);
}
