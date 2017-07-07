package bns.micromaster.galilio.myflashcards;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import bns.micromaster.galilio.myflashcards.Data.DBcontract;

/**
 * Created by AYMANE BNS on 06/07/2017.
 */
public class WidgetProvider  extends AppWidgetProvider {


Context context;
   private static  String last_random_question="";// this is used to store the last question

  static  HashMap<String,String> QuestionAnswerListe  = new HashMap<String,String>();; //  this hashMap is for allQuestionAndAnswer initial and DB
    static  HashMap<String,String> DbQuestionAnswerListe  = new HashMap<String,String>();  // Question and answer stored in DB
    static  HashMap<String,String> InitilaQuestionAnswerListe  = new HashMap<String,String>();; //Question and Answer Stored In Json File




    protected PendingIntent getPendingSelfIntent(Context context, String action,String extras,int[] appWidgetIds,String MyOnClick) {
        Intent intent = new Intent(context, WidgetProvider.class);
        intent.setAction(action);
        intent.putExtra(extras,appWidgetIds);
        intent.setData(Uri.parse(MyOnClick));
        return PendingIntent.getBroadcast(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.context=context;

        final int max = appWidgetIds.length;

        for (int i = 0; i < max; i++) {
            int mywidgetid = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.next_button, getPendingSelfIntent(context, AppWidgetManager.ACTION_APPWIDGET_UPDATE,AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds,"Question"));
            views.setOnClickPendingIntent(R.id.answer_button, getPendingSelfIntent(context,AppWidgetManager.ACTION_APPWIDGET_UPDATE,AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds,"Answer"));
            appWidgetManager.updateAppWidget(mywidgetid, views);

        }

    }





//  this methode will work like a listner to handle user click
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views;

        if(intent.getDataString()!=null){
            if(intent.getDataString().equals("Question")){  // user click to Next Question
                 views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                String myRandomQuestion=GetRandomQuestion(GetBDData(context));// generate a random question
                views.setTextViewText(R.id.widgetTV," Q : "+myRandomQuestion);  //set the question
               last_random_question=myRandomQuestion;//   save this last question
                AppWidgetManager.getInstance(context).updateAppWidget(  // call the widget managet to update the widget
                        new ComponentName(context, WidgetProvider.class),views);

            }else{     // user click to See Answer

                 views = new RemoteViews(context.getPackageName(),
                        R.layout.widget_layout);
                views.setTextViewText(R.id.widgetTV," A : "+GetAnswer(last_random_question));  // call the methode GetAnswer
                AppWidgetManager.getInstance(context).updateAppWidget(
                        new ComponentName(context, WidgetProvider.class),views);
            }
        }

    }


    // here I didn't use loader because we have a small quantity of data so I use A ContentResolver
    public HashMap<String, String> GetBDData(Context context) {

        //Get the date from DB
        HashMap<String, String> QuestionAnswer = new HashMap<String,String>();
        Cursor cursor = context.getContentResolver().query(DBcontract.DBDefinition.DB_PROVIDER_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String question = cursor.getString(cursor.getColumnIndex(DBcontract.DBDefinition.COLUM2));
            String answer = cursor.getString(cursor.getColumnIndex(DBcontract.DBDefinition.COLUM3));
            QuestionAnswer.put(question, answer);
        }
        this.DbQuestionAnswerListe=QuestionAnswer;   //  this HashMap contail only user question and answer


        ////////////////////  Get the initial information usin AsyncTask  and initialize the InitilaQuestionAnswerListe  using putInitialInformationListe
        new InitialInformationTaskForWidget(context).execute("Task");

        try {
            Thread.currentThread().sleep(100); // let the InitialInformationTaskForWidget get the information and initila the hashMap (InitilaQuestionAnswerListe)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //////////////////////////
        // set the current hash map

        this.QuestionAnswerListe=Merge2Hashes(DbQuestionAnswerListe,InitilaQuestionAnswerListe);  //  merge 2 hasheMap
        return QuestionAnswerListe;
    }




    // generate  a random question
    public String GetRandomQuestion(HashMap<String, String> QuestionAnswer) {
        ArrayList<String> QuestionListe = new ArrayList<>();
        Iterator it = QuestionAnswer.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            QuestionListe.add((String) pair.getKey());
            ++i;
        }

        Random randomValue = new Random();
        int randomIndex = randomValue.nextInt(QuestionListe.size());
        return QuestionListe.get(randomIndex);

    }




    // this methode will retourn an answer for a given question
    public String GetAnswer(String Question){

        String answer=QuestionAnswerListe.get(Question);
        if(answer!=null){
            return answer;
        }else{

            Toast.makeText(context, "No Answer For This  Question ", Toast.LENGTH_SHORT).show();
            return "";
        }
    }






    public HashMap<String,String> Merge2Hashes(HashMap<String,String> liste1,HashMap<String,String> liste2){
      HashMap<String,String> MyListe= new HashMap<>();
        MyListe.putAll(liste1);
        MyListe.putAll(liste2);
        return MyListe;
    }


    public void putInitialInformationListe(HashMap<String,String> InitialInfoList){   // this will called from the AsynkTask
      this.InitilaQuestionAnswerListe=InitialInfoList;
    }



}







