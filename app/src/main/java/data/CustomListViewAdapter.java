package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.contactsdbapplication.com.contactsdb.ContactsDetailsActivity;
import app.contactsdbapplication.com.contactsdb.DisplayContactsActivity;
import app.contactsdbapplication.com.contactsdb.R;
import model.Contacts;

/**
 * Created by vaam on 04-03-2017.
 */
public class CustomListViewAdapter extends ArrayAdapter<Contacts> {

    private int layoutResource;
    private ArrayList<Contacts> myContactsList;
    private Activity activity;


    public CustomListViewAdapter(Activity act, int resource, ArrayList<Contacts> contactsList) {
        super(act, resource, contactsList);
            activity = act;
            layoutResource = resource;
            myContactsList = contactsList;
            notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return myContactsList.size();
    }

    @Override
    public Contacts getItem(int position) {
        return myContactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if(row == null || row.getTag() == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            row = layoutInflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.contactsName = (TextView) row.findViewById(R.id.lrName);
            holder.phoneNumber = (TextView) row.findViewById(R.id.lrPhoneNumber);
            holder.emailID = (TextView) row.findViewById(R.id.lrEmailID);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        holder.contacts = getItem(position);
        holder.contactsName.setText(holder.contacts.getName());
        holder.emailID.setText(holder.contacts.getEmail());
        holder.phoneNumber.setText(holder.contacts.getPhoneNumber());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ContactsDetailsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("ContactsObj", finalHolder.contacts);
                i.putExtras(mBundle);

                activity.startActivity(i);

            }
        });
        return row;
    }
}
