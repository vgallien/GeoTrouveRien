package valentin.imie.geotrouverien.listener;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import valentin.imie.geotrouverien.R;
import valentin.imie.geotrouverien.model.Pays;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by valentin on 08/02/18.
 */

public class MyWindowListener implements GoogleMap.OnInfoWindowClickListener {

    private Context c;

    public MyWindowListener(Context c) {
        this.c = c;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Pays p = (Pays) marker.getTag();
        Toast.makeText(c, p.getDifference(), Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(R.drawable.david)
                        .setContentTitle(p.getNom())
                        .setContentText(p.getDifference());

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
