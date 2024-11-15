package com.melato.syriangeeks.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentPublicBlogBinding;
import com.melato.syriangeeks.model.BlogModel;
import com.melato.syriangeeks.model.EventModel;


public class PublicBlogFragment extends Fragment implements View.OnClickListener {

    private FragmentPublicBlogBinding binding;
    private MainViewModel viewModel;
    private PublicBlogViewAdapter publicBlogViewAdapter;

    public PublicBlogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbarBack.setOnClickListener(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.listRecyclerView.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });
        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        publicBlogViewAdapter = new PublicBlogViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(publicBlogViewAdapter);

        viewModel.blogModelLiveData.observe(getViewLifecycleOwner(), courseModels -> {
            publicBlogViewAdapter.setBlogList(courseModels);
        });

        publicBlogViewAdapter.SetOnItemClickListener(position -> {
            BlogModel.Blog item = publicBlogViewAdapter.blogList.get(position);
            Intent intent = new Intent(requireContext(), PublicBlogDetailsActivity.class);
            intent.putExtra("id", item.id);
            startActivity(intent);
        });

        binding.nointernet.setOnClickListener(this);

        viewModel.getBlogs(requireContext());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_blog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openMain();
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getBlogs(requireContext());
        }
    }

}