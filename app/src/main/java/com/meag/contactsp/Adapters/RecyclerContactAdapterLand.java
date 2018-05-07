package com.meag.contactsp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meag.contactsp.FMainLandscape;
import com.meag.contactsp.Methods.ContactFilterLand;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.io.Serializable;
import java.util.List;

public class RecyclerContactAdapterLand extends RecyclerView.Adapter<RecyclerContactAdapterLand.ContactViewHolder> implements Filterable {
    Context context;
    ContactFilterLand filter;
    public List<Contact> contactlist;
    public List<Contact> filteredlist;

    FMainLandscape fMainLandscape;
    AppCompatActivity activity;

    public RecyclerContactAdapterLand(AppCompatActivity activity,List<Contact> contactlist) {
        this.context = context;
        this.contactlist = contactlist;
        this.filteredlist = contactlist;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.contactcardviewlandscape, parent,false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerContactAdapterLand.ContactViewHolder holder, final int position) {
        final ImageButton favstar=holder.btnfav;
        holder.name.setText(contactlist.get(position).getName().get(0));

        if(contactlist.get(position).getImg()!=null) {
            holder.img.setImageURI(Uri.parse(contactlist.get(position).getImg()));
        }else{
            holder.img.setImageResource(R.mipmap.person);
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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(v.getContext(),Description_Contact.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("list", (Serializable) contactlist.get(position));
                fMainLandscape=new FMainLandscape();
                fMainLandscape.setArguments(bundle);
                FragmentTransaction transaction= activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame2, fMainLandscape).commit();
//
//                    intent.putExtra("contactl", contactlist.get(position));
//
//                    ((Activity) v.getContext()).startActivityForResult(intent, 0);
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
        filter = new ContactFilterLand(this, contactlist);
        }
        return filter;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        ImageButton btnfav;
        LinearLayout linearLayout;

        public ContactViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardname_textviewland);

            img = itemView.findViewById(R.id.cardpicture_imageviewland);
            btnfav = itemView.findViewById(R.id.btnfavland);
            linearLayout=itemView.findViewById(R.id.cardviewlinearl);

        }
    }

//    public abstract void abstras(Contact c);
}
