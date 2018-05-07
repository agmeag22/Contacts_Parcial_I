package com.meag.contactsp.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.meag.contactsp.Adapters.PhoneAdapter;
import com.meag.contactsp.Objects.Contact;
import com.meag.contactsp.R;

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
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView rv;
    private PhoneAdapter phoneAdapter;
    private ImageView img;
    private ImageButton back;
    private TextView name,birthday,email,address;

    Contact contact;
    ImageButton button_delete;


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
            contact = (Contact) getArguments().getSerializable("list");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_description__contact, container, false);
        rv = v.findViewById(R.id.phonerecyclerview);
        img = v.findViewById(R.id.description_image_view);
        back=v.findViewById(R.id.btnback);
        name = v.findViewById(R.id.name_text);
        img= v.findViewById(R.id.description_image_view);
        name=v.findViewById(R.id.name_text);
        birthday=v.findViewById(R.id.birthday_text);
        email=v.findViewById(R.id.email_text);
        address=v.findViewById(R.id.addresstext);
        button_delete=v.findViewById(R.id.btn_delete);
//        address=findViewById(R.id.addresstext);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(linearLayoutManager);

        if (contact != null) {
            if(contact.getImg()!=null){
                Uri uri=Uri.parse(contact.getImg());
                img.setImageURI(uri);}
            else{
                img.setImageResource(R.drawable.person); }

            if(contact.getName().size()>0){
                name.setText(contact.getName().get(0));}

            if(contact.getEmail().size()>0){
                email.setText(contact.getEmail().get(0));}

            if(contact.getPhone().size()>0) {
                String phonestring=new ArrayList<String>(contact.getPhone().values()).get(0);
                phoneAdapter=new PhoneAdapter(this.getContext(),contact.getPhone());
                rv.setAdapter(phoneAdapter); }

            if(contact.getAddress()!=null){
                address.setText(contact.getAddress());}

            if(contact.getBirthdate()!=null){
                birthday.setText(contact.getBirthdate().toString());
            }
        }
        final FMainLandscape fMainLandscape=this;
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                int index = main.getContactList().indexOf(contact);
                main.getContactList().remove(contact);
                main.getAdapterContactland().notifyItemRemoved(index);
                main.getAdapterContactland().notifyDataSetChanged();
                getActivity().getSupportFragmentManager().beginTransaction().remove(fMainLandscape).commit();
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
