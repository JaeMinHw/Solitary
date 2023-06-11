package org.techtown.push.myprotecter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;

import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;


public class soli_map extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soli_map);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        naverMapBasicSettings();
    }

    public void naverMapBasicSettings() {
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        // 현재 위치 버튼 안보이게 설정
        UiSettings uiSettings = naverMap.getUiSettings();

        uiSettings.setLocationButtonEnabled(false);

        // 지도 유형 위성사진으로 설정
        naverMap.setMapType(NaverMap.MapType.Basic);
        Marker marker = new Marker();
        marker.setPosition(new LatLng(36.6246456, 127.4825031));
        marker.setMap(naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(
                        new LatLng(36.6246456, 127.4825031),17)
                .animate(CameraAnimation.Fly, 3000);

        naverMap.moveCamera(cameraUpdate);
        marker.setCaptionTextSize(16);
        marker.setCaptionText("독거인분의 가장 마지막 위치입니다.");
        marker.setCaptionRequestedWidth(200);
        marker.setCaptionColor(Color.BLUE);
        marker.setCaptionHaloColor(Color.rgb(200, 255, 200));

    }

}
