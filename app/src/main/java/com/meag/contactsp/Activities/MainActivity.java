package com.meag.contactsp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import com.meag.contactsp.Adapters.RecyclerContactAdapter;
import com.meag.contactsp.Adapters.RecyclerContactAdapterLand;
import com.meag.contactsp.Methods.Contact_Obtain;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable{
    private RecyclerView rv;
    private RecyclerContactAdapter adapterContact,adapterContactfav;
    private List<Contact> favcontactlist,contactList;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private TabLayout tabLayout;
    private SearchView searchView;
    private Contact_Obtain class_contact =new Contact_Obtain(this);
    RecyclerContactAdapterLand adapterContactland, adapterContactfavLand;
    AppCompatActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {


            contactList = new ArrayList<>();
            contactList = class_contact.findContacts();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                context = this;
                rv = findViewById(R.id.container);
                linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                rv.setLayoutManager(linearLayoutManager);
                adapterContact = new RecyclerContactAdapter(contactList);
                rv.setAdapter(adapterContact);
                searchView = findViewById(R.id.search_view);
                tabLayout = findViewById(R.id.tablayout);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()) {
                            case 0:

                                adapterContact = new RecyclerContactAdapter(contactList);
                                rv.setAdapter(adapterContact);
                                break;
                            case 1:
                                favcontactlist = new ArrayList<>();
                                for (Contact contact : contactList) {
                                    if (contact.isFavmarker()) {
                                        favcontactlist.add(contact);
                                    }
                                }
                                adapterContactfav = new RecyclerContactAdapter(favcontactlist);
                                rv.setAdapter(adapterContactfav);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        switch (tabLayout.getSelectedTabPosition()) {
                            case 0:
                                adapterContact.getFilter().filter(newText);
                                break;
                            case 1:
                                adapterContactfav.getFilter().filter(newText);
                                break;
                        }
                        return false;
                    }
                });


                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CreateContact.class);
                        startActivityForResult(intent, 1);
                    }
                });
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                activity=this;
                rv = findViewById(R.id.container);
                linearLayoutManager = new LinearLayoutManager(context);
                rv.setLayoutManager(linearLayoutManager);
                adapterContactland = new RecyclerContactAdapterLand(this,contactList);
                rv.setAdapter(adapterContactland);
                searchView = findViewById(R.id.search_view);
                tabLayout = findViewById(R.id.tablayout);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()) {
                            case 0:

                                adapterContactland = new RecyclerContactAdapterLand(activity,contactList);
                                rv.setAdapter(adapterContactland);
                                break;
                            case 1:
                                favcontactlist = new ArrayList<>();
                                for (Contact contact : contactList) {
                                    if (contact.isFavmarker()) {
                                        favcontactlist.add(contact);
                                    }
                                }
                                adapterContactfavLand = new RecyclerContactAdapterLand(activity,favcontactlist);
                                rv.setAdapter(adapterContactfavLand);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        switch (tabLayout.getSelectedTabPosition()) {
                            case 0:
                                adapterContactland.getFilter().filter(newText);
                                break;
                            case 1:
                                adapterContactfavLand.getFilter().filter(newText);
                                break;
                        }
                        return false;
                    }
                });


                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CreateContact.class);
                        startActivityForResult(intent, 1);
                    }
                });

