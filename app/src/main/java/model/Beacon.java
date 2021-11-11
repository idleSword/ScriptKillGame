package model;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Beacon {
    private ScanResult nearestResult;
    private BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    private BluetoothLeScanner mLeScanner=bluetoothAdapter.getBluetoothLeScanner();
    public static final long SCAN_PERIOD = 10000;
    private boolean scanning;
    private ScanSettings.Builder builder;
    private List<ScanResult> scanResults=new ArrayList<>();
    private List<String> macList=new ArrayList<>();
    private ArrayList<BeaconData> beaconDataList=new ArrayList<>();

    private ScanCallback leScanCallback=new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if(result.getDevice().getType()==2&&macList.indexOf(result.getDevice().getAddress())==-1) {
                scanResults.add(result);
                macList.add(result.getDevice().getAddress());
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            for(int i=0;i<results.size();i++){
                if(results.get(i).getDevice().getType()==2)
                {
                    scanResults.add(results.get(i));
                }
            }
        }
    };


    private BroadcastReceiver searchReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                //获取蓝牙广播中的蓝牙新状态
                int blueNewState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                //获取蓝牙广播中的蓝牙旧状态
                int blueOldState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueNewState) {
                    //正在打开蓝牙
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                    //蓝牙已打开
                    case BluetoothAdapter.STATE_ON://重新打开蓝牙后，重新设置LeScanner
                        mLeScanner=bluetoothAdapter.getBluetoothLeScanner();
                        break;
                    //正在关闭蓝牙
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    //蓝牙已关闭
                    case BluetoothAdapter.STATE_OFF:
                        break;
                }
            }
        }


    };

    public Beacon(){
        builder=new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_BALANCED);
        if(android.os.Build.VERSION.SDK_INT >= 23) {
            //定义回调类型
            builder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
            //设置蓝牙LE扫描滤波器硬件匹配的匹配模式
            builder.setMatchMode(ScanSettings.MATCH_MODE_STICKY);
        }
        if(bluetoothAdapter.isOffloadedScanBatchingSupported()){
            builder.setReportDelay(5000);
        }
        else
        {
            builder.setReportDelay(0L);
            System.out.println("不支持批处理");
        }
    }

    public BluetoothAdapter getAdapter(){
        return bluetoothAdapter;
    }

    public BroadcastReceiver getSearchReceiver(){
        return searchReceiver;
    }

    public void startScan(){
        scanning = true;
        scanResults.clear();
        macList.clear();
        beaconDataList.clear();
        System.out.println("进行了一次清除");
        mLeScanner.startScan(null, builder.build(), leScanCallback);
    }

    public void stopScan(){
        scanning = false;
        mLeScanner.stopScan(leScanCallback);
    }

    public double resultProcess(){
        if(scanResults==null||scanResults.isEmpty())
        {
            System.out.println("没找到");
            return 0;
        }

        else
        {
            double minDistance=Double.POSITIVE_INFINITY;
            for(int i=0;i<scanResults.size();i++)
            {
                BeaconData beaconData=new BeaconData(scanResults.get(i));
                beaconDataList.add(beaconData);
                if(beaconData.getDistance()>0)
                    minDistance=Math.min(minDistance, beaconData.getDistance());
                beaconData.printData();
            }
            return minDistance;
        }
    }

    public BluetoothDevice getDevice(){
        return nearestResult.getDevice();
    }

    public boolean isScanning(){
        return scanning;
    }
}
