package bns.micromaster.galilio.myflashcards;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import bns.micromaster.galilio.myflashcards.WidgetProvider;

/**
 * Created by AYMANE BNS on 07/07/2017.
 */
public class InitialInformationTaskForWidget  extends AsyncTask<String,Integer,HashMap<String,String>> {

    Context context;
    HashMap<String,String> InitialQuestionAnswerListe;
   public InitialInformationTaskForWidget(Context context){
       this.context=context;
       InitialQuestionAnswerListe= new HashMap<>();
   }



    @Override
    protected HashMap<String, String> doInBackground(String... params) {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray MyJsonListe = obj.getJSONArray("liste_question");
            for (int i = 0; i < MyJsonListe.length(); i++) {
                JSONObject MyJsonArrayObj = MyJsonListe.getJSONObject(i);
                InitialQuestionAnswerListe.put(MyJsonArrayObj.getString("question"),MyJsonArrayObj.getString("answer"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return InitialQuestionAnswerListe;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("initial_information.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
        WidgetProvider provider= new WidgetProvider();
        provider.putInitialInformationListe(stringStringHashMap);  //  call putInitialInformationListe to initialize the initialInformationHashMap

    }
}
