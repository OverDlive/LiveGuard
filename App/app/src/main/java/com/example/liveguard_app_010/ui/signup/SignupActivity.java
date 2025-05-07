package com.example.liveguard_app_010.ui.signup;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etEmail, etPassword, etConfirmPassword;
    private TextInputLayout layoutUsername, layoutEmail, layoutPassword, layoutConfirmPassword;
    private Button btnSignup, btnBack;
    private TextView tvLoginLink;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageButton btnBack;

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        layoutUsername = findViewById(R.id.layout_username);
        layoutEmail = findViewById(R.id.layout_email);
        layoutPassword = findViewById(R.id.layout_password);
        layoutConfirmPassword = findViewById(R.id.layout_confirm_password);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnSignup = findViewById(R.id.btn_signup);
        btnBack = findViewById(R.id.btn_back);
        tvLoginLink = findViewById(R.id.tv_login_link);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("회원가입 중입니다...");
        progressDialog.setCancelable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutUsername.setError(null);
                layoutEmail.setError(null);
                layoutPassword.setError(null);
                layoutConfirmPassword.setError(null);

                final String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
                final String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
                final String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
                final String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

                boolean valid = true;
                if (TextUtils.isEmpty(username)) {
                    layoutUsername.setError("사용자 이름을 입력해주세요.");
                    valid = false;
                }
                if (TextUtils.isEmpty(email)) {
                    layoutEmail.setError("이메일을 입력해주세요.");
                    valid = false;
                }
                if (TextUtils.isEmpty(password)) {
                    layoutPassword.setError("비밀번호를 입력해주세요.");
                    valid = false;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    layoutConfirmPassword.setError("비밀번호 확인을 입력해주세요.");
                    valid = false;
                }
                if (!TextUtils.isEmpty(password) && !password.equals(confirmPassword)) {
                    layoutConfirmPassword.setError("비밀번호가 일치하지 않습니다.");
                    valid = false;
                }
                if (!valid) return;

                progressDialog.show();

                // Check email duplication
                auth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<com.google.firebase.auth.SignInMethodQueryResult> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null && task.getResult().getSignInMethods() != null &&
                                            !task.getResult().getSignInMethods().isEmpty()) {
                                        progressDialog.dismiss();
                                        layoutEmail.setError("이미 사용 중인 이메일입니다.");
                                        return;
                                    }
                                    // Check username duplication in Firestore
                                    db.collection("users")
                                            .whereEqualTo("username", username)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<com.google.firebase.firestore.QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (!task.getResult().isEmpty()) {
                                                            progressDialog.dismiss();
                                                            layoutUsername.setError("이미 사용 중인 사용자 이름입니다.");
                                                            return;
                                                        }
                                                        // Create user in Firebase Auth
                                                        auth.createUserWithEmailAndPassword(email, password)
                                                                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                                                                        if (task.isSuccessful()) {
                                                                            FirebaseUser firebaseUser = auth.getCurrentUser();
                                                                            if (firebaseUser == null) {
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(SignupActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                                                return;
                                                                            }
                                                                            final String uid = firebaseUser.getUid();
                                                                            final Map<String, Object> userData = new HashMap<>();
                                                                            userData.put("username", username);
                                                                            userData.put("email", email);
                                                                            userData.put("createdAt", System.currentTimeMillis());
                                                                            // Firestore transaction to set user and username mapping
                                                                            db.runTransaction(new Transaction.Function<Void>() {
                                                                                @Override
                                                                                public Void apply(@NonNull Transaction transaction) {
                                                                                    DocumentReference userDoc = db.collection("users").document(uid);
                                                                                    DocumentReference usernameDoc = db.collection("usernames").document(username);
                                                                                    transaction.set(userDoc, userData);
                                                                                    Map<String, Object> usernameMap = new HashMap<>();
                                                                                    usernameMap.put("uid", uid);
                                                                                    transaction.set(usernameDoc, usernameMap);
                                                                                    return null;
                                                                                }
                                                                            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    progressDialog.dismiss();
                                                                                    if (task.isSuccessful()) {
                                                                                        Toast.makeText(SignupActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                                                        finish();
                                                                                    } else {
                                                                                        Toast.makeText(SignupActivity.this, "가입정보 저장에 실패했습니다: " + (task.getException() != null ? task.getException().getMessage() : ""), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                        } else {
                                                                            progressDialog.dismiss();
                                                                            Exception e = task.getException();
                                                                            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : "회원가입에 실패했습니다.";
                                                                            Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(SignupActivity.this, "사용자 이름 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupActivity.this, "이메일 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
