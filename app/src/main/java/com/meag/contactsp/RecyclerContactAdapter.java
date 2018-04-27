package com.meag.contactsp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view;
//        view= LayoutInflater.from(context).inflate(R.layout.it)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        public ContactViewHolder(View itemView){
            super(itemView);
        }
    }



}
