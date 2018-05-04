package com.meag.contactsp;

import android.widget.Filter;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactFilter extends Filter implements Serializable {
    RecyclerContactAdapter adapter;
    List<Contact> filterList;


    public ContactFilter(RecyclerContactAdapter adapter, List<Contact> filterList) {
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
                if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredContacts.add(filterList.get(i));
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
