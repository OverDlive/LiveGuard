package com.example.liveguard_app_010.ui.TermsDetail;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.liveguard_app_010.R;

public class LocationTermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_terms);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Enable scrolling for the location terms TextView
        TextView tvLocationTermsContent = findViewById(R.id.tv_location_terms_content);
        tvLocationTermsContent.setMovementMethod(new ScrollingMovementMethod());
    }
}
