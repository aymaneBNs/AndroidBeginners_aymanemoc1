package bns.micromaster.galilio.myflashcards;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;

import bns.micromaster.galilio.myflashcards.Adapter.FlashCardAdapter;
import bns.micromaster.galilio.myflashcards.Data.DBcontract;

public class MainActivity extends AppCompatActivity  implements InitialInformationTask.InitialInformationListeListner,LoaderManager.LoaderCallbacks<Cursor> {

ListView MyInitialInformationLv;   //  Liste view that will be use to display initial information

RecyclerView  MyFlashCardRecyclerView;   // RecyclerView Will use this to display the Question answer added by the user

FloatingActionButton btn;

FlashCardAdapter flashCardAdapter;   //  the adapter for the MyFlashCardRecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        MyInitialInformationLv=(ListView)findViewById(R.id.initial_informationLV);

        MyFlashCardRecyclerView=(RecyclerView)findViewById(R.id.FlashCardRV);
        LinearLayoutManager LLM= new LinearLayoutManager(this);
        MyFlashCardRecyclerView.setLayoutManager(LLM);
        MyFlashCardRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        btn=(FloatingActionButton)findViewById(R.id.fab);
       new InitialInformationTask(this).execute(MyInitialInformationLv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddQuestionFragment fragment= new AddQuestionFragment();
                fragment.show(getSupportFragmentManager(),"Add New Question");

            }
        });


        flashCardAdapter= new FlashCardAdapter(this);// initialize the adapter
        MyFlashCardRecyclerView.setAdapter(flashCardAdapter);

        getSupportLoaderManager().initLoader(0, null,this); // initialize the loader
    }








    //   this methode is for initial_Information Item Click
    @Override
    public void InitialItemClicked( String ItemQuestion, String Item_answer) {
        Intent intent= new Intent(this,Suffciency.class);//  go to the 2 activity to display the answer and question
        intent.putExtra("question",ItemQuestion);//
        intent.putExtra("answer",Item_answer);//
       startActivity(intent);//
    }



    // load the data from Db using ContentProvider
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DBcontract.DBDefinition.DB_PROVIDER_URI,null,null,null,null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
flashCardAdapter.SwapCursur(data);//  call the RecyclerView to set the changes


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
flashCardAdapter.SwapCursur(null);
    }






 }
