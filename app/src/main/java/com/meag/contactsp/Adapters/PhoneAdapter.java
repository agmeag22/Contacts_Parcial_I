package com.meag.contactsp.Adapters;

import android.Manifest;
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
import android.widget.ImageButton;
import android.widget.TextView;
import com.meag.contactsp.R;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    public Context context;
    public LinkedHashMap<String, String> phone_item;

    public PhoneAdapter(Context context, LinkedHashMap<String, String> phone_item) {
        this.context = context;
        this.phone_item=phone_item;
    }

    @NonNull
    @Override
    public PhoneAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_phone,parent,false);
        PhoneViewHolder viewHolder= new PhoneViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.PhoneViewHolder holder, int position) {
        String type = (new ArrayList<String>(phone_item.keySet())).get(position);
        final String number = (new ArrayList<String>(phone_item.values())).get(position);
        holder.phone.setText(number);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
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
        return phone_item.size();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder{
        TextView phone;
        ImageButton call;



        public PhoneViewHolder(View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.phone_text);
            call=itemView.findViewById(R.id.call_btn);
        }
    }

}
