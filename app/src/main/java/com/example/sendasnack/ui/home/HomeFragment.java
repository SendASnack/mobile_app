package com.example.sendasnack.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sendasnack.api.RetrieveFeedTask;
import com.example.sendasnack.data.model.Order;
import com.example.sendasnack.databinding.FragmentHomeBinding;
import com.example.sendasnack.data.viewmodel.OrderViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private OrderViewModel orderViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //HomeViewModel homeViewModel =
                //new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        OrderListAdapter adapter = new OrderListAdapter(getActivity(),new ArrayList<>());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerview.setAdapter(adapter);

        // associate a view model to this fragment

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        // start observing changes in the list (defined in the ViewModel)
        orderViewModel.getAllContacts().observe(getViewLifecycleOwner(), adapter::setOrders);

        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}