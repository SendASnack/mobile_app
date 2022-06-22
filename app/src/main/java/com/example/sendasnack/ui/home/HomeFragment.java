package com.example.sendasnack.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sendasnack.data.model.Order;
import com.example.sendasnack.data.model.Product;
import com.example.sendasnack.data.model.ProductsList;
import com.example.sendasnack.databinding.FragmentHomeBinding;
import com.example.sendasnack.data.viewmodel.OrderViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private OrderViewModel orderViewModel;

    private FragmentHomeBinding binding;

    private FirebaseFirestore db;
    private static final String TAG = "ReadData";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //HomeViewModel homeViewModel =
                //new ViewModelProvider(this).get(HomeViewModel.class);

        db = FirebaseFirestore.getInstance();

        // associate a view model to this fragment

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        db.collection("basededados").document("Ecd7VCP5nIstQu6unRk0")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                insertDataToDatabase(document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }
                        else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        OrderListAdapter adapter = new OrderListAdapter(getActivity(),new ArrayList<>());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerview.setAdapter(adapter);

        // start observing changes in the list (defined in the ViewModel)
        orderViewModel.getAllContacts().observe(getViewLifecycleOwner(), adapter::setOrders);

/*
        orderViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                adapter.setOrders(orders);
            }
        });*/


        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void insertDataToDatabase(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> orderMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            map2 = (Map<String, Object>) value;
            for (Map.Entry<String, Object> entry12 : map2.entrySet()) {
                Order order = new Order();
                double totalPrice = 0;
                String key12 = entry12.getKey();
                Object value12 = entry12.getValue();
                orderMap = (Map<String, Object>) value12;
                Log.d(TAG, "key12: " + key12);
                Log.d(TAG, "value12: " + value12);
                Log.d(TAG, "MAPA2: " + orderMap);
                for (Map.Entry<String, Object> entry2 : orderMap.entrySet()) {
                    String key2 = entry2.getKey();
                    Object value2 = entry2.getValue();
                    Log.d(TAG, "key2: " + key2);
                    Log.d(TAG, "value2: " + value2);
                    if (key2.equals("pickUp")) {
                        String pickUp = (String) value2;
                        order.setPickUp(pickUp);
                    } else if (key2.equals("delivery")) {
                        String delivery = (String) value2;
                        order.setDelivery(delivery);
                    }
                    else if (key2.equals("deliveryCoords")) {
                        String deliveryCoords = (String) value2;
                        order.setDeliveryCoords(deliveryCoords);
                    }
                    else if (key2.equals("pickUpCoords")) {
                        String pickUpCoords = (String) value2;
                        order.setPickUpCoords(pickUpCoords);
                    }else {
                        Map<String, Object> newOrderMap = new HashMap<>();
                        Log.d(TAG, "value2 dentro do else: " + value2);
                        newOrderMap = (Map<String, Object>) value2;
                        for (Map.Entry<String, Object> entry3 : newOrderMap.entrySet()) {
                            Product p = new Product();
                            Object value3 = entry3.getValue();
                            Map<String, Object> newOrderMap2 = new HashMap<>();
                            newOrderMap2 = (Map<String, Object>) value3;
                            for (Map.Entry<String, Object> entry4 : newOrderMap2.entrySet()) {
                                String key4 = entry4.getKey();
                                Object value4 = entry4.getValue();
                                if (key4.equals("description")) {
                                    String desc = (String) value4;
                                    p.setDescription(desc);
                                } else if (key4.equals("name")) {
                                    String name = (String) value4;
                                    p.setName(name);
                                } else if (key4.equals("price")) {
                                    double price = Double.parseDouble(value4.toString());
                                    totalPrice = totalPrice + price;
                                    p.setPrice(price);
                                }
                            }
                            if (order.getProducts() == null) {
                                List<Product> prods = new ArrayList<>();
                                prods.add(p);
                                order.setProducts(new ProductsList(prods));
                            } else
                                order.getProducts().getProducts().add(p);
                            order.setPrice(totalPrice);
                        }
                    }
                }
                if(!orderViewModel.contains(order))
                    orderViewModel.insert(order);
                Log.d(TAG, "VALORES: " + order.getPickUp());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}