package valentin.imie.geotrouverien.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import valentin.imie.geotrouverien.BR;

/**
 * Created by valentin on 07/02/18.
 */

public class Pays extends BaseObservable {

    private String nom;
    private int population;
    private Double latitude;
    private Double longitude;
    private LatLng pos;
    private Population[] pop;

    public Pays(String nom) {
        this.nom = nom;
    }

    @Bindable
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        notifyPropertyChanged(BR.nom);
    }

    @Bindable
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
        notifyPropertyChanged(BR.population);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Pays : " + nom;
    }

    public LatLng getPos() {
        return pos;
    }

    public void setPos(LatLng pos) {
        this.pos = pos;
    }

    public Population[] getPop() {
        return pop;
    }

    public void setPop(Population[] pop) {
        this.pop = pop;
    }

    public String getDifference() {
        int diff = pop[1].getNumber() - pop[0].getNumber();
        if (diff > 0) {
            return "Il y aura " + String.valueOf(diff) + " naissances :).";
        } else if (diff < 0) {
            return "Il y aura " + String.valueOf(diff * -1) + " morts :(.";
        }
        return "Mais qu'est-ce qu'ils branlent dans ce pays ?";
    }

    public static class Population extends BaseObservable {
        private String date;
        private int number;

        public Population(String date, int number) {
            this.date = date;
            this.number = number;
        }

        @Bindable
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
            notifyPropertyChanged(BR.date);
        }

        @Bindable
        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
            notifyPropertyChanged(BR.number);
        }
    }
}
