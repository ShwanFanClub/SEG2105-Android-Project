package com.segwumbo.www.wumbo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder>  {

    private final ClickListener listener;
    private List<UserAccount> serviceProviders = new ArrayList<>();
    private ArrayList<ViewHolder> viewHolders = new ArrayList<>();
    private String serviceNameString;

    public ProviderAdapter(ArrayList<UserAccount> providers, ClickListener listener, String serviceName) {
        serviceProviders.addAll(providers);
        this.listener = listener;
        this.serviceNameString = serviceName;
    }

    @Override
    public @NonNull ProviderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.viewprovider_custom_row_layout, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, listener);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderAdapter.ViewHolder viewHolder, int position) {

        UserAccount provider = serviceProviders.get(position);
        TextView providerName = viewHolder.providerName;
        TextView serviceName = viewHolder.serviceName;
        providerName.setText(provider.getUsername());
        serviceName.setText(this.serviceNameString);
    }

    @Override
    public int getItemCount() {
        return serviceProviders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView providerName;
        public TextView serviceName;
        public Button ViewButton;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            providerName = itemView.findViewById(R.id.provider_name);
            serviceName = itemView.findViewById(R.id.service_name);

            ViewButton = itemView.findViewById(R.id.provider_button);
            ViewButton.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

            String providerName = serviceProviders.get(getAdapterPosition()).getUsername();

            Intent intent = new Intent(v.getContext(), ServiceProfile.class);
            Bundle bundle = new Bundle();

            bundle.putString("username", providerName);
            bundle.putString("service name", serviceNameString);
            intent.putExtra("bundle", bundle);

            v.getContext().startActivity(intent);
        }
    }
}