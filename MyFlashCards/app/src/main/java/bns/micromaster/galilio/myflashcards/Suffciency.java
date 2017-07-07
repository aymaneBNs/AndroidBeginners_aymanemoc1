package bns.micromaster.galilio.myflashcards;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import bns.micromaster.galilio.myflashcards.R;

/**
 * Created by AYMANE BNS on 04/07/2017.
 */
public class Suffciency  extends AppCompatActivity {
    TextView question,answer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.didplay_answer_layout);
        question=(TextView)findViewById(R.id.questionTV);
        answer=(TextView)findViewById(R.id.answerTV);
        question.setText(""+getIntent().getStringExtra("question"));
        answer.setText(""+getIntent().getStringExtra("answer"));

    }
}
