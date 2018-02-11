package valentin.imie.geotrouverien.async;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import valentin.imie.geotrouverien.activity.CountriesActivity;
import valentin.imie.geotrouverien.model.Pays;
import valentin.imie.geotrouverien.tools.MyTools;

/**
 * Created by valentin on 08/02/18.
 */

public class GetLocalisation extends AsyncTask<String, String, String> {

    private CountriesActivity activity;
    private Pays pays;

    public GetLocalisation(CountriesActivity activity, Pays pays) {
        this.activity = activity;
        this.pays = pays;
    }

    @Override
    protected String doInBackground(String... strings) {
        String adr = strings[0];
        String tk = MyTools.askQuestionToServer(adr);
        return tk;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        getElements(s);
        activity.onLocalisationLoaded(pays);

    }

    public void getElements(String datas) {
        JSONObject c;
        try {
            c = new JSONObject(datas);
            JSONArray reponse1 = c.getJSONArray("results");
            JSONObject reponse2 = reponse1.getJSONObject(0);
            JSONObject geometry = reponse2.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            Double lat = location.getDouble("lat");
            Double lng = location.getDouble("lng");
            pays.setLatitude(lat);
            pays.setLongitude(lng);
            pays.setPos(new LatLng(lat, lng));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
