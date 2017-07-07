package bns.micromaster.galilio.myflashcards.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by AYMANE BNS on 05/07/2017.
 */
public class DbContentProvider  extends ContentProvider {

     DbHelper myDbHelper;

    @Override
    public boolean onCreate() {
        myDbHelper= new DbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
     Cursor cursur=null;
     cursur=myDbHelper.getReadableDatabase().query(DBcontract.DBDefinition.TABLE_NAME,projection,null,null,null,null,null);
      cursur.setNotificationUri(getContext().getContentResolver(),uri);
      return  cursur;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
       myDbHelper.getWritableDatabase().insert(DBcontract.DBDefinition.TABLE_NAME,null,values);
      getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}




