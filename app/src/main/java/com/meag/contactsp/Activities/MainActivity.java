package com.meag.contactsp.Activities;

import android.Manifest;
import android.content.Context;
//add dialogs
import android.content.DialogInterface;
//add intent
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.karan.churi.PermissionManager.PermissionManager;
import com.meag.contactsp.Adapters.RecyclerContactAdapter;
import com.meag.contactsp.Adapters.RecyclerContactAdapterLand;
import com.meag.contactsp.Methods.Contact_Obtain;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable{
    PermissionManager permissionManager;
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
    int index;
    private boolean b_edited_contact=false;
ArrayList<String> denied;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionManager = new PermissionManager() {
            };
            permissionManager.checkAndRequestPermissions(this);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
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


                    ImageView fab = findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, CreateContact.class);
                            startActivityForResult(intent, 1);
                        }
                    });
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    activity = this;
                    rv = findViewById(R.id.container);
                    linearLayoutManager = new LinearLayoutManager(context);
                    rv.setLayoutManager(linearLayoutManager);
                    adapterContactland = new RecyclerContactAdapterLand(this, contactList);
                    rv.setAdapter(adapterContactland);
                    searchView = findViewById(R.id.search_view);
                    tabLayout = findViewById(R.id.tablayout);
                    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            switch (tab.getPosition()) {
                                case 0:

                                    adapterContactland = new RecyclerContactAdapterLand(activity, contactList);
                                    rv.setAdapter(adapterContactland);
                                    break;
                                case 1:
                                    favcontactlist = new ArrayList<>();
                                    for (Contact contact : contactList) {
                                        if (contact.isFavmarker()) {
                                            favcontactlist.add(contact);
                                        }
                                    }
                                    adapterContactfavLand = new RecyclerContactAdapterLand(activity, favcontactlist);
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


                    ImageView fab = findViewById(R.id.fab);
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
            } else {
                onRestoreInstanceState(savedInstanceState);
            }


        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.question)
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

            ImageView fab = findViewById(R.id.fab);
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
                            getfavorites();
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


            ImageView fab = findViewById(R.id.fab);
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
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Contact new_contact = (Contact) data.getSerializableExtra("new_contact");
                contactList.add(new_contact);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                Toast.makeText(context, newid, Toast.LENGTH_SHORT).show();
                    // adapterContact.contactlist.add(new_contact);
                    adapterContact.contactlist = contactList;
                    adapterContact.notifyItemInserted(contactList.size());
                    adapterContact.notifyDataSetChanged();
                    if (tabLayout.getSelectedTabPosition() == 1) {
                        adapterContactfav.contactlist = getfavorites();
                        adapterContactfav.notifyDataSetChanged();
                    }
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // adapterContactland.contactlist.add(new_contact);
                    adapterContactland.notifyItemInserted(contactList.size());
                    adapterContactland.notifyDataSetChanged();
                }
            }

        } else if (requestCode == 0) {
            if (resultCode == 2) {
                int index = -1;
                if (tabLayout.getSelectedTabPosition() == 1) {
                    index = (int) data.getIntExtra("remove_contact_index", -1);
                    int index2 = contactList.indexOf(adapterContactfav.contactlist.get(index));
                    adapterContactfav.contactlist.remove(index);
                    adapterContactfav.notifyItemRemoved(index);
                    adapterContactfav.notifyDataSetChanged();


                    contactList.remove(index2);
                } else {
                    index = (int) data.getIntExtra("remove_contact_index", -1);
                    contactList.remove(index);
                    adapterContact.notifyItemRemoved(index);
                    adapterContact.notifyDataSetChanged();
                }
            }
            if (resultCode == 3) {
                int index = -1;
                Contact edited_contact = (Contact) data.getSerializableExtra("edited_contact");
                if (tabLayout.getSelectedTabPosition() == 1) {
                    index = (int) data.getIntExtra("index", -1);
                    int index2 = contactList.indexOf(adapterContactfav.contactlist.get(index));
                    adapterContactfav.contactlist.set(index, edited_contact);
                    adapterContactfav.notifyItemChanged(index);
                    contactList.set(index2, edited_contact);
                } else {
                    index = (int) data.getIntExtra("index", -1);
                    contactList.set(index, edited_contact);
                    adapterContact.notifyItemChanged(index);
                }
            }
        }

        if(requestCode == 3) {
            if (resultCode == -1) {
                index = -1;
                Contact edited_contact = (Contact) data.getSerializableExtra("new_contact");
                if (tabLayout.getSelectedTabPosition() == 1) {
                    index = (int) data.getIntExtra("index", -1);
                    int index2 = adapterContactfavLand.contactlist.indexOf(contactList.get(index));
                    adapterContactfavLand.contactlist.set(index2, edited_contact);
                    adapterContactfavLand.notifyItemChanged(index2);
                    contactList.set(index, edited_contact);
                     b_edited_contact=  true;
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("list", (Serializable) contactList.get(index));
//                    FMainLandscape fMainLandscape=new FMainLandscape();
//                    fMainLandscape.setArguments(bundle);
//                    FragmentTransaction transaction= activity.getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame2, fMainLandscape).commit();
                } else {
                    index = (int) data.getIntExtra("index", -1);
                    contactList.set(index, edited_contact);
                    adapterContactland.notifyItemChanged(index);
                    b_edited_contact=true;
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("list", (Serializable) contactList.get(index));
//                    FMainLandscape fMainLandscape=new FMainLandscape();
//                    fMainLandscape.setArguments(bundle);
//                    FragmentTransaction transaction= activity.getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame2, fMainLandscape).commit();

                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(b_edited_contact) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", (Serializable) contactList.get(index));
            FMainLandscape fMainLandscape = new FMainLandscape();
            fMainLandscape.setArguments(bundle);
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame2, fMainLandscape).commit();
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
    public List<Contact> getfavorites() {
        favcontactlist = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.isFavmarker()) {
                favcontactlist.add(contact);
            }
        }
        return favcontactlist;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);
        denied=new ArrayList<>();
        denied= permissionManager.getStatus().get(0).denied;


    }
}