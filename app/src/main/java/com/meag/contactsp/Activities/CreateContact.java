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

import com.meag.contactsp.R;

import java.util.ArrayList;

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
    Boolean favmark;
    String sid;
    String sname;
    String semail;
    String saddress;
    String sphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        id=findViewById(R.id.edit_text_id);
        name= findViewById(R.id.edit_text_name);
        lastname= findViewById(R.id.edit_text_last_name);
        email= findViewById(R.id.edit_text_email);
        address= findViewById(R.id.edit_text_address);
        btnback=findViewById(R.id.btnback);
        phone=findViewById(R.id.edit_text_phone);
        btnadd=findViewById(R.id.btnadd);
        btnfav=findViewById(R.id.favchecker);
        image=Uri.parse("res:///" + R.mipmap.person);
        favmark=false;


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
                sid=id.getText().toString();
                sname=name.getText().toString()+" "+lastname.getText().toString();
                semail=String.valueOf(email.getText());
                saddress=String.valueOf(address.getText());
                sphone=phone.getText().toString();
                btnfav.setImageResource(R.drawable.ic_favoriteempty);

                Intent intent= new Intent();
                intent.putExtra("id",sid);
                intent.putExtra("name",sname);
                intent.putExtra("email",semail);
                intent.putExtra("phone",sphone);
                intent.putExtra("addrress",saddress);
                intent.putExtra("fav",favmark.toString());
                intent.putExtra("image",image.toString());

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
