package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.tabs.TabLayoutMediator;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.databinding.FragmentCourseDetailsBinding;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;
import com.melato.syriangeeks.ui.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PublicCourseDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentCourseDetailsBinding binding;
    private MainViewModel mainViewModel;
    private SliderMainAdapter sliderMainAdapter;


    private static final String ARG_PARAM1 = "state";
    private static final String ARG_PARAM2 = "id";

    private String state;
    private int id;


    public PublicCourseDetailsFragment() {
        // Required empty public constructor
    }

    public static PublicCourseDetailsFragment newInstance(String param1, int id) {
        PublicCourseDetailsFragment fragment = new PublicCourseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            state = getArguments().getString(ARG_PARAM1);
            id = getArguments().getInt(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        mainViewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main1.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
                if (!working.isRunning() && !working.isSuccessful())
                    Toast.makeText(requireContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.coursedetailsModelLiveData.observe(getViewLifecycleOwner(), courseDetalsModel -> {
            binding.blogName.setText(courseDetalsModel.title);
            binding.teacherName.setText(courseDetalsModel.getInstructor_name());
            binding.courseHour.setText(courseDetalsModel.time);
            binding.courseDep.setText(courseDetalsModel.category.title);
            binding.courseDays.setText(courseDetalsModel.lessonsCount + " درس");
            binding.courseTeath.setText(courseDetalsModel.getInstructor_name());
            binding.btuJouin.setEnabled(!courseDetalsModel.joined);

            loadImage(ClientAPI.BASE_URL + "/storage/" + courseDetalsModel.thumbnail_image.original);

            sliderMainAdapter = new SliderMainAdapter(requireActivity(), courseDetalsModel);
            binding.viewpager.setAdapter(sliderMainAdapter);
            new TabLayoutMediator(binding.tabs, binding.viewpager, (tab, position) -> {
                tab.setText(sliderMainAdapter.getItem(position));
            }).attach();
        });

        binding.toolbarBack.setOnClickListener(this);
        binding.nointernet.setOnClickListener(this);
        binding.btuJouin.setOnClickListener(this);

        if (state.equals("MY")) {
            mainViewModel.getCourseFullDetails(requireContext(), id);
            binding.btuJouin.setVisibility(View.GONE);
        } else {
            mainViewModel.getCourseDetails(requireContext(), id);
            binding.btuJouin.setVisibility(View.VISIBLE);
            if (ClientAPI.getClientAPI().tokenInterceptor.getToken().isEmpty())
                binding.btuJouin.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getCourseDetails(requireContext(), id);
        } else if (v.getId() == R.id.btuJouin) {
            mainViewModel.join(requireActivity(), id, Objects.requireNonNull(mainViewModel.coursedetailsModelLiveData.getValue()).slug);
        }
    }

    private void loadImage(String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.blog_logo) // Optional: Placeholder while loading
                .error(R.drawable.blog_logo) // Optional: Image to show on error
                .into(binding.img);
    }
}