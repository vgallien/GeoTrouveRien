package valentin.imie.geotrouverien.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import valentin.imie.geotrouverien.R;
import valentin.imie.geotrouverien.adapter.PopulationAdapter;
import valentin.imie.geotrouverien.model.Pays;

/**
 * Created by valentin on 08/02/18.
 */

public class InfoPays implements GoogleMap.InfoWindowAdapter {

    Context context;

    public InfoPays(Context context) {
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
        tvTitre.setText(pays.getNom());

        ListView tvInfos = (ListView) view.findViewById(R.id.infosHabitants);
        PopulationAdapter pa = new PopulationAdapter(context, R.layout.population_item, pays.getPop());
        tvInfos.setAdapter(pa);
        // tvInfos.setText("Population : " + String.valueOf(pays.getPopulation()) + " habitants");

        return view;
    }

}
