package com.example.liveguard_app_010.ui.TermsDetail;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.liveguard_app_010.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Enable scrolling for the privacy policy TextView
        TextView tvPrivacyContent = findViewById(R.id.tv_privacy_content);
        tvPrivacyContent.setMovementMethod(new ScrollingMovementMethod());
    }
}
