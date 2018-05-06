package com.meag.contactsp.Methods;

import android.widget.Filter;

import com.meag.contactsp.Adapters.RecyclerContactAdapter;
import com.meag.contactsp.Adapters.RecyclerContactAdapterLand;
import com.meag.contactsp.Objects.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactFilterLand extends Filter implements Serializable {
    RecyclerContactAdapterLand adapter;
    List<Contact> filterList;


    public ContactFilterLand(RecyclerContactAdapterLand adapter, List<Contact> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Contact> filteredContacts = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK IF ONLY FAVS
                if (filterList.get(i).getName().get(0) != null) {
                    if (filterList.get(i).getName().get(0).toUpperCase().contains(constraint)) {
                        //ADD PLAYER TO FILTERED PLAYERS
                        filteredContacts.add(filterList.get(i));
                    }
                }
            }
            results.count = filteredContacts.size();
            results.values = filteredContacts;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.contactlist = (List<Contact>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }


}
