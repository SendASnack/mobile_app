package com.example.sendasnack.ui.earning;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sendasnack.LoginActivity;
import com.example.sendasnack.MainActivity;
import com.example.sendasnack.databinding.FragmentEarningBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EarningFragment extends Fragment {

    private FragmentEarningBinding binding;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //EarningViewModel earningViewModel =
          //      new ViewModelProvider(this).get(EarningViewModel.class);

        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent i=new Intent(getActivity(), MainActivity.class);
        startActivity(i);

        binding = FragmentEarningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        //earningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}