// 파일 위치: com.example.liveguard_app_010.ui.home.CameraAnimationHelper.java

package com.example.liveguard_app_010.ui.home;

import android.animation.ValueAnimator;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.geometry.LatLng;

public class CameraAnimationHelper {

    public static void animateCamera(NaverMap naverMap, LatLng startPosition, LatLng endPosition, float startZoom, float endZoom, long duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            double latitude = startPosition.latitude + (endPosition.latitude - startPosition.latitude) * fraction;
            double longitude = startPosition.longitude + (endPosition.longitude - startPosition.longitude) * fraction;
            float zoom = startZoom + (endZoom - startZoom) * fraction;
            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(latitude, longitude), zoom);
            naverMap.moveCamera(cameraUpdate);
        });
        animator.start();
    }
}