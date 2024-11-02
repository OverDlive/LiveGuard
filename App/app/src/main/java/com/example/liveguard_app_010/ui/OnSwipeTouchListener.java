package com.example.liveguard_app_010; // 적절한 패키지 이름으로 수정

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public abstract class OnSwipeTouchListener implements View.OnTouchListener {

    public OnSwipeTouchListener(Context context) {
        // 생성자에서 특별한 초기화 필요 없음
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 필요한 경우 다른 터치 이벤트 처리 가능
        return v.onTouchEvent(event);
    }

    // 스와이프 감지 동작 구현
    public abstract void onSwipeRight();
}
