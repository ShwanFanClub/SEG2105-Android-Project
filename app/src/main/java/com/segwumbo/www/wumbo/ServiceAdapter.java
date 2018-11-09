package com.segwumbo.www.wumbo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ServiceName;
        public TextView ServiceCost;
        public Button DeleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            ServiceName = (TextView) itemView.findViewById(R.id.service_name);
            ServiceCost = (TextView) itemView.findViewById(R.id.service_cost);
            DeleteButton = (Button) itemView.findViewById(R.id.delete_button);
        }
    }
    private List<Service> mServices;
    public ServiceAdapter(List<Service> service) {
        mServices = service;
    }
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.custom_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
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
}
