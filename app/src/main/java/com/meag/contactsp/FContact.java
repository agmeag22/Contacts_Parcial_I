package com.meag.contactsp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        prepareContacts();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void prepareContacts(){
        contactList=new ArrayList<>();
        contactList.add(new Contact("Miguel","miguelere","77725","sgghjk",true, R.drawable.ic_contact));

    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }
    public void setContactList(ArrayList<Contact> contactList){
        this.contactList=contactList;
    }

}
