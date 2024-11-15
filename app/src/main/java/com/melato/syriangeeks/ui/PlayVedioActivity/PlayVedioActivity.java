package com.melato.syriangeeks.ui.PlayVedioActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityPlayVedioBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayVedioActivity extends AppCompatActivity {

    private ActivityPlayVedioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_vedio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

        // Enable JavaScript
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load YouTube video using iframe
        String videoId = getVideoId(getIntent().getExtras().getString("URL"));
        String iframe = "<html><body style='margin:0;padding:0;'>" +
                "<iframe width='100%' height='100%' " +
                "src='https://www.youtube.com/embed/" + videoId +
                "?controls=0&modestbranding=1&rel=0&showinfo=0' " +
                "frameborder='0' allowfullscreen></iframe></body></html>";
        binding.webView.loadData(iframe, "text/html", "utf-8");

    }

    public String getVideoId(String url) {
        String regex = "(?:https?://)?(?:www\\.)?(?:youtube\\.com/(?:[^/\\n]+/\\S+/|(?:v|e(?:mbed)?)|.*[?&]v=)|youtu\\.be/)([a-zA-Z0-9_-]{11})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1); // Return the video ID
        }
        return null; // Return null if no match found
    }
}