package com.example.sendasnack.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.sendasnack.R;
import com.example.sendasnack.data.model.Order;
import com.example.sendasnack.data.model.Product;
import com.example.sendasnack.databinding.RecyclerviewItemBinding;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private LayoutInflater mInflater;
    private List<Order> mOrders = Collections.emptyList(); // Cached copy of words
    private Context c;

    OrderListAdapter(Context context, List<Order> orders) {
        Log.i("DEBUG", "Constructor");
        this.c = context;
        mInflater = LayoutInflater.from(context);
        Log.i("DEBUG MENU - CONSTRUCTOR", mInflater.toString());
        this.mOrders = orders;
        for(Order order: this.mOrders){
            Log.i("DEBUG MENU - CONSTRUCTOR", order.toString());
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemBinding = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new OrderViewHolder(mInflater.getContext(),itemBinding);
    }

    @Override
    public void onBindViewHolder( @NonNull OrderListAdapter.OrderViewHolder holder, int position) {

        if (mOrders != null) {
            Order current = mOrders.get(position);
            holder.tvDelivery.setText(current.getDelivery());
            holder.tvPickUP.setText(current.getPickUp());
            holder.tvPrice.setText(Double.toString(current.getPrice()));
            List<Product> prods = new ArrayList<>();
            for (Product p : current.getProducts().getProducts()){
                prods.add(p);
            }
            ProductAdapter adapter = new ProductAdapter(c , prods);
            holder.tvListView.setLayoutManager(new LinearLayoutManager(c));
            holder.tvListView.setAdapter(adapter);

        } else {
            // Covers the case of data not being ready yet.
            holder.tvDelivery.setText("no contact");
            holder.tvPickUP.setText("");
            //holder.btMessage.setSelected(false);
        }
    }

    void setOrders(List<Order> orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mOrders != null)
            return mOrders.size();
        else return 0;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        final TextView tvPickUP;
        final TextView tvDelivery;
        final TextView tvPrice;
        final RecyclerView tvListView;
        //final Button btMessage;

        // activity context
        //private final Context context;

        //RecyclerviewItemBinding binding;

        private final Context context;


        OrderViewHolder(Context context, View itemView) {
            super(itemView);
            //this.binding = binding;
            tvDelivery = itemView.findViewById(R.id.tvDelivery);
            tvPickUP = itemView.findViewById( R.id.tvPickUp);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvListView = itemView.findViewById(R.id.listview);
            //btMessage = itemView.findViewById( R.id.btSendEmail);
            this.context = context;

            // Store the context
            //this.context = context;
            // Attach a click listener to the entire row view
            //btMessage.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Order order = mOrders.get(position);

                sendEmail( view.getContext(), order);
            }
        }
    }

    private void sendEmail(Context context, Order order) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ order.getPickUp() });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello!");

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
