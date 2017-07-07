package bns.micromaster.galilio.myflashcards;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by AYMANE BNS on 04/07/2017.
 */
public class InitialInformationTask  extends AsyncTask<ListView,Integer,HashMap<String,String>>{
    ListView MyInitialInformationLv;
    Context context;
    InitialInformationListeListner  initial_information_listner;
    String initialQuestionTab[];  // table for question we use this in the listeView Adapter
    String initialAnswernTab[];   // table for answers

public InitialInformationTask( Context context){
    this.context=context;
    initial_information_listner=(InitialInformationListeListner)context;  // initial initial_information_listner that is implemented in MainActivity
}




    @Override
    protected HashMap<String,String> doInBackground(ListView... params) {
        this.MyInitialInformationLv=params[0];  // initial information listeView
        HashMap<String,String> initialQuestionListe= new HashMap<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());     // reading info from json File
            JSONArray MyJsonListe = obj.getJSONArray("liste_question");
            for (int i = 0; i < MyJsonListe.length(); i++) {
                JSONObject MyJsonArrayObj = MyJsonListe.getJSONObject(i);
                initialQuestionListe.put(MyJsonArrayObj.getString("question"),MyJsonArrayObj.getString("answer"));
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return initialQuestionListe;
    }

    @Override
    protected void onPostExecute(HashMap<String,String> HashMapQuestionValue) {
          initialQuestionTab = new String[HashMapQuestionValue.size()];// table for question
          initialAnswernTab = new String[HashMapQuestionValue.size()];// table for answers
        int i=0;
        Iterator it = HashMapQuestionValue.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            initialQuestionTab[i]= (String) pair.getKey();
            initialAnswernTab[i]=(String) pair.getValue();

            it.remove();
            ++i;
        }

        ArrayAdapter adapter=  new ArrayAdapter(context,R.layout.initial_information_item,R.id.initial_ifo_itemTV,initialQuestionTab);
        MyInitialInformationLv.setAdapter(adapter);// set the adapter using the question table array
        MyInitialInformationLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initial_information_listner.InitialItemClicked(initialQuestionTab[position],initialAnswernTab[position]); // send to question and the answer to the Main activity to start a new activity
            }
        });
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

    public interface InitialInformationListeListner   {   // this interface will implemented in the main activity
        void InitialItemClicked(String ItemQuestion,String Item_answer);
    }


}
