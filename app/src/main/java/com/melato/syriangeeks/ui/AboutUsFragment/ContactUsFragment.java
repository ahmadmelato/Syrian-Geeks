package com.melato.syriangeeks.ui.AboutUsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentContactUsBinding;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainViewModel;


public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private FragmentContactUsBinding binding;
    private MainViewModel viewModel;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.btuSend.setVisibility(working.isFinish());
                if (!working.isRunning() && !working.isSuccessful())
                    Toast.makeText(requireContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
                else if(working.isSuccessful()) {
                    binding.name.setText("");
                    binding.mail.setText("");
                    binding.phoneDail.setText("");
                    binding.phone.setText("");
                    binding.subject.setText("");
                    binding.message.setText("");
                }
            }
        });

        binding.btuSend.setOnClickListener(this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_us, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btuSend) {
            String name = binding.name.getText().toString().trim();
            String email = binding.mail.getText().toString().trim();
            String phone = binding.phoneDail.getText().toString().trim() + binding.phone.getText().toString().trim();
            String subject = binding.subject.getText().toString().trim();
            String message = binding.message.getText().toString().trim();
            if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && phone.length() > 4 && !subject.isEmpty() && !message.isEmpty()) {
                viewModel.contact(requireContext(), name, email, phone, subject, message);
            }
        }
    }
}