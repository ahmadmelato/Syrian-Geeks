package com.melato.syriangeeks.ui.MainActivity;

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

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentPublicBlogBinding;
import com.melato.syriangeeks.model.BlogModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


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

        viewModel.workingLoadMore.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.moreprogress.setVisibility(working.isProgressing());
            }
        });

        viewModel.blogModelLiveData.observe(getViewLifecycleOwner(), blogs -> {
            List<BlogModel.Blog> datum = new ArrayList<>();
            for (BlogModel item : blogs) {
                datum.addAll(item.data);
            }
            publicBlogViewAdapter.setBlogList(datum);
        });

        publicBlogViewAdapter.SetOnItemClickListener(position -> {
            BlogModel.Blog item = publicBlogViewAdapter.blogList.get(position);
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicBlogDetailsFragment(item.id);
        });

        binding.nointernet.setOnClickListener(this);

        if (viewModel.blogModelLiveData.getValue() == null)
            viewModel.getBlogs(requireContext());


        binding.listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.listRecyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int currentItem = linearLayoutManager.getChildCount();
                int totalItem = linearLayoutManager.getItemCount();
                AtomicInteger secrollOutItem = new AtomicInteger(linearLayoutManager.findFirstVisibleItemPosition());

                if (!Objects.requireNonNull(viewModel.workingLoadMore.getValue()).isRunning() && currentItem + secrollOutItem.get() == totalItem) {
                    viewModel.getMoreBlogs(requireContext());
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

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
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getBlogs(requireContext());
        }
    }

}