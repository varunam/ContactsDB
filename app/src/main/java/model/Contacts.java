package model;

import java.io.Serializable;

/**
 * Created by vaam on 03-03-2017.
 */
public class Contacts implements Serializable {

    private String Name, Email;
    private int ContactsID;
    private String PhoneNumber;

    public Contacts(String MainName, String PhoneNumb, int ID, String emailID)
    {
        Name = MainName;
        PhoneNumber = PhoneNumb;
        Email = emailID;
        ContactsID = ID;
    }

    public Contacts() {

    }

    public int getContactsID() {
        return ContactsID;
    }

    public void setContactsID(int contactsID) {
        ContactsID = contactsID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
