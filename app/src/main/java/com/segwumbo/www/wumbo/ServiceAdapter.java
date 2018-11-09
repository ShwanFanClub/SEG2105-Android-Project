package com.segwumbo.www.wumbo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  {


    private final ClickListener listener;
    private List<Service> mServices;
    public ServiceAdapter(List<Service> service, ClickListener listener) {
        mServices = service;
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
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            ServiceName = (TextView) itemView.findViewById(R.id.service_name);
            ServiceCost = (TextView) itemView.findViewById(R.id.service_cost);
            DeleteButton = (Button) itemView.findViewById(R.id.delete_button);
            DeleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == DeleteButton.getId()) {
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
