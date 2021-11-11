package model;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.util.Log;

public class BeaconData {
    String uuid;
    String mac;
    String name;
    int major;
    int minor;
    int rssi;
    int txPower;
    double distance;
    public BeaconData(ScanResult result){
        resolveADPackage(result);
    }
    void resolveADPackage(ScanResult result){
        BluetoothDevice device=result.getDevice();
        byte[] scanRecord=result.getScanRecord().getBytes();
        int startByte = 2;
        boolean patternFound = false;
        // 寻找ibeacon
        while (startByte <= 5) {
            if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && // Identifies
                    // an
                    // iBeacon
                    ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { // Identifies
                // correct
                // data
                // length
                patternFound = true;
                break;
            }
            startByte++;
        }
        // 如果找到了的话
        if (patternFound) {
            // 转换为16进制
            byte[] uuidBytes = new byte[16];
            System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
            String hexString = bytesToHex(uuidBytes);

            // ibeacon的UUID值
            uuid = hexString.substring(0, 8) + "-"
                    + hexString.substring(8, 12) + "-"
                    + hexString.substring(12, 16) + "-"
                    + hexString.substring(16, 20) + "-"
                    + hexString.substring(20, 32);

            // ibeacon的Major值
            major = (scanRecord[startByte + 20] & 0xff) * 0x100
                    + (scanRecord[startByte + 21] & 0xff);

            // ibeacon的Minor值
            minor = (scanRecord[startByte + 22] & 0xff) * 0x100
                    + (scanRecord[startByte + 23] & 0xff);

            name = device.getName();
            mac = device.getAddress();
            txPower = (scanRecord[startByte + 24]);
            rssi=result.getRssi();
            distance= calculateAccuracy(txPower, rssi);
        }
    }

    public void printData(){
        Log.d("BLE", "Name：" + name + "\nMac：" + mac
                + " \nUUID：" + uuid + "\nMajor：" + major + "\nMinor："
                + minor + "\nTxPower：" + txPower + "\nrssi：" +rssi+"\nDistance:"+distance);
    }
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {//转16进制字符串
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static double calculateAccuracy(int txPower, double rssi) {//计算距离
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    public double getDistance(){
        return distance;
    }

}
