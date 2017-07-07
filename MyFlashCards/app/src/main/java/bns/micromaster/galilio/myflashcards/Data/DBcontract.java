package bns.micromaster.galilio.myflashcards.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URL;

/**
 * Created by AYMANE BNS on 05/07/2017.
 */
public class DBcontract {



    public static final String AUTHORITY="bns.micromaster.galilio.myflashcards";
    public static final String DB_Path="FlashCard";
    public static final Uri BASE_URI=Uri.parse("content://"+AUTHORITY);

    public static  final class DBDefinition implements BaseColumns {


        public static final String TABLE_NAME="flashcard";
        public static final String COLUM1="flashcard_id";
        public static final String COLUM2="flashcard_question";
        public static final String COLUM3="flashcard_answer";

        public static final Uri DB_PROVIDER_URI=BASE_URI.buildUpon().appendPath(DB_Path).build();


    }











}
