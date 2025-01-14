package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentMainBinding;
import com.melato.syriangeeks.model.BlogModel;
import com.melato.syriangeeks.model.CourseModel;
import com.melato.syriangeeks.model.EventModel;
import com.melato.syriangeeks.ui.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding binding;
    private MainViewModel mainViewModel;
    private CourseRecyclerViewAdapter courseRecyclerViewAdapter;
    private BlogRecyclerViewAdapter blogRecyclerViewAdapter;
    private EventRecyclerViewAdapter eventRecyclerViewAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton toolbar_openmenu = view.findViewById(R.id.toolbar_openmenu);
        ImageButton toolbar_notify = view.findViewById(R.id.toolbar_notify);
        TextView toolbar_name = view.findViewById(R.id.toolbar_name);
        CircleImageView profile_image = view.findViewById(R.id.profile_image);
        toolbar_openmenu.setOnClickListener(this);
        toolbar_notify.setOnClickListener(this);
        mainViewModel = ((MainActivity) requireActivity()).getMainViewModel();

        mainViewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        MainViewModel.userLiveData.observe(getViewLifecycleOwner(), userModel -> {
            if (userModel != null) {
                toolbar_name.setText("أهلا " + userModel.getUser().getName());
                toolbar_notify.setVisibility(View.VISIBLE);
                profile_image.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(userModel.getUser().getAvatar())
                        .placeholder(R.mipmap.ic_launcher)
                        .circleCrop()
                        .into(profile_image);
            } else {
                profile_image.setImageResource(R.mipmap.ic_launcher);
                toolbar_name.setText("مرحباً بك");
                toolbar_notify.setVisibility(View.GONE);
                profile_image.setVisibility(View.GONE);

            }
        });

        binding.RecyclerView1.setHasFixedSize(true);
        binding.RecyclerView2.setHasFixedSize(true);
        binding.RecyclerView3.setHasFixedSize(true);
        binding.nointernet.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        toolbar_name.setOnClickListener(this);
        binding.RecyclerView1.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.RecyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.RecyclerView3.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));

        courseRecyclerViewAdapter = new CourseRecyclerViewAdapter(requireContext());
        blogRecyclerViewAdapter = new BlogRecyclerViewAdapter(requireContext());
        eventRecyclerViewAdapter = new EventRecyclerViewAdapter(requireContext());
        binding.RecyclerView1.setAdapter(courseRecyclerViewAdapter);
        binding.RecyclerView2.setAdapter(blogRecyclerViewAdapter);
        binding.RecyclerView3.setAdapter(eventRecyclerViewAdapter);

        binding.showMore1.setOnClickListener(this);
        binding.showMore2.setOnClickListener(this);
        binding.showMore3.setOnClickListener(this);


        mainViewModel.courseModelLiveData.observe(getViewLifecycleOwner(), courseModels -> {
            List<CourseModel.Datum> datum = new ArrayList<>();
            for (CourseModel item : courseModels) {
                datum.addAll(item.data);
            }
            courseRecyclerViewAdapter.setCourseModels(datum);
            if(datum.isEmpty()){
                binding.HeaderRecyclerView1.setVisibility(View.GONE);
                binding.RecyclerView1.setVisibility(View.GONE);
            }else {
                binding.HeaderRecyclerView1.setVisibility(View.VISIBLE);
                binding.RecyclerView1.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.blogModelLiveData.observe(getViewLifecycleOwner(), blogs -> {
            List<BlogModel.Blog> datum = new ArrayList<>();
            for (BlogModel item : blogs) {
                datum.addAll(item.data);
            }
            blogRecyclerViewAdapter.setBlogList(datum);
            if(datum.isEmpty()){
                binding.HeaderRecyclerView2.setVisibility(View.GONE);
                binding.RecyclerView2.setVisibility(View.GONE);
            }else {
                binding.HeaderRecyclerView2.setVisibility(View.VISIBLE);
                binding.RecyclerView2.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.eventModelLiveData.observe(getViewLifecycleOwner(), events -> {
            eventRecyclerViewAdapter.setEventList(events);
            if(events.isEmpty()){
                binding.HeaderRecyclerView3.setVisibility(View.GONE);
                binding.RecyclerView3.setVisibility(View.GONE);
            }else {
                binding.HeaderRecyclerView3.setVisibility(View.VISIBLE);
                binding.RecyclerView3.setVisibility(View.VISIBLE);
            }
        });

        eventRecyclerViewAdapter.SetOnItemClickListener(position -> {
            EventModel.Item item = eventRecyclerViewAdapter.itemList.get(position);
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicEventsDetailsFragment(new Gson().toJson(item));
        });

        blogRecyclerViewAdapter.SetOnItemClickListener(position -> {
            BlogModel.Blog item = blogRecyclerViewAdapter.blogList.get(position);
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicBlogDetailsFragment(item.id);
        });

        courseRecyclerViewAdapter.SetOnItemClickListener(position -> {
            CourseModel.Datum item = courseRecyclerViewAdapter.CourseModels.get(position);
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicCourseDetailsFragment("PUK", item.getId());
        });

        if (mainViewModel.eventModelLiveData.getValue() == null)
            mainViewModel.getIndexCourses(requireContext(), "");
    }


    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        int id = v.getId();
        if (id == R.id.toolbar_openmenu) {
            assert mainActivity != null;
            mainActivity.openDrawerLayout();
        } else if (id == R.id.toolbar_notify) {
            assert mainActivity != null;
            mainActivity.openNotifications();
        } else if (id == R.id.show_more1) {
            assert mainActivity != null;
            mainActivity.openAllCousres();
        } else if (id == R.id.show_more2) {
            assert mainActivity != null;
            mainActivity.openAllBlog();
        } else if (id == R.id.show_more3) {
            assert mainActivity != null;
            mainActivity.openAllEvents();
        } else if (id == R.id.nointernet) {
            mainViewModel.getIndexCourses(requireContext(), "");
        }else if ((id == R.id.profile_image || id == R.id.toolbar_name) && MainViewModel.userLiveData.getValue() != null) {
            assert mainActivity != null;
            mainActivity.openProfileFragment();
        }
    }

}