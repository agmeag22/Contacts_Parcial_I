package com.meag.contactsp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CreateContact extends AppCompatActivity {
    ImageButton btnback;
    ImageView photo;
    EditText name;
    EditText lastname;
    EditText email;
    EditText address;
    EditText phone;
    EditText id;
    Button btnadd;
    ImageButton btnfav;
    Uri image;
    Boolean favmark,imagebool;
    String sid;
    String sname;
    String semail;
    String saddress;
    String sphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        name= findViewById(R.id.edit_text_name);
        lastname= findViewById(R.id.edit_text_last_name);
        email= findViewById(R.id.edit_text_email);
        address= findViewById(R.id.edit_text_address);
        btnback=findViewById(R.id.btnback);
        photo=findViewById(R.id.create_imageview_descrpition);
        phone=findViewById(R.id.edit_text_phone);
        btnadd=findViewById(R.id.btnadd);
        btnfav=findViewById(R.id.favchecker);
        favmark=false;
        imagebool=false;

        if(imagebool){
            //photo.setImageResource();
        }else{
            photo.setImageResource(R.drawable.ic_personbig);
            image=null;
        }

        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!favmark){
                    btnfav.setImageResource(R.drawable.ic_favoritefull);
                    favmark=true;
                }else{
                    btnfav.setImageResource(R.drawable.ic_favoriteempty);
                    favmark=false;
                }
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sid="0";
                sname=name.getText().toString()+" "+lastname.getText().toString();
                semail=String.valueOf(email.getText());
                saddress=String.valueOf(address.getText());
                sphone=phone.getText().toString();
                ArrayList<String> arrayname=new ArrayList<>();
                ArrayList<String> arrayemail=new ArrayList<>();
                LinkedHashMap<String,String> hashMapphone=new LinkedHashMap<>();
                arrayname.add(sname);
                arrayemail.add(semail);
                hashMapphone.put("phone",sphone);
                Contact contact=new Contact();
                contact.setId(sid);
                contact.setName(arrayname);
                contact.setEmail(arrayemail);
                contact.setAddress(saddress);
                contact.setPhone(hashMapphone);
                contact.setFavmarker(favmark);
                Intent intent= new Intent();
                intent.putExtra("new_contact",contact);


                setResult(RESULT_OK,intent);
                finish();


            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();

            }
        });


    }

}
