package com.meag.contactsp;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Description_Contact extends AppCompatActivity {

    private ImageButton btnback;
    ImageView img;
    TextView name;
    TextView email;
    TextView phone;
    TextView address;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description__contact);
        Bundle bundle=getIntent().getExtras();
        img= findViewById(R.id.cardpicture_imageview_descrpition);
        name=findViewById(R.id.nametext);
        email=findViewById(R.id.emailtext);
        phone=findViewById(R.id.phonetext);
        address=findViewById(R.id.addresstext);
        btnback=findViewById(R.id.btnback);



        if(bundle.getString("image")!=null){
            Uri uri=Uri.parse(bundle.getString("image"));
            img.setImageURI(uri);
        }else{
            img.setImageResource(R.drawable.person);
        }
        if(bundle.getCharSequence("name")!=null){
        name.setText(bundle.getCharSequence("name"));}
        else {name.setText("-");}
        if(bundle.getCharSequence("email")!=null){
        email.setText(bundle.getCharSequence("email"));}
        else {email.setText("-");}
        if(bundle.getCharSequence("phone")!=null){
        phone.setText(bundle.getCharSequence("phone"));}
        else {email.setText("-");}
        if(bundle.getCharSequence("address")!=null){
        address.setText(bundle.getCharSequence("address"));}
        else {email.setText("-");}


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                setResult(0);
            }
        });
    }
}