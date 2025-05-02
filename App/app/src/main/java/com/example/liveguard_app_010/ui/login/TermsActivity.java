package com.example.liveguard_app_010.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.R;

public class TermsActivity extends AppCompatActivity {

    private CheckBox checkBoxAll, checkBox1, checkBox2;
    private Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        checkBoxAll = findViewById(R.id.checkBoxAll);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        btnAgree = findViewById(R.id.btn_agree);
        ImageView btnBack = findViewById(R.id.btn_back);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 뒤로 가기 버튼
        btnBack.setOnClickListener(v -> finish());

        // "모두 동의하기" 클릭 시 개별 체크박스 자동 체크
        checkBoxAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkBox1.setChecked(isChecked);
            checkBox2.setChecked(isChecked);
        });

        // 이용약관 동의 후 네이버 로그인 화면으로 이동
        btnAgree.setOnClickListener(v -> {
            if (checkBox1.isChecked() && checkBox2.isChecked()) {
                Intent intent = new Intent(TermsActivity.this, NaverLoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "모든 약관에 동의해야 합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
