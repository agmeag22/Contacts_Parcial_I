package com.meag.contactsp.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.meag.contactsp.Adapters.PhoneAdapter;
import com.meag.contactsp.Adapters.RecyclerContactAdapter;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.util.ArrayList;

public class Description_Contact extends AppCompatActivity {

    private ImageButton btnback;
    private RecyclerView rv;
    private PhoneAdapter phoneAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ImageView img;
    private TextView name;
    TextView birthday;
    private TextView phone;
    private TextView address;
    private Bundle bundle;
    private Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description__contact);
        Bundle bundle=getIntent().getExtras();
        rv=findViewById(R.id.phonerecyclerview);
        img= findViewById(R.id.description_image_view);
        name=findViewById(R.id.name_text);
        birthday=findViewById(R.id.birthday_text);
//        address=findViewById(R.id.addresstext);
        btnback=findViewById(R.id.btnback);
        linearLayoutManager= new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        if(bundle.getSerializable("contactl")!=null){
            contact=(Contact) bundle.getSerializable("contactl");

            if(contact.getImg()!=null){
                Uri uri=Uri.parse(contact.getImg());
                img.setImageURI(uri);
            }else{
                img.setImageResource(R.drawable.ic_person);
            }
            if(contact.getName().size()>0){
                name.setText(contact.getName().get(0));}
            else {name.setText("-");}
//            if(contact.getEmail().size()>0){
//                email.setText(contact.getEmail().get(0));}
//            else {email.setText("-");}
            if(contact.getPhone().size()>0) {
            String phonestring=new ArrayList<String>(contact.getPhone().values()).get(0);
            phoneAdapter=new PhoneAdapter(this,contact.getPhone());
            rv.setAdapter(phoneAdapter);
            }
            else {phone.setText("-");}
//            if(contact.getAddress()!=null){
//                address.setText(contact.getAddress());}
//            else {address.setText("-");}
            if(contact.getBirthdate()!=null){
                birthday.setText(contact.getBirthdate().toString());
            }
        }



//

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                setResult(0);
            }
        });
    }
}