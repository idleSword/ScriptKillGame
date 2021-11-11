package model;

import android.location.Location;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.test1.PlayerActivity;

public class SchoolMap {//学校地图
    public MapView mapView; //地图视图
    private final BaiduMap mapController;   //地图控制器


    private CoordinateConverter converter=new CoordinateConverter();
    private LatLng mLocation;

    public SchoolMap(MapView mapView){
        this.mapView =mapView;

        mLocation=GPS2BConvert(PlayerActivity.myLocation);

        mapController= this.mapView.getMap();
        mapController.setMyLocationEnabled(true);
        mapController.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mapController.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(mLocation,19));    //初始化地图中心点和缩放等级
        mapController.setMaxAndMinZoomLevel(21, 16);    //设置缩放范围

        int accuracyCircleFillColor = 0x00FFFFFF;
        int accuracyCircleStrokeColor = 0x0D00FF00;
        MyLocationConfiguration myLocationConfiguration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,false,null,accuracyCircleFillColor,accuracyCircleStrokeColor);
        mapController.setMyLocationConfiguration(myLocationConfiguration);

    }

    //获取地图控制器
    public BaiduMap getMapController(){
        return mapController;
    }

    public void return2MyLocation(){//返回我的位置
        //System.out.println("尝试返回");
        mapController.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(MyLocationListener.getBmyLocation(),19));
        //System.out.println("返回成功");
    }

    private LatLng GPS2BConvert(Location location){
        LatLng latLng=new LatLng(location.getLatitude(), location.getLongitude());
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latLng);
        return converter.convert();
    }

    private LatLng doConvert(LatLng latLng){
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latLng);
        return converter.convert();
    }


}
