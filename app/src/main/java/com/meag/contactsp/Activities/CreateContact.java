package com.meag.contactsp.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meag.contactsp.ImageURI;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CreateContact extends AppCompatActivity {
    ImageButton btnback,changeimage;
    ImageView photo;
    EditText name;
    EditText lastname;
    EditText email;
    EditText address;
    EditText phone;
    EditText birthday;
    Button btnadd;
    ImageButton btnfav;
    Uri image;
    Boolean favmark,imagebool;
    String sid;
    String sname;
    String semail;
    String saddress;
    String sphone;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        findviews();
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePickerDialog();
            }
        });
        favmark=false;
        photo.setImageResource(R.drawable.ic_personbig);
        if(getIntent().getSerializableExtra("contacto")!=null){
            contact= (Contact) getIntent().getSerializableExtra("contacto");
            settextviews();
        }else{
            contact=new Contact();
        }

//        if(imagebool){
//            //photo.setImageResource();
//        }else{
//            photo.setImageResource(R.drawable.ic_personbig);
//            image=null;
//        }
        clicklisteners();


    }

    public void clicklisteners(){
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
                contact=setValues();
                Intent intent= new Intent();
                intent.putExtra("new_contact",contact);
                setResult(Activity.RESULT_OK,intent);
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
        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateContact.this, getString(R.string.changetoselect), Toast.LENGTH_SHORT).show();
                selectImage();
            }
        });

    }
    public void findviews(){
        name= findViewById(R.id.edit_text_name);
        lastname= findViewById(R.id.edit_text_last_name);
        email= findViewById(R.id.edit_text_email);
        address= findViewById(R.id.edit_text_address);
        btnback=findViewById(R.id.btnback);
        photo=findViewById(R.id.create_imageview_descrpition);
        phone=findViewById(R.id.edit_text_phone);
        btnadd=findViewById(R.id.btnadd);
        btnfav=findViewById(R.id.favchecker);
        changeimage=findViewById(R.id.btnchangeimage);
        birthday=findViewById(R.id.edit_text_birthday);
    }
    public Contact setValues(){
        Contact contact=new Contact();
        ArrayList<String> arrayname=new ArrayList<>();
        ArrayList<String> arrayemail=new ArrayList<>();
        LinkedHashMap<String,String> hashMapphone=new LinkedHashMap<>();
        arrayname.add(sname);
        arrayemail.add(semail);
        hashMapphone.put("phone",sphone);

        contact.setId(sid);
        contact.setName(arrayname);
        contact.setEmail(arrayemail);
        contact.setAddress(saddress);
        contact.setPhone(hashMapphone);
        contact.setFavmarker(favmark);
        return contact;
    }
    public void settextviews(){
        if(contact.getName().size()>0){
        name.setText(contact.getName().get(0));
        }
        if(contact.getEmail().size()>0){
            email.setText(contact.getEmail().get(0));
        }
        if(contact.getAddress()!=null){
            address.setText(contact.getAddress());
        }
        if(contact.getPhone().size()>0){
            phone.setText((new ArrayList<String> (contact.getPhone().values())).get(0));
        }

    }
    private void selectImage() {
        final CharSequence[] items = {
                getString(R.string.selectphotog),
                getString(R.string.cancel)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_selection));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals(getString(R.string.selectphoto))) {
//                    File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, 8);  }else{
                 if (items[item].equals(getString(R.string.selectphotog))) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, getString(R.string.title_selection)),
                            8);
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 8 && resultCode == RESULT_OK) {
            Uri selectedImageURI = data.getData();
            contact.setImg(selectedImageURI.toString());

            Glide.with(this).load(new File(ImageURI.getRealPathFromURI(this, selectedImageURI))).apply(RequestOptions.overrideOf(150, 150)).into(photo);
            photo.setImageURI(selectedImageURI);
            Toast.makeText(this, getString(R.string.selectionmade), Toast.LENGTH_SHORT).show();
        }
    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image
//private void showDatePickerDialog() {
//    DatePickerFragment newFragment = new DatePickerFragment();
//    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker")
//}
}
