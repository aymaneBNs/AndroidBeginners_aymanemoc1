package bns.micromaster.galilio.myflashcards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bns.micromaster.galilio.myflashcards.Data.DBcontract;

/**
 * Created by AYMANE BNS on 04/07/2017.
 */
public class AddQuestionFragment extends android.support.v4.app.DialogFragment {

  EditText question,answer;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder mydialogueBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myDialogueView = inflater.inflate(R.layout.add_question_dialogue, null, false);
        question = (EditText) myDialogueView.findViewById(R.id.questionET);
        answer = (EditText) myDialogueView.findViewById(R.id.answerET);

        mydialogueBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        mydialogueBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userquestion=question.getText().toString();
                String useranswer=answer.getText().toString();
                if(!userquestion.isEmpty()&&!useranswer.isEmpty()){
                    getContext().getContentResolver().insert(DBcontract.DBDefinition.DB_PROVIDER_URI, getContentValue(userquestion,useranswer));//  add new question using ContentResolver
                }else{

                    Toast.makeText(getContext(), "Question Or Answer Is Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mydialogueBuilder.setView(myDialogueView);
        mydialogueBuilder.setCancelable(true);

        return mydialogueBuilder.create();

    }

    public ContentValues getContentValue(String s1,String s2){
        ContentValues contentValues= new ContentValues();
        contentValues.put(DBcontract.DBDefinition.COLUM2,s1);
        contentValues.put(DBcontract.DBDefinition.COLUM3,s2);


        return  contentValues;

    }
}



