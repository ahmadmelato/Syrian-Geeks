package com.melato.syriangeeks.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityActiveCodeBinding;
import com.melato.syriangeeks.databinding.ActivityPublicEventsDetailsActivityBinding;
import com.melato.syriangeeks.model.EventModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class PublicEventsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPublicEventsDetailsActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_public_events_details_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.toolbar_back).setOnClickListener(this);
        String data = Objects.requireNonNull(getIntent().getExtras()).getString("data");
        EventModel.Item  item = new Gson().fromJson(data,EventModel.Item.class);

        binding.eventName.setText(item.title);

        binding.eventDis.setText(item.getContent());

        binding.eventLocation.setText(item.address);
        binding.eventDate.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(item.start_date));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            finish();
        } else if (v.getId() == R.id.btulike) {

        }
    }
}