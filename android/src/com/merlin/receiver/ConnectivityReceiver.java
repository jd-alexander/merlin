package com.merlin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.merlin.Log;
import com.merlin.receiver.event.ConnectionEventPackager;
import com.merlin.receiver.event.ConnectivityChangeEvent;
import com.merlin.service.MerlinService;

public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && connectivityAction(intent)) {
            Log.d("onReceive : " + intent.getAction());
            ConnectivityChangeEvent connectivityChangedEvent = ConnectionEventPackager.from(intent);
            notifyMerlinService(context, connectivityChangedEvent);
        }
    }

    private boolean connectivityAction(Intent intent) {
        return ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction());
    }

    private void notifyMerlinService(Context context, ConnectivityChangeEvent connectivityChangedEvent) {
        IBinder binder = peekService(context, new Intent(context, MerlinService.class));
        MerlinService merlinService = ((MerlinService.LocalBinder) binder).getService();
        merlinService.onConnectivityChanged(connectivityChangedEvent);
    }

}
