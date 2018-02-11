package valentin.imie.geotrouverien.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import valentin.imie.geotrouverien.R;
import valentin.imie.geotrouverien.databinding.PaysItemBinding;
import valentin.imie.geotrouverien.model.Pays;


/**
 * Created by valentin on 07/02/18.
 */

public class CountryAdapter extends ArrayAdapter<Pays> {

    final private int resource;

    public CountryAdapter(@NonNull Context context, int resource, @NonNull Pays[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PaysItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.pays_item, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (PaysItemBinding) convertView.getTag();
        }
        Pays p = getItem(position);
        binding.setPays(p);
        return convertView;
    }
}


