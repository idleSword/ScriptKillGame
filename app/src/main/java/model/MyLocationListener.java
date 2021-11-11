package model;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MyLocationListener extends BDAbstractLocationListener {//位置监听器
    private final MapView mapView;
    private final BaiduMap mapController;
    private static LatLng bmyLocation;
    public  MyLocationListener(MapView mapView){
        this.mapView=mapView;
        mapController=mapView.getMap();
    }
    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null || mapView == null){
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        bmyLocation =new LatLng(location.getLatitude(), location.getLongitude());
        mapController.setMyLocationData(locData);
    }

    public static LatLng getBmyLocation(){
        return bmyLocation;
    }
}
