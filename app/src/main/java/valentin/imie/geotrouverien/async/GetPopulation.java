package valentin.imie.geotrouverien.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import valentin.imie.geotrouverien.activity.CountriesActivity;
import valentin.imie.geotrouverien.model.Pays;
import valentin.imie.geotrouverien.tools.MyTools;

/**
 * Created by valentin on 08/02/18.
 */

public class GetPopulation extends AsyncTask<String, String, String> {
    // GET /population/{country}/today-and-tomorrow/

    private CountriesActivity activity;
    private Pays pays;

    public GetPopulation(CountriesActivity activity, Pays pays) {
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
    }

    public void getElements(String datas) {
        JSONObject c;
        try {
            c = new JSONObject(datas);
            JSONArray reponse = c.getJSONArray("total_population");
            try {
                pays.setPopulation(Integer.parseInt(reponse.getJSONObject(0).getString("population")));
                Pays.Population p = new Pays.Population(
                        reponse.getJSONObject(0).getString("date"),
                        Integer.parseInt(reponse.getJSONObject(0).getString("population"))
                );
                Pays.Population p2 = new Pays.Population(
                        reponse.getJSONObject(1).getString("date"),
                        Integer.parseInt(reponse.getJSONObject(1).getString("population"))
                );
                Pays.Population[] tPop = { p, p2 };
                pays.setPop(tPop);
            } catch(NumberFormatException e) {
                Toast.makeText(activity, "Erreur au niveau de la population", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

}
