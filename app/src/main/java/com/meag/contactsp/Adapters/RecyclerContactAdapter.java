package com.meag.contactsp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.meag.contactsp.Methods.ContactFilter;
import com.meag.contactsp.Activities.Description_Contact;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.util.List;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder> implements Filterable {

    ContactFilter filter;
    public List<Contact> contactlist;
    public List<Contact> filteredlist;

    public RecyclerContactAdapter(List<Contact> contactlist) {
        this.contactlist = contactlist;
        this.filteredlist=contactlist;
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
        holder.name.setText(contactlist.get(position).getName().get(0));
        if(contactlist.get(position).getImg()!=null) {
            holder.img.setImageURI(Uri.parse(contactlist.get(position).getImg()));
        }else{
            holder.img.setImageResource(R.drawable.ic_person);
        }
        if(contactlist.get(position).isFavmarker()) {
            holder.btnfav.setImageResource(R.drawable.ic_favoritefull);
        }
        else{
            holder.btnfav.setImageResource(R.drawable.ic_favoriteempty);
        }

        holder.btnfav.setOnClickListener( new View.OnClickListener() {
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
                    int index= position;

                    intent.putExtra("index",index);
                    intent.putExtra("contactl", contactlist.get(position));

                    ((Activity) v.getContext()).startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ContactFilter(this, contactlist);
        }
        return filter;
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
