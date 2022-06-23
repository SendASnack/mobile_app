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
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import com.example.sendasnack.R;
import com.example.sendasnack.data.model.Order;
import com.example.sendasnack.data.model.Product;
import com.example.sendasnack.databinding.RecyclerviewItemBinding;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private LayoutInflater mInflater;
    //private List<Order> mOrders = Collections.emptyList();
    private List<Product> mProducts = Collections.emptyList(); // Cached copy of words

    ProductAdapter(Context context, List<Product> products) {
        Log.i("DEBUG", "Constructor");
        mInflater = LayoutInflater.from(context);
        Log.i("DEBUG MENU - CONSTRUCTOR", mInflater.toString());
        this.mProducts = products ;
        for(Product product: this.mProducts){
            Log.i("DEBUG MENU - CONSTRUCTOR", product.toString());
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemBinding = mInflater.inflate(R.layout.listview_item, parent, false);
        return new ProductViewHolder(mInflater.getContext(),itemBinding);
    }

    @Override
    public void onBindViewHolder( @NonNull ProductAdapter.ProductViewHolder holder, int position) {

        if (mProducts != null) {
            Product current = mProducts.get(position);
            holder.desc.setText(current.getDescription());
            holder.name.setText(current.getName());
            holder.price.setText(Double.toString(current.getPrice()) + "€");

        } else {
            // Covers the case of data not being ready yet.
            holder.name.setText("no contact");
            holder.desc.setText("");
            //holder.btMessage.setSelected(false);
        }
    }

    void setOrders(List<Product> products) {
        mProducts = products;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mProducts != null)
            return mProducts.size();
        else return 0;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        final TextView desc;
        final TextView name;
        final TextView price;
        //final Button btMessage;

        // activity context
        //private final Context context;

        //RecyclerviewItemBinding binding;

        private final Context context;


        ProductViewHolder(Context context, View itemView) {
            super(itemView);
            //this.binding = binding;
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById( R.id.desc);
            price    = itemView.findViewById(R.id.price);
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
                Product product = mProducts.get(position);

                sendEmail( view.getContext(), product);
            }
        }
    }

    private void sendEmail(Context context, Product product) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ product.getName() });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello!");

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
