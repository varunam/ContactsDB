package app.contactsdbapplication.com.contactsdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DatabaseHandler;
import model.Contacts;

public class DisplayContactsActivity extends AppCompatActivity {

    private ListView listView;
    private TextView totalContacts;
    private Button addItem;
    private ArrayList<Contacts> myContactsList = new ArrayList<>();
    private DatabaseHandler dba;
    private Button addButton;
    int backpressedonce = 0;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DisplayContactsActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);

        addItem = (Button) findViewById(R.id.disaddItem);
        listView = (ListView) findViewById(R.id.dislist);
        totalContacts = (TextView) findViewById(R.id.disTotalContacts);
        addButton = (Button) findViewById(R.id.disaddItem);

        refreshData();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayContactsActivity.this, MainActivity.class));
            }
        });

    }

    private void refreshData() {

        myContactsList.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Contacts> contactsFromDB = dba.getContacts();

        int totalContact = dba.totalContacts();
        totalContacts.setText(totalContact + " Contacts");

        for(int i=0; i<contactsFromDB.size(); i++)
        {
            String name = contactsFromDB.get(i).getName();
            String phoneNumber = contactsFromDB.get(i).getPhoneNumber();
            String email = contactsFromDB.get(i).getEmail();
            int contactsID = contactsFromDB.get(i).getContactsID();

            Log.v("Contact added: " , String.valueOf(contactsID));

            Contacts contacts = new Contacts();
            contacts.setEmail(email);
            contacts.setName(name);
            contacts.setPhoneNumber(phoneNumber);
            contacts.setContactsID(contactsID);

            myContactsList.add(contacts);
        }

        dba.close();

        //setup Adapter
        CustomListViewAdapter contactsAdapter = new CustomListViewAdapter(DisplayContactsActivity.this, R.layout.list_row, contactsFromDB);
        listView.setAdapter(contactsAdapter);
        contactsAdapter.notifyDataSetChanged();

    }
}
