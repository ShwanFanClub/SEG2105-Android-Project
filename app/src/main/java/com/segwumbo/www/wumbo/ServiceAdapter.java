package com.segwumbo.www.wumbo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

import static android.text.TextUtils.isDigitsOnly;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  {

    private int viewType, viewPositionChange;
    private DatabaseReference databaseServices = FirebaseDatabase.getInstance().getReference("services");
    private DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference("users");
    private final ClickListener listener;
    private List<Service> mServices = new ArrayList<>();
    private ArrayList<ViewHolder> viewHolders = new ArrayList<>();

    public ServiceAdapter(ArrayList<Service> service, ClickListener listener, int viewType) {
        mServices.addAll(service);
        this.listener = listener;
        this.viewType = viewType;
    }

    @Override
    public @NonNull ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(this.viewType == 1){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.custom_row_layout, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView, listener, 1);
            viewHolders.add(viewHolder);
            return viewHolder;
        }
        else if(this.viewType == 2){

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.addservice_custom_row_layout, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView, listener, 2);
            viewHolders.add(viewHolder);
            return viewHolder;
        }
        else if(this.viewType == 3){

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.removeservice_custom_row_layout, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView, listener, 3);
            viewHolders.add(viewHolder);
            return viewHolder;
        }

        else if(this.viewType == 4){

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.plain_custom_row_layout, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView, listener, 4);
            viewHolders.add(viewHolder);
            return viewHolder;

        }
        // ViewServices class
        else if(this.viewType == 5){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.viewservice_custom_row_layout, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView, listener, 5);
            viewHolders.add(viewHolder);
            return viewHolder;

        }
        else {
            throw new RuntimeException("Invalid viewType");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder viewHolder, int position) {

        Service service = mServices.get(position);
        TextView sName = viewHolder.ServiceName;
        sName.setText(service.getName());
        TextView sCost = viewHolder.ServiceCost;
        sCost.setText("$ " + String.valueOf(service.getHourlyRate()));
    }

    public void setAllUpdateInvisible(){
        for(ViewHolder viewHolder : viewHolders){
            viewHolder.setUpdateInvisible();
        }
    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ServiceName;
        public TextView ServiceCost;
        public Button DeleteButton, UpdateButton, AddButton, RemoveButton, ViewButton;
        private String m_Text = "";
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View itemView, ClickListener listener, int viewType) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            ServiceName = itemView.findViewById(R.id.service_name);
            ServiceCost = itemView.findViewById(R.id.service_cost);

            if(viewType == 1){
                DeleteButton = itemView.findViewById(R.id.delete_button);
                DeleteButton.setOnClickListener(this);
                UpdateButton = itemView.findViewById(R.id.update_button);
                UpdateButton.setOnClickListener(this);
            }
            else if(viewType == 2){
                AddButton = itemView.findViewById(R.id.add_button);
                AddButton.setOnClickListener(this);
            }
            else if(viewType == 3){
                RemoveButton = itemView.findViewById(R.id.remove_button);
                RemoveButton.setOnClickListener(this);
            }
            else if(viewType == 5){
                ViewButton = itemView.findViewById(R.id.view_button);
                ViewButton.setOnClickListener(this);
            }
        }

        public void setUpdateInvisible(){UpdateButton.setVisibility(View.INVISIBLE);}

        @Override
        public void onClick(final View v) {

            if(viewType == 1){

                if (v.getId() == DeleteButton.getId()) {
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
                }
                if (v.getId() == UpdateButton.getId()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Update Pricing For: "+ mServices.get(getAdapterPosition()).getName());
                    final EditText input = new EditText(v.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    input.setHint("$ "+String.valueOf(mServices.get(getAdapterPosition()).getHourlyRate()));
                    builder.setView(input);

                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            String temp = m_Text.replace(".","");
                            if (isDigitsOnly(temp) && !temp.equals("")){
                                double am = (Double.parseDouble(m_Text));
                                databaseServices.child(mServices.get(getAdapterPosition()).getId()).child("hourlyRate").setValue(am);
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
            else if(viewType == 2){
                AvailableServices.addService(mServices.get(getAdapterPosition()));
                AddButton.setVisibility(View.GONE);
                Toast.makeText(v.getContext(), "Service added!",Toast.LENGTH_SHORT).show();
            }
            else if(viewType == 3){
                ProfileActivity.removeService(mServices.get(getAdapterPosition()), getAdapterPosition());
                RemoveButton.setVisibility(View.GONE);
                Toast.makeText(v.getContext(), "Service removed!",Toast.LENGTH_SHORT).show();
            }
            else if(viewType == 5){
                v.getContext().startActivity(new Intent(v.getContext(),ServiceProfile.class));

            }

        }
    }
}

