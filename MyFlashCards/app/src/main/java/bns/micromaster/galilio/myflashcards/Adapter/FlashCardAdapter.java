package bns.micromaster.galilio.myflashcards.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import bns.micromaster.galilio.myflashcards.Data.DBcontract;
import bns.micromaster.galilio.myflashcards.R;
import bns.micromaster.galilio.myflashcards.Suffciency;

/**
 * Created by AYMANE BNS on 05/07/2017.
 */

public class FlashCardAdapter  extends RecyclerView.Adapter<FlashCardAdapter.MyViewHolder> {

  ArrayList<String>  Question_Liste;
    ArrayList<String> Answer_Liste;

      String new_question,new_answer;
      Context context;
      Cursor cursor;
    public  FlashCardAdapter(Context context){
        this.context=context;
        Question_Liste= new ArrayList<>();
        Answer_Liste= new ArrayList<>();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from((parent.getContext())).inflate(R.layout.card_item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       cursor.moveToPosition(position);
        holder.OnBindViewFromHolder(cursor);

    }

    public void SwapCursur(Cursor c){
          this.cursor=c;
          notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount():0;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


TextView card_item;

        public MyViewHolder(View itemView) {
            super(itemView);

            card_item=(TextView)itemView.findViewById(R.id.card_itemTV);

            itemView.setOnClickListener(this);
        }

        public void OnBindViewFromHolder(Cursor c){
            new_question =c.getString(c.getColumnIndex(DBcontract.DBDefinition.COLUM2));
            new_answer=c.getString(c.getColumnIndex(DBcontract.DBDefinition.COLUM3));
         
                Question_Liste.add(new_question);
                Answer_Liste.add(new_answer);
                card_item.setText(" "+c.getString(c.getColumnIndex(DBcontract.DBDefinition.COLUM2)));
            


        }

        @Override
        public void onClick(View v) {
        Intent intent= new Intent(context, Suffciency.class);
            intent.putExtra("question",Question_Liste.get(getAdapterPosition()));
            intent.putExtra("answer",Answer_Liste.get(getAdapterPosition()));
              context.startActivity(intent);



        }






     
    }



}
