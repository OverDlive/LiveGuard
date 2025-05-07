package com.example.liveguard_app_010.ui.TermsDetail;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.liveguard_app_010.R;
import android.text.method.ScrollingMovementMethod;

public class TermsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_detail);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Enable scrolling for the terms TextView
        TextView tvTermsContent = findViewById(R.id.tv_terms_content);
        tvTermsContent.setMovementMethod(new ScrollingMovementMethod());
    }
}
