package app.contactsdbapplication.com.contactsdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import data.DatabaseHandler;
import model.Contacts;

public class ContactsDetailsActivity extends AppCompatActivity {

    private TextView cdName, cdPhoneNumber, cdEmailID;
    private ImageView imageView;
    private Button deleteButton;
    private int ContactsID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);

        int[] imagesArray = {
                R.drawable.fimage,
                R.drawable.simage,
                R.drawable.timage,
                R.drawable.foimage,
                R.drawable.fiimage,
                R.drawable.siximage
        };

        cdName = (TextView) findViewById(R.id.cdName);
        cdPhoneNumber = (TextView) findViewById(R.id.cdPhoneNumber);
        cdEmailID = (TextView) findViewById(R.id.cdEmail);
        imageView = (ImageView) findViewById(R.id.cdImage);
        deleteButton = (Button) findViewById(R.id.cdDelete);

        Contacts contacts = (Contacts) getIntent().getSerializableExtra("ContactsObj");

        cdName.setText(contacts.getName());
        cdPhoneNumber.setText(contacts.getPhoneNumber());
        cdEmailID.setText(contacts.getEmail());
        ContactsID = contacts.getContactsID();

        Random rand = new Random();
        int randNum = imagesArray[rand.nextInt(imagesArray.length)];
        imageView.setImageResource(randNum);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ContactsDetailsActivity.this);
                alert.setTitle("Delete?");
                alert.setMessage("Are you sure to delete this contact?");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes, Please", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                        //Toast.makeText(getApplicationContext(), "ID is: " + ContactsID, Toast.LENGTH_SHORT).show();
                        dba.deleteContact(ContactsID);
                        Toast.makeText(getApplicationContext(), "Contact deleted successfully!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ContactsDetailsActivity.this, DisplayContactsActivity.class));

                        //remove item from stack
                        //ContactsDetailsActivity.this.finish();
                    }
                });

                alert.show();
            }
        });
    }
}
