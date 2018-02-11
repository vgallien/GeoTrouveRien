package valentin.imie.geotrouverien.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import valentin.imie.geotrouverien.R;
import valentin.imie.geotrouverien.model.Pays;

/**
 * Created by valentin on 07/02/18.
 */

public class InfoWindow implements GoogleMap.InfoWindowAdapter {

    Context context;

    public InfoWindow(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Pays pays = (Pays) marker.getTag();
        LayoutInflater li  = LayoutInflater.from(context);
        View view = li.inflate(R.layout.info_pays, null);

        TextView tvTitre = (TextView) view.findViewById(R.id.titre);
        tvTitre.setText(marker.getTitle());

        TextView tvInfos = (TextView) view.findViewById(R.id.infos);
        tvInfos.setText(marker.getSnippet());

        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource((Integer) marker.getTag());

        return view;
    }
}
