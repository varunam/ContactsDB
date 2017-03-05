package data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.ArrayList;

import model.Contacts;

/**
 * Created by vaam on 03-03-2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public ArrayList<Contacts> contactsList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, " +
                Constants.CONTACTS_NAME + " TEXT, " + Constants.PHONE_NUMBER + " INTEGER, " + Constants.EMAIL_ID + " TEXT);";

        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);

    }

    public int totalContacts()
    {
        int totalcontacts = 0;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);

        totalcontacts = cursor.getCount();
        dba.close();
        return totalcontacts;
    }

    public void deleteContact(int id)
    {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ", new String[] {String.valueOf(id)});
        //dba.rawQuery(" DELETE FROM " + Constants.TABLE_NAME + " WHERE " + Constants.KEY_ID + " = " + id + ";" , null);
        dba.close();
    }

    public void addContact (Contacts contact)
    {
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CONTACTS_NAME, contact.getName());
        values.put(Constants.PHONE_NUMBER, contact.getPhoneNumber());
        values.put(Constants.EMAIL_ID, contact.getEmail().toLowerCase());

        dba.insert(Constants.TABLE_NAME, null, values);
        Log.v("Contact added!", "Yes Macha");
        dba.close();
    }

    public boolean findRecordifExists(String number){
        SQLiteDatabase dba = this.getReadableDatabase();

        String query = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.PHONE_NUMBER + " = " + number + ";";
        Cursor cursor = dba.rawQuery(query, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public boolean findEmailifExists(String email){
        SQLiteDatabase dba = this.getReadableDatabase();

        String query = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.EMAIL_ID + " = ? ";
        Cursor cursor = dba.rawQuery(query , new String[] {email});

        if(cursor.getCount()<1)
        {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public ArrayList<Contacts> getContacts ()
    {
        //contactsList.clear();
        SQLiteDatabase dba = this.getReadableDatabase();

        String query = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.CONTACTS_NAME + " COLLATE NOCASE ASC; ";
        Cursor cursor = dba.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{

                Contacts contacts = new Contacts();
                contacts.setName(cursor.getString(cursor.getColumnIndex(Constants.CONTACTS_NAME)));
                contacts.setPhoneNumber(cursor.getString(cursor.getColumnIndex((Constants.PHONE_NUMBER))));
                contacts.setEmail(cursor.getString(cursor.getColumnIndex(Constants.EMAIL_ID)));
                contacts.setContactsID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                contactsList.add(contacts);

            }while(cursor.moveToNext());
        }

        cursor.close();
        dba.close();

        return contactsList;
    }
}
