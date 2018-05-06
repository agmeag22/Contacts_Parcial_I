package com.meag.contactsp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meag.contactsp.Adapters.RecyclerContactAdapterLand;
import com.meag.contactsp.Objects.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FMainLandscape.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FMainLandscape#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FMainLandscape extends Fragment {
    RecyclerView recyclerView;
    private List<Contact> favcontactlist,contactList;
    private LinearLayoutManager linearLayoutManager;
    TabLayout tabLayout;
    private SearchView searchView;
    private RecyclerContactAdapterLand adapterContactland,adapterContactfav;



    private OnFragmentInteractionListener mListener;

    public FMainLandscape() {
        // Required empty public constructor
    }


    public static FMainLandscape newInstance(List<Contact> contactList) {
        FMainLandscape fragment = new FMainLandscape();
        Bundle args = new Bundle();
        args.putSerializable("list", (Serializable) contactList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactList = (List<Contact>) getArguments().getSerializable("list");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_fmain_landscape, container, false);
        recyclerView=v.findViewById(R.id.containerland);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterContactland =new RecyclerContactAdapterLand(contactList);
        recyclerView.setAdapter(adapterContactland);
        searchView=v.findViewById(R.id.search_view);
        tabLayout=v.findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                        adapterContactland = new RecyclerContactAdapterLand(contactList);
                        recyclerView.setAdapter(adapterContactland);

                        break;
                    case 1:
                        favcontactlist = new ArrayList<>();
                        for (Contact contact : contactList) {
                            if (contact.isFavmarker()) {
                                favcontactlist.add(contact);
                            }
                        }
                        adapterContactfav = new RecyclerContactAdapterLand(favcontactlist);
                        recyclerView.setAdapter(adapterContactfav);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
