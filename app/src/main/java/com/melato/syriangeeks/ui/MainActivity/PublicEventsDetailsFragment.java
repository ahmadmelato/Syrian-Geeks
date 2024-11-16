package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.databinding.FragmentPublicEventsDetailsBinding;
import com.melato.syriangeeks.model.EventModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PublicEventsDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentPublicEventsDetailsBinding binding;
    private MainViewModel viewModel;

    private static final String ARG_PARAM1 = "data";

    private String data;


    public PublicEventsDetailsFragment() {
        // Required empty public constructor
    }

    public static PublicEventsDetailsFragment newInstance(String param1) {
        PublicEventsDetailsFragment fragment = new PublicEventsDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_events_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.toolbarBack.setOnClickListener(this);
        binding.btulike.setOnClickListener(this);

        EventModel.Item item = new Gson().fromJson(data, EventModel.Item.class);

        if (ClientAPI.getClientAPI().tokenInterceptor.getToken().isEmpty()) {
            binding.btulike.setVisibility(View.GONE);
        }

        binding.eventName.setText(item.title);

        binding.eventDis.setText(item.getContent());

        binding.eventLocation.setText(item.address);
        binding.eventDate.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(item.start_date));
        loadImage(ClientAPI.BASE_URL + "/storage/" + item.image.original, binding.img);
    }


    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.img_events) // Optional: Placeholder while loading
                .error(R.drawable.img_events) // Optional: Image to show on error
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.btulike) {

        }
    }
}