package valentin.imie.geotrouverien.async;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import valentin.imie.geotrouverien.activity.CountriesActivity;
import valentin.imie.geotrouverien.activity.MainActivity;
import valentin.imie.geotrouverien.model.Pays;
import valentin.imie.geotrouverien.tools.MyTools;

/**
 * Created by valentin on 07/02/18.
 */

public class GetCountries extends AsyncTask<String, String, String> {

    CountriesActivity activity;

    public GetCountries(CountriesActivity activity) {
        this.activity = activity;
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
        activity.setPays(getElements(s));
        activity.onCountriesLoaded();
    }

    public static Pays[] getElements(String datas) {
        JSONObject c;
        try {
            c = new JSONObject(datas);
            JSONArray reponse = c.getJSONArray("countries");
            Pays[] pays = new Pays[reponse.length()];
            for (int i = 0; i < reponse.length(); i++) {
                pays[i] = new Pays(reponse.getString(i));
            }
            return pays;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
