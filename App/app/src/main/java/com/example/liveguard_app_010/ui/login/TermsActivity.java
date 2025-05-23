package com.example.liveguard_app_010.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.TermsDetail.PrivacyPolicyActivity;
import com.example.liveguard_app_010.ui.TermsDetail.TermsDetailActivity;
import com.example.liveguard_app_010.ui.TermsDetail.LocationTermsActivity;

public class TermsActivity extends AppCompatActivity {

    private CheckBox checkBoxAll, checkBox1, checkBox2, checkBox3;
    private Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_terms);
        // 액션바 무조건 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        checkBoxAll = findViewById(R.id.checkBoxAll);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        btnAgree = findViewById(R.id.btn_agree);
        TextView tvPrivacyDetail = findViewById(R.id.tv_privacy_detail);
        tvPrivacyDetail.setClickable(true);
        tvPrivacyDetail.setOnClickListener(v -> {
            Intent privacyIntent = new Intent(TermsActivity.this, PrivacyPolicyActivity.class);
            startActivity(privacyIntent);
        });
        ImageView btnBack = findViewById(R.id.btn_back);

        TextView tvTermsDetail = findViewById(R.id.tv_terms_detail);
        tvTermsDetail.setClickable(true);
        tvTermsDetail.setOnClickListener(v -> {
            Intent detailIntent = new Intent(TermsActivity.this, TermsDetailActivity.class);
            startActivity(detailIntent);
        });

        TextView tvLocationDetail = findViewById(R.id.tv_location_detail);
        tvLocationDetail.setClickable(true);
        tvLocationDetail.setOnClickListener(v -> {
            Intent locIntent = new Intent(TermsActivity.this, LocationTermsActivity.class);
            startActivity(locIntent);
        });

        // "모두 동의하기" 클릭 시 개별 체크박스 자동 체크
        checkBoxAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkBox1.setChecked(isChecked);
            checkBox2.setChecked(isChecked);
            checkBox3.setChecked(isChecked);
        });

        // 이용약관 동의 후 네이버 로그인 화면으로 이동
        btnAgree.setOnClickListener(v -> {
            if (checkBox1.isChecked() && checkBox2.isChecked()) {
                btnAgree.setEnabled(false);
                Intent intent = new Intent(TermsActivity.this, NaverLoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "모든 약관에 동의해야 합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