//            Bundle bundle=new Bundle();
//            bundle.putSerializable("list", (Serializable) contactList);
//            fMainLandscape=new FMainLandscape();
//            fMainLandscape.setArguments(bundle);
//            FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
//            transition.replace(R.id.frame1,fMainLandscape);
//            transition.addToBackStack(null);
//            transition.commit();
            }
            } else{
            onRestoreInstanceState(savedInstanceState);
        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage(R.string.exit_question)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
////            for (Fragment fragment:getSupportFragmentManager().getFragments()) {
////                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
////            }
//        }
        if(rv!=null){
            Parcelable recyclerstate=rv.getLayoutManager().onSaveInstanceState();
            savedInstanceState.putParcelable("rv",recyclerstate);}

        savedInstanceState.putSerializable("list", (Serializable) contactList);
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LinearLayoutManager.SavedState recyclerstate=savedInstanceState.getParcelable("rv");
        LinearLayoutManager.SavedState recyclerstateland=savedInstanceState.getParcelable("rv");

        contactList = (List<Contact>) savedInstanceState.getSerializable("list");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv = findViewById(R.id.container);
            linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            rv.setLayoutManager(linearLayoutManager);
            adapterContact = new RecyclerContactAdapter(contactList);
            rv.setAdapter(adapterContact);
            searchView = findViewById(R.id.search_view);
            tabLayout = findViewById(R.id.tablayout);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:

                            adapterContact = new RecyclerContactAdapter(contactList);
                            rv.setAdapter(adapterContact);
                            break;
                        case 1:
                            favcontactlist = new ArrayList<>();
                            for (Contact contact : contactList) {
                                if (contact.isFavmarker()) {
                                    favcontactlist.add(contact);
                                }
                            }
                            adapterContactfav = new RecyclerContactAdapter(favcontactlist);
                            rv.setAdapter(adapterContactfav);
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    switch (tabLayout.getSelectedTabPosition()) {
                        case 0:
                            adapterContact.getFilter().filter(newText);
                            break;
                        case 1:
                            adapterContactfav.getFilter().filter(newText);
                            break;
                    }
                    return false;
                }
            });

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CreateContact.class);
                    startActivityForResult(intent, 1);
                }
            });

            if( recyclerstate != null) rv.getLayoutManager().onRestoreInstanceState(recyclerstate);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity=this;
            rv = findViewById(R.id.container);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv.setLayoutManager(linearLayoutManager);
            adapterContactland = new RecyclerContactAdapterLand(activity,contactList);
            rv.setAdapter(adapterContactland);
            searchView = findViewById(R.id.search_view);
            tabLayout = findViewById(R.id.tablayout);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:

                            adapterContactland = new RecyclerContactAdapterLand(activity,contactList);
                            rv.setAdapter(adapterContactland);
                            break;
                        case 1:
                            favcontactlist = new ArrayList<>();
                            for (Contact contact : contactList) {
                                if (contact.isFavmarker()) {
                                    favcontactlist.add(contact);
                                }
                            }
                            adapterContactfavLand = new RecyclerContactAdapterLand(activity,favcontactlist);
                            rv.setAdapter(adapterContactfavLand);
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    switch (tabLayout.getSelectedTabPosition()) {
                        case 0:
                            adapterContactland.getFilter().filter(newText);
                            break;
                        case 1:
                            adapterContactfavLand.getFilter().filter(newText);
                            break;
                    }
                    return false;
                }
            });


            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CreateContact.class);
                    startActivityForResult(intent, 1);
                }
            });

            if( recyclerstate != null) rv.getLayoutManager().onRestoreInstanceState(recyclerstate);
        }







    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check that it is the SecondActivity with an OK result
        if(requestCode==1) {
            if (resultCode== RESULT_OK) {
                String newid = data.getStringExtra("id");
                ArrayList<String> newemail = new ArrayList();
                ArrayList<String> newname = new ArrayList();
                LinkedHashMap newphone = new LinkedHashMap();

                newname.add(data.getStringExtra("name"));
                newemail.add(data.getStringExtra("email"));
                newphone.put("as", data.getStringExtra("phone"));

                String newaddress = data.getStringExtra("address");
                Boolean newfav = Boolean.parseBoolean(data.getStringExtra("fav"));

                contactList.add(new Contact(newid, newname, newemail, newphone, newaddress, newfav, Calendar.getInstance().getTime(), ""));
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                Toast.makeText(context, newid, Toast.LENGTH_SHORT).show();
                adapterContact.notifyItemInserted(contactList.size());
                adapterContact.notifyDataSetChanged();
            }
            }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                adapterContactland.notifyItemInserted(contactList.size());
                adapterContactland.notifyDataSetChanged();
            }
        }else if(requestCode==0){
            if(resultCode==2){
                int index= (int) data.getSerializableExtra("remove_contact_index");
                contactList.remove(index);
                adapterContact.contactlist.remove(index);
                adapterContact.notifyItemRemoved(index);
                adapterContact.notifyDataSetChanged();
                if(adapterContactfav!=null) {

                    adapterContactfav.notifyItemRemoved(index);
                    adapterContactfav.notifyDataSetChanged();
                }
            }
        }
    }

    public List<Contact> getFavcontactlist() {
        return favcontactlist;
    }

    public void setFavcontactlist(List<Contact> favcontactlist) {
        this.favcontactlist = favcontactlist;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public RecyclerContactAdapter getAdapterContact() {
        return adapterContact;
    }


    public RecyclerContactAdapterLand getAdapterContactland() {
        return adapterContactland;
    }

    public void setAdapterContactland(RecyclerContactAdapterLand adapterContactland) {
        this.adapterContactland = adapterContactland;
    }

    public RecyclerContactAdapterLand getAdapterContactfavLand() {
        return adapterContactfavLand;
    }

    public void setAdapterContactfavLand(RecyclerContactAdapterLand adapterContactfavLand) {
        this.adapterContactfavLand = adapterContactfavLand;
    }
}



