package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.databinding.FragmentPeopleQuationBinding;
import com.melato.syriangeeks.model.AnswerModel;
import com.melato.syriangeeks.model.QuestionModel;
import com.melato.syriangeeks.ui.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PeopleQuationFragment extends Fragment implements View.OnClickListener {

    private FragmentPeopleQuationBinding binding;
    private MainViewModel viewModel;
    private AnswerRecyclerViewAdapter answerRecyclerViewAdapter;


    private QuestionModel.Datum datum;

    public PeopleQuationFragment() {
        // Required empty public constructor
    }

    public static PeopleQuationFragment newInstance(String param1) {
        PeopleQuationFragment fragment = new PeopleQuationFragment();
        Bundle args = new Bundle();
        args.putString("data", param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.btuReplay.setVisibility(working.isSuccessfulView());
                binding.listRecyclerView.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        viewModel.workingLoadMore.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.moreprogress.setVisibility(working.isProgressing());
            }
        });


        binding.toolbarBack.setOnClickListener(this);
        binding.nointernet.setOnClickListener(this);

        binding.contentItem.setText(datum.user.name_ar);
        binding.contentTitle.setText(datum.title);
        binding.contentDisc.setText(datum.getQuestion());
        binding.dateItem.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(datum.created_at));

        binding.contentItem.setOnClickListener(this);


        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        answerRecyclerViewAdapter = new AnswerRecyclerViewAdapter(requireContext(), this);
        binding.listRecyclerView.setAdapter(answerRecyclerViewAdapter);


        viewModel.answerliveData.observe(getViewLifecycleOwner(), datumList -> {
            List<AnswerModel.Datum> datum = new ArrayList<>();
            for (AnswerModel item : datumList) {
                datum.addAll(item.data);
            }
            answerRecyclerViewAdapter.setDatumList(datum);
        });

        answerRecyclerViewAdapter.SetOnItemClickListener(position -> {
                    if (answerRecyclerViewAdapter.datumList.get(position).user != null) {
                        openPublicProfileFragment(answerRecyclerViewAdapter.datumList.get(position).user.id);
                    }
                });

        binding.nointernet.setOnClickListener(this);
        binding.toolbarBack.setOnClickListener(this);
        binding.btuReplay.setOnClickListener(this);

        if (viewModel.answerliveData.getValue() == null)
            viewModel.getQuestionsDetails(requireActivity(), datum.id);

        binding.listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.listRecyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int currentItem = linearLayoutManager.getChildCount();
                int totalItem = linearLayoutManager.getItemCount();
                int secrollOutItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (!Objects.requireNonNull(viewModel.workingLoadMore.getValue()).isRunning() && currentItem + secrollOutItem == totalItem) {
                    viewModel.getMoreQuestionsDetails(requireContext(), datum.id);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        }
        if (v.getId() == R.id.content_item) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicProfileFragment(datum.user.id);
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getQuestionsDetails(requireActivity(), datum.id);
        } else if (v.getId() == R.id.btuReplay) {
            if (!ClientAPI.getClientAPI().tokenInterceptor.getToken().isEmpty()) {
                showCreateAnwserDialog();
            } else {
                Toast.makeText(requireContext(), "الرجاء تسجيل الدخول الى حسابك", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void openPublicProfileFragment(int user_id) {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.openPublicProfileFragment(user_id);
    }


    private void showCreateAnwserDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View dialogView = inflater.inflate(R.layout.dialog_create_anwser, null);

        EditText topic_content = dialogView.findViewById(R.id.topic_content);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button submitButton = dialogView.findViewById(R.id.submit_button);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        submitButton.setOnClickListener(v -> {
            String content = topic_content.getText().toString().trim();

            if (content.isEmpty()) {
                Toast.makeText(requireContext(), "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            } else {
                // Handle submission
                viewModel.question_store(requireContext(), datum.id, content);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showCreateCommanitDialog(int id) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View dialogView = inflater.inflate(R.layout.dialog_create_anwser, null);

        EditText topic_content = dialogView.findViewById(R.id.topic_content);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button submitButton = dialogView.findViewById(R.id.submit_button);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        submitButton.setOnClickListener(v -> {
            String content = topic_content.getText().toString().trim();

            if (content.isEmpty()) {
                Toast.makeText(requireContext(), "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            } else {
                // Handle submission
                viewModel.comment(requireContext(), id, content);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            datum = new Gson().fromJson(getArguments().getString("data"), QuestionModel.Datum.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people_quation, container, false);
        return binding.getRoot();
    }
}