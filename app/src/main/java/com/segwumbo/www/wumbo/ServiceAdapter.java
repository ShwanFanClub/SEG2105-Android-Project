package com.segwumbo.www.wumbo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.se.omapi.SEService;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.support.v4.content.ContextCompat.createDeviceProtectedStorageContext;
import static android.support.v4.content.ContextCompat.startActivity;
import static android.text.TextUtils.isDigitsOnly;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  {

    DatabaseReference databaseServices = FirebaseDatabase.getInstance().getReference("services");
    private final ClickListener listener;
    private List<Service> mServices = new ArrayList<>();
    public ServiceAdapter(List<Service> service, ClickListener listener) {
        for (int i = 0; i<service.size();i++){
            mServices.add(service.get(i));
        }
        this.listener = listener;
    }
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.custom_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.ViewHolder viewHolder, int position) {
        Service service = mServices.get(position);
        TextView sName = viewHolder.ServiceName;
        sName.setText(service.getName());
        TextView sCost = viewHolder.ServiceCost;
        sCost.setText(String.valueOf(service.getHourlyRate()));
    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ServiceName;
        public TextView ServiceCost;
        public Button DeleteButton;
        public Button UpdateButton;
        private String m_Text = "";
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            ServiceName = (TextView) itemView.findViewById(R.id.service_name);
            ServiceCost = (TextView) itemView.findViewById(R.id.service_cost);
            DeleteButton = (Button) itemView.findViewById(R.id.delete_button);
            DeleteButton.setOnClickListener(this);
            UpdateButton = (Button) itemView.findViewById(R.id.update_button);
            UpdateButton.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            if (v.getId() == DeleteButton.getId()) {
                //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition())+ " "+getItemCount(), Toast.LENGTH_SHORT).show();


                /*
                DatabaseReference serviceToDelete = databaseServices.child(mServices.get(getAdapterPosition()).getId());
                serviceToDelete.removeValue();
                mServices.remove(getAdapterPosition());
                ArrayList<Service> clone = new ArrayList<>();
                while (!mServices.isEmpty()){
                    clone.add();
                    mServices.remove(0);
                }
                mServices.clear();
                mServices.addAll(clone);
                notifyDataSetChanged();*/
                DatabaseReference serviceToDelete = databaseServices.child(mServices.get(getAdapterPosition()).getId());
                Service temp = mServices.get(getAdapterPosition());
                mServices.remove(getAdapterPosition());
                ArrayList<Service> clone = new ArrayList<>();
                while (!mServices.isEmpty()){
                    clone.add(new Service(mServices.get(0).getId(),mServices.get(0).getName(),mServices.get(0).getHourlyRate()));
                    mServices.remove(0);
                }
                mServices.clear();
                mServices.addAll(clone);
                notifyDataSetChanged();
                serviceToDelete.removeValue();
                ((ModifyServices)v.getContext()).refresh();
                //for (int i = 0; i<(clone.size()+1);i++){
                //    DatabaseReference serviceToDelete = databaseServices.child(mServices.get(getAdapterPosition()).getId());

                /*String id = databaseServices.push().getKey();
                Service newService;

                newService = new Service(id, serviceName, Double.parseDouble(hourlyRate));

                // stores user in database as a JSON object
                databaseServices.child(id).setValue(newService);*/
            }
            if (v.getId() == UpdateButton.getId()){

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Update Pricing For: "+ mServices.get(getAdapterPosition()).getName());
                final EditText input = new EditText(v.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        String temp = m_Text.replace(".","");
                        if (isDigitsOnly(temp) && !temp.equals("")){
                            databaseServices.child(mServices.get(getAdapterPosition()).getId()).child("hourlyRate").setValue(Double.parseDouble(m_Text));
                            ((ModifyServices)v.getContext()).refresh();
                        }
                        else{
                            Toast.makeText(v.getContext(), "Invalid Input",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }

        }

    }
}
