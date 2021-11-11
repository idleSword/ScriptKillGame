package com.example.test1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Beacon;
import model.MyLocationListener;
import model.SqlUtil;
import model.SchoolMap;

public class PlayerActivity extends AppCompatActivity {

    public Beacon beacon=new Beacon();
    private BluetoothAdapter bluetoothAdapter=beacon.getAdapter();
    private BroadcastReceiver searchReceiver=beacon.getSearchReceiver();
    //Set<BluetoothDevice> bondDevices;
    public Context context;

    private MapView mapView=null;
    private Animation scanAnim;
    private ImageView scanView;
    private SchoolMap schoolMap;
    private LocationManager locationManager=null;
    public static Location myLocation =null;
    LocationClient locationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        System.out.println("界面打开了，接受控件");
        context=getApplicationContext();

        SqlUtil sqlUtil=new SqlUtil();
        new Thread(new Runnable(){

            @Override
            public void run() {
                String sql="select * from team_member";
                ResultSet rs=sqlUtil.query(sql);
        }}).start();


        mapView=findViewById(R.id.bMapView);
        scanAnim= AnimationUtils.loadAnimation(context, R.anim.scan_rorate);
        scanView=findViewById(R.id.scan_view);

        initPermission();
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, locationListener);
        }
        catch (SecurityException exception){
            initPermission();
        }
        try {
            myLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (SecurityException exception){
            initPermission();
        }


        schoolMap=new SchoolMap(mapView);
        startLocate();
        schoolMap.return2MyLocation();

        //注册广播接收信号
        System.out.println("权限允许");
        IntentFilter intent=new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);//用BroadcastReceiver来取得搜索结果
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//当扫描模式变化的时候，可以通过此ACTION来监听全局的消息
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//蓝牙模块被打开或关闭，程序可监听
        registerReceiver(searchReceiver,intent); //注册一个接收器

        setOnclickEvent();


        //listView.setOnItemClickListener(new ItemClickListener());
    }


    private void initPermission() //获取定位权限
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                else {
                    /**
                     * 用户未彻底拒绝授予权限
                     * 第一次安装时，调出的允许权限提示框，之后再也不提示
                     */
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)  //申请权限之后的处理
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    // 申请失败
                    Toast.makeText(PlayerActivity.this, "请在设置中更改定位权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    @Override
    public  void onStart() //检测能不能用蓝牙，现在应该都能用，但是别删，不知道删了会怎样
    {
        super.onStart();
        //if bt is not on,request that it be enabled
        if (bluetoothAdapter==null){
            Toast.makeText(context,"蓝牙设备不可用",Toast.LENGTH_SHORT).show();
        }
        if (bluetoothAdapter!=null)
        {
            if (!bluetoothAdapter.isEnabled()){
                Intent enableIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,3);
            }
        }
    }


    /*public class ItemClickListener implements AdapterView.OnItemClickListener //有些地方看不懂，别删
    {
        @Override
        public void onItemClick(AdapterView<?> arg0,View arg1, int arg2,long arg3){
            if(bluetoothAdapter.isDiscovering()) bluetoothAdapter.cancelDiscovery();

            String str=beacon.list.get(arg2);
            String address=str.substring(str.length()-17);
            BluetoothDevice btDev=bluetoothAdapter.getRemoteDevice(address);
        }
    }*/

    private final LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            myLocation=location;
        }
    };

    @Override
    protected void onDestroy() //注销接收器
    {
        super.onDestroy();
        unregisterReceiver(searchReceiver);
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    public Location getMyLocation(){
        try {
            myLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (SecurityException exception){
            initPermission();
        }
        return myLocation;
    }

    private void setOnclickEvent(){
        Button searchBtn;//搜索蓝牙设备
        ToggleButton openBtn;//开关蓝牙设备
        Button locateMeBtn;

        searchBtn=findViewById(R.id.btnSearch);
        openBtn=findViewById(R.id.tbtnSwitch);
        locateMeBtn=findViewById(R.id.locateMeBtn);
        searchBtn.setOnClickListener(new View.OnClickListener()  //按搜索设备按钮之后触发的事件
        {
            @Override
            public void onClick(View view) {
                //TODO Auto-generated method stub
                Handler handler=new Handler();
                System.out.println("开始扫描");
                scanBeacon(handler);
                System.out.println("显示解析结果");
            }
        });

        openBtn.setChecked(bluetoothAdapter.isEnabled());
        openBtn.setOnClickListener(new View.OnClickListener()  //开关蓝牙设备
        {
            @Override
            public void onClick(View view) {
                if (openBtn.isChecked()==true){
                    beacon.stopScan();
                    bluetoothAdapter.disable();
                }
                else if (openBtn.isChecked()==false){
                    bluetoothAdapter.enable();
                }
            }
        });

        locateMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schoolMap.return2MyLocation();
            }
        });
    }

    private void startLocate(){//发起定位
        locationClient=new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        MyLocationListener myLocationListener=new MyLocationListener(mapView);
        locationClient.registerLocationListener(myLocationListener);
        locationClient.start();
    }

    private void scanBeacon(Handler handler){
        if(bluetoothAdapter.isEnabled()) {
            scanView.setVisibility(View.VISIBLE);
            scanView.startAnimation(scanAnim);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(beacon.isScanning())
                        beacon.stopScan();
                    scanView.clearAnimation();
                    scanView.setVisibility(View.INVISIBLE);
                    double res=beacon.resultProcess();
                    if(res==0)
                        Toast.makeText(context, "附近没有线索",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "附近线索据您最近约"+String.format("%.2f",res)+"米",Toast.LENGTH_LONG).show();
                }
            }, beacon.SCAN_PERIOD);

            beacon.startScan();
        }
        else{
            if(beacon.isScanning())
                beacon.stopScan();
            else
                Toast.makeText(context, "蓝牙未打开",Toast.LENGTH_LONG).show();
        }
    }

}