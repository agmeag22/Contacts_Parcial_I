package com.meag.contactsp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder> {

    private List<Contact> contactlist;

    public RecyclerContactAdapter(List<Contact> contactlist) {
        this.contactlist = contactlist;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.contactcardview, parent,false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerContactAdapter.ContactViewHolder holder, final int position) {
        final ImageButton favstar=holder.btnfav;
        holder.name.setText(contactlist.get(position).getName());
        if(contactlist.get(position).getImg()!=null) {
            holder.img.setImageURI(contactlist.get(position).getImg());
        }else{
            holder.img.setImageResource(R.drawable.ic_person);
        }
        if(contactlist.get(position).isFavmarker()) {
            holder.btnfav.setImageResource(R.drawable.ic_favoritefull);
        }
        else{
            holder.btnfav.setImageResource(R.drawable.ic_favoriteempty);
        }

        holder.btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!contactlist.get(position).isFavmarker()){
                    contactlist.get(position).setFavmarker(true);
                    favstar.setImageResource(R.drawable.ic_favoritefull);
                }else{
                    contactlist.get(position).setFavmarker(false);
                    favstar.setImageResource(R.drawable.ic_favoriteempty);
                }
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Description_Contact.class);
                Uri uri=contactlist.get(position).getImg();
                if(uri!=null) {
//                    Uri.parse("res:///" + R.drawable.ic_person);
                    intent.putExtra("image",uri.toString());
                }
                intent.putExtra("name",contactlist.get(position).getName());
                intent.putExtra("email",contactlist.get(position).getEmail());
                intent.putExtra("phone",contactlist.get(position).getPhone());
                intent.putExtra("address",contactlist.get(position).getAddress());

                ((Activity) v.getContext()).startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        ImageButton btnfav;

        public ContactViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardname_textview);
            img = itemView.findViewById(R.id.cardpicture_imageview);
            btnfav = itemView.findViewById(R.id.btnfav);

        }
    }
//    public abstract void abstras(Contact c);
}
