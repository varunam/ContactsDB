package app.contactsdbapplication.com.contactsdb;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Contacts;

public class MainActivity extends AppCompatActivity {

    private EditText mainName, mainPhoneNumber, mainEmail;
    private Button mainSaveButton;
    private DatabaseHandler dba;
    private Button showAll;
    int backpressedonce = 0;

    @Override
    public void onBackPressed() {
        if(backpressedonce==1) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        backpressedonce++;
        if(backpressedonce<2)
            Toast.makeText(getApplicationContext(), "Please press back again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpressedonce=0;
            }
        },4000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dba = new DatabaseHandler(MainActivity.this);

        mainName = (EditText) findViewById(R.id.mainName);
        mainPhoneNumber = (EditText) findViewById(R.id.mainNumber);
        mainEmail = (EditText) findViewById(R.id.mainEmail);
        mainSaveButton = (Button) findViewById(R.id.mainSaveButton);
        showAll = (Button) findViewById(R.id.showAllContacts);

        mainSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayContactsActivity.class));
            }
        });

    }

    private void saveToDB() {
        Contacts contact = new Contacts();

        String name = mainName.getText().toString().trim();
        String phoneNumber = mainPhoneNumber.getText().toString().trim();
        String emailID = mainEmail.getText().toString().toLowerCase().trim();

        if (name.equals("") && phoneNumber.equals("")) {
            mainName.setError("Required");
            mainName.requestFocus();
            mainPhoneNumber.setError("Required");
            //Toast.makeText(getApplicationContext(), "Please Enter Name and Phone number", Toast.LENGTH_SHORT).show();
        }
        else if (name.equals("")) {
            mainName.setError("Required");
            mainName.requestFocus();
            //Toast.makeText(getApplicationContext(),"Name is required!", Toast.LENGTH_SHORT).show();
        }
        else if (phoneNumber.equals("") || (phoneNumber.length()!=10) || (phoneNumber.contains("0000")) || ((!phoneNumber.startsWith("8"))
                && !phoneNumber.startsWith("9") && !phoneNumber.startsWith("7"))) {
            mainPhoneNumber.setError("Please enter valid 10 digit number");
            mainPhoneNumber.requestFocus();
            //Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
        }
        else if (dba.findRecordifExists(phoneNumber)){
            mainPhoneNumber.setError("Number exists already!");
        }
        else if (!emailID.equals("") && ((!emailID.contains("@")) || (!emailID.contains(".com"))))
        {
            mainEmail.setError("Invalid Email ID");
            mainEmail.requestFocus();
            //Toast.makeText(getApplicationContext(), "Invalid Email ID", Toast.LENGTH_SHORT).show();
        }
        else if(dba.findEmailifExists(emailID)){
            mainEmail.setError("Email ID exists");
            mainEmail.requestFocus();
        }
        else
        {
            contact.setName(name);
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(emailID);

            dba.addContact(contact);
            dba.close();

            mainName.setText("");
            mainPhoneNumber.setText("");
            mainEmail.setText("");

            startActivity(new Intent(MainActivity.this, DisplayContactsActivity.class));
        }

    }
}
