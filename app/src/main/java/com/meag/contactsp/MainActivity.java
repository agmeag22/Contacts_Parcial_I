package com.meag.contactsp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private RecyclerContactAdapter adapterContact;
    private RecyclerContactAdapter adapterContactfav;
    private List<Contact> favcontactlist;
    private LinearLayoutManager linearLayoutManager;
    private List<Contact> contactList;
    private ImageButton buttonselected;
    private ImageButton buttonnonselected;
    private boolean button;
    private FloatingActionButton btnaddcontact;
    private Context context;
    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=new ArrayList<>();
        contactList=fill_list();
        rv = findViewById(R.id.container);





        linearLayoutManager=new GridLayoutManager(getApplicationContext(),3);
        rv.setLayoutManager(linearLayoutManager);

        adapterContact=new RecyclerContactAdapter(contactList);
        rv.setAdapter(adapterContact);

        button=false;
        buttonselected= findViewById(R.id.tabselected);
        buttonnonselected= findViewById(R.id.tabnonselected);
        buttonselected.setImageResource(R.drawable.ic_contact);
        buttonnonselected.setImageResource(R.drawable.ic_favorite);

        buttonnonselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button==false){
                    superior_to_inferior();
                    button=true;
                    favcontactlist=new ArrayList<>();
                    for (Contact contact : contactList) {
                        if (contact.isFavmarker()) {
                            favcontactlist.add(contact);
                        }
                    }
                    adapterContactfav=new RecyclerContactAdapter(favcontactlist);
                    rv.setAdapter(adapterContactfav);



                }else {
                    inferior_to_superior();
                    button = false;
                    adapterContact=new RecyclerContactAdapter(contactList);
                    rv.setAdapter(adapterContact);
                }

                }


        });
//        btnaddcontact=findViewById(R.id.btnaddcontact);
//        btnaddcontact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



    }
    public void superior_to_inferior(){
        buttonselected.setImageResource(R.drawable.ic_favorite);
        buttonnonselected.setImageResource(R.drawable.ic_contact);
        buttonnonselected.setBackgroundResource(R.color.tabsuperior);
        buttonselected.setBackgroundResource(R.color.tabinferior);
    }
    public void inferior_to_superior(){
        buttonselected.setImageResource(R.drawable.ic_contact);
        buttonnonselected.setImageResource(R.drawable.ic_favorite);
        buttonnonselected.setBackgroundResource(R.color.tabinferior);
        buttonselected.setBackgroundResource(R.color.tabsuperior);
    }

    public List<Contact> fill_list(){
        String name;
        List<String> emails= new ArrayList<>(), numbers = new ArrayList<>();
        List<Contact> contactlist = new ArrayList<>();
        Uri image;
        String id=new String();

        // CURSOR PARAMS
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //SET CURSOR
        Cursor phones = this.getContentResolver().query(uri, null, null, null, sort);
        //MOVING
        while (phones.moveToNext()) {
            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String nav = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
            String Strt = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
            String Cty = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            String cntry = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
            String address=null;
            if(Strt!=null && Cty!=null && cntry!=null ) {
                address = Strt + "" + Cty + "" + cntry;
            }
            if (nav != null) {
                image = Uri.parse(nav);
            } else image = null;


            numbers = getPhoneNumbers(id);

            emails = getEmails(id);

            //Si la columna starred tiene 1 es que el contacto del telefono es favorito
            boolean fav = (phones.getString(phones.getColumnIndex(ContactsContract.Data.STARRED))).equals("1");
            contactlist.add(new Contact(id,name,emails.toString(), numbers.toString(),address,fav,image));

            Log.d("TAM", "findContacts: "+ contactlist.size());
        }
        phones.close();
        return contactlist;
    }

    public List<String> getEmails(String id){
        List<String> emails = new ArrayList<>();
        Cursor cur1 = this.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);
        while (cur1.moveToNext()) {
            //to get the contact names
            // String email2 = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
            String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

            emails.add(email);
        }
        // Log.d( "emails-size: " , emails.size() + "");
        cur1.close();
        return emails;
    }
    public List<String> getPhoneNumbers(String id){
        List<String> numbers = new ArrayList<>();
        Cursor pCur = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
        while (pCur.moveToNext()) {
            String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            numbers.add(contactNumber);
            //Log.d("NUMBER_SIZE_INTERNO", "findContacts: " + numbers.size());
            break;
        }
        //Log.d("NUMBER_SIZE", "findContacts: " + numbers.size());
        pCur.close();
        return numbers;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        buttonnonselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button==false){
                    superior_to_inferior();
                    button=true;
                    favcontactlist=new ArrayList<>();
                    for (Contact contact : contactList) {
                        if (contact.isFavmarker()) {
                            favcontactlist.add(contact);
                        }
                    }
                    adapterContactfav=new RecyclerContactAdapter(favcontactlist);
                    rv.setAdapter(adapterContactfav);



                }else {
                    inferior_to_superior();
                    button = false;
                    adapterContact=new RecyclerContactAdapter(contactList);
                    rv.setAdapter(adapterContact);
                }

            }


        });
//        btnaddcontact=findViewById(R.id.btnaddcontact);
//        btnaddcontact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
