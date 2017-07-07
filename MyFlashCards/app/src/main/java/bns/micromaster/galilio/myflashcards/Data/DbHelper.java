package bns.micromaster.galilio.myflashcards.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AYMANE BNS on 05/07/2017.
 */
public class DbHelper  extends SQLiteOpenHelper {



    public  static final String  DB_NAME="FlashCard.db";

    public static final  int DB_VERSION=1;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATION_QUERY=" CREATE TABLE "+ DBcontract.DBDefinition.TABLE_NAME+" ( "+
                DBcontract.DBDefinition.COLUM1+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                DBcontract.DBDefinition.COLUM2+" TEXT ,"+
                DBcontract.DBDefinition.COLUM3+" TEXT  );";

        db.execSQL(CREATION_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROPE TABLE IF EXIST "+ DBcontract.DBDefinition.TABLE_NAME);
        onCreate(db);
    }



}
