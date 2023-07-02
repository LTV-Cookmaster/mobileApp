package com.sorcierstechnologiques.cookmaster.ui.fidelity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sorcierstechnologiques.cookmaster.databinding.FragmentFidelityBinding;

public class FidelityFragment extends Fragment {

    private FragmentFidelityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FidelityViewModel fidelityViewModel =
                new ViewModelProvider(this).get(FidelityViewModel.class);

        binding = FragmentFidelityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFidelity;
        fidelityViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}