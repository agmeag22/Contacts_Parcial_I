package com.meag.contactsp.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.meag.contactsp.Activities.Description_Contact;
import com.meag.contactsp.Methods.ContactFilterLand;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

import java.util.List;

public class RecyclerContactAdapterLand extends RecyclerView.Adapter<RecyclerContactAdapterLand.ContactViewHolder> implements Filterable {
    Context context;
    ContactFilterLand filter;
    public List<Contact> contactlist;
    public List<Contact> filteredlist;

    public RecyclerContactAdapterLand(List<Contact> contactlist) {
        this.context = context;
        this.contactlist = contactlist;
        this.filteredlist = contactlist;
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
            holder.img.setImageResource(R.drawable.ic_person);
        }
        if(contactlist.get(position).isFavmarker()) {
            holder.btnfav.setImageResource(R.drawable.ic_favoritefull);
        }
        else{
            holder.btnfav.setImageResource(R.drawable.ic_favoriteempty);
        }
        if(contactlist.get(position).getPhone().size()>0){
            holder.phone.setText(contactlist.get(position).getPhone().get(0));
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

//
                    intent.putExtra("contactl", contactlist.get(position));

                    ((Activity) v.getContext()).startActivityForResult(intent, 0);
            }
        });
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactlist.get(position).getPhone().get(0)));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
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
        TextView phone;
        ImageView img;
        ImageButton btnfav;
        ImageButton btncall;

        public ContactViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardname_textviewland);
            phone = itemView.findViewById(R.id.cardphone_textviewland);

            img = itemView.findViewById(R.id.cardpicture_imageviewland);
            btnfav = itemView.findViewById(R.id.btnfavland);
            btncall=itemView.findViewById(R.id.btncallland);

        }
    }
//    public abstract void abstras(Contact c);
}
