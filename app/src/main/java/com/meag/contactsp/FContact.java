package com.meag.contactsp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class FContact extends Fragment {

    View view;
    RecyclerView rv;
    RecyclerContactAdapter adapter;
    ArrayList <Contact> contactList;
    LinearLayoutManager linearLayoutManager;
    OnFragmentInteractionListener mListener;

    public FContact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view=inflater.inflate(R.layout.fragment_contact, container, false);
        rv= view.findViewById(R.id.recyclerviewcontact);
        rv.setHasFixedSize(true);
        linearLayoutManager= new GridLayoutManager(getContext(),3);
        rv.setLayoutManager(linearLayoutManager);
        adapter=new RecyclerContactAdapter(contactList);
        rv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public List<Contact> getContactList() {
        String name;
        List<String> emails= new ArrayList<>(), numbers = new ArrayList<>();
        List<Contact> contactlist = new ArrayList<>();
        Uri image;
        String id=new String();

        // CURSOR PARAMS
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //SET CURSOR
        Cursor phones = getActivity().getContentResolver().query(uri, null, null, null, sort);
        //MOVING
        while (phones.moveToNext()) {

            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String nav = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
            String Strt = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
            String Cty = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            String cntry = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
            String address= Strt+Cty+cntry;
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
        Cursor cur1 = getActivity().getContentResolver().query(
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
        Cursor pCur = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
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


}
