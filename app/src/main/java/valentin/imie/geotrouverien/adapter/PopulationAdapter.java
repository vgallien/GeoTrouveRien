package valentin.imie.geotrouverien.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import valentin.imie.geotrouverien.R;
import valentin.imie.geotrouverien.databinding.PaysItemBinding;
import valentin.imie.geotrouverien.databinding.PopulationItemBinding;
import valentin.imie.geotrouverien.model.Pays;

/**
 * Created by valentin on 08/02/18.
 */

public class PopulationAdapter extends ArrayAdapter<Pays.Population> {

    private int resource;

    public PopulationAdapter(@NonNull Context context, int resource, @NonNull Pays.Population[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater li  = LayoutInflater.from(getContext());
            convertView = li.inflate(this.resource, null);

            TextView tvDate = (TextView) convertView.findViewById(R.id.date);
            TextView tvNumber = (TextView) convertView.findViewById(R.id.number);

            PopulationViewHolder pvh = new PopulationViewHolder();

            pvh.tvDate = tvDate;
            pvh.tvNumber = tvNumber;

            convertView.setTag(pvh);
        }

        Pays.Population p = getItem(position);

        PopulationViewHolder pvh = (PopulationViewHolder) convertView.getTag();

        pvh.tvDate.setText("Date : " + p.getDate());
        pvh.tvNumber.setText("Population : " + String.valueOf(p.getNumber()) + " habitants");

        return convertView;
    }

    public static class PopulationViewHolder {

        protected TextView tvDate;
        protected TextView tvNumber;

    }

}
