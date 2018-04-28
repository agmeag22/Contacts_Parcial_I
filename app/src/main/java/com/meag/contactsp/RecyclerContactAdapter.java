package com.meag.contactsp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder> {

    Context context;
    List<Contact> contactList;


    public RecyclerContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.contactcardview, parent,false);
        ContactViewHolder viewHolder= new ContactViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.name.setText(contactList.get(position).getName());
        holder.img.setImageResource(contactList.get(position).getImg());

        if(contactList.get(position).isFavmarker()) {
            holder.btnfavstar.setImageResource(R.drawable.ic_favorite);
        }
        else{
            holder.btnfavstar.setImageResource(R.drawable.ic_favoriteempty);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView img;
        ImageView btnfavstar;

        public ContactViewHolder(View itemView){
            super(itemView);
            name= itemView.findViewById(R.id.cardname_textview);
            img=  itemView.findViewById(R.id.cardpicture_imageview);
            btnfavstar = itemView.findViewById(R.id.btnfav);

            btnfavstar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!
                }
            });
        }


    }



}
