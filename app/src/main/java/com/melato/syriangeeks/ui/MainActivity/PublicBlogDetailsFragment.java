package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.databinding.FragmentPublicBlogDetailsBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PublicBlogDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentPublicBlogDetailsBinding binding;
    private MainViewModel mainViewModel;

    private static final String ARG_PARAM1 = "id";

    // TODO: Rename and change types of parameters
    private int id;

    public PublicBlogDetailsFragment() {
        // Required empty public constructor
    }


    public static PublicBlogDetailsFragment newInstance(Integer param1) {
        PublicBlogDetailsFragment fragment = new PublicBlogDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        WebSettings webSettings = binding.web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setFixedFontFamily();
        mainViewModel.blogdetailsModelLiveData.observe(getViewLifecycleOwner(), blogDetalsModel -> {
            binding.blogName.setText(blogDetalsModel.title);
            binding.blogDate.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(blogDetalsModel.created_at));

            String htmlContent = "<html dir='rtl' lang='ar'>" +
                    "<head>" +
                    "<style type='text/css'>" +
                    "@font-face { font-family: 'Bahji'; src: url('https://sygeeks.net/frontend/assets/fonts/bahji/bahji.ttf'); }" +
                    "body { font-family: 'Bahji'; font-size: 16px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" + blogDetalsModel.description + "</body>" +
                    "</html>";

            binding.web.loadData(htmlContent, "text/html; charset=UTF-8", null);
            System.out.println(ClientAPI.BASE_URL + "/storage" + blogDetalsModel.meta_image.original);
            loadImage(ClientAPI.BASE_URL + "/storage/" + blogDetalsModel.meta_image.original);
            binding.web.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.web.setTextDirection(View.TEXT_DIRECTION_RTL);

        });

        binding.toolbarBack.setOnClickListener(this);
        binding.nointernet.setOnClickListener(this);
        mainViewModel.getBlogsDetails(requireContext(), id);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_blog_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getBlogsDetails(requireContext(), id);
        }
    }

    private void loadImage(String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.blog_logo) // Optional: Placeholder while loading
                .error(R.drawable.blog_logo) // Optional: Image to show on error
                .into(binding.img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

}