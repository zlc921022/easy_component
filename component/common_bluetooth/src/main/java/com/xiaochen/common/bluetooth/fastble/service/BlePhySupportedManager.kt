package com.xiaochen.common.bluetooth.fastble.service;

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertisingSet
import android.bluetooth.le.AdvertisingSetCallback
import android.bluetooth.le.AdvertisingSetParameters
import android.os.Build
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*


/**
 * <p>蓝牙 5 公告功能扩展{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/4
 */
object BlePhySupportedManager {

    var currentAdvertisingSet: AdvertisingSet? = null

    /**
     * 如果支持蓝牙5.0 则开启公告功能扩展
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun le2PhySupport(adapter: BluetoothAdapter) {
        val LOG_TAG = "MainActivity"
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val advertiser = adapter.bluetoothLeAdvertiser

        // Check if all features are supported
        // Check if all features are supported
        if (!adapter.isLe2MPhySupported) {
            Log.e(LOG_TAG, "2M PHY not supported!")
            return
        }

        if (!adapter.isLeExtendedAdvertisingSupported) {
            Log.e(LOG_TAG, "LE Extended Advertising not supported!")
            return
        }

        val parameters = AdvertisingSetParameters.Builder()
            .setLegacyMode(false)
            .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
            .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
            .setPrimaryPhy(BluetoothDevice.PHY_LE_2M)
            .setSecondaryPhy(BluetoothDevice.PHY_LE_2M)

        val data = AdvertiseData.Builder().addServiceData(
            ParcelUuid(UUID.randomUUID()),
            ("You should be able to fit large amounts of data up to maxDataLength. " +
                    "This goes up to 1650 bytes. For legacy advertising this would not work").toByteArray()
        ).build()

        var callback: AdvertisingSetCallback? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            callback = object : AdvertisingSetCallback() {
                override fun onAdvertisingSetStarted(
                    advertisingSet: AdvertisingSet,
                    txPower: Int,
                    status: Int
                ) {
                    Log.i(
                        LOG_TAG, "onAdvertisingSetStarted(): txPower:" + txPower + " , status: "
                                + status
                    )
                    currentAdvertisingSet = advertisingSet
                }

                override fun onAdvertisingSetStopped(advertisingSet: AdvertisingSet) {
                    Log.i(LOG_TAG, "onAdvertisingSetStopped():")
                }
            }
        }

        advertiser.startAdvertisingSet(parameters.build(), data, null, null, null, callback)

        if (currentAdvertisingSet == null) {
            return
        }

        // After the set starts, you can modify the data and parameters of currentAdvertisingSet.
        // After the set starts, you can modify the data and parameters of currentAdvertisingSet.
        currentAdvertisingSet?.setAdvertisingData(
            AdvertiseData.Builder().addServiceData(
                ParcelUuid(UUID.randomUUID()),
                ("Without disabling the advertiser first, you can set the data, if new data is " +
                        "less than 251 bytes long.").toByteArray()
            ).build()
        )

        // Wait for onAdvertisingDataSet callback...

        // Can also stop and restart the advertising
        // Wait for onAdvertisingDataSet callback...
        // Can also stop and restart the advertising
        currentAdvertisingSet?.enableAdvertising(false, 0, 0)
        // Wait for onAdvertisingEnabled callback...
        // Wait for onAdvertisingEnabled callback...
        currentAdvertisingSet?.enableAdvertising(true, 0, 0)
        // Wait for onAdvertisingEnabled callback...

        // Or modify the parameters - i.e. lower the tx power
        // Wait for onAdvertisingEnabled callback...
        // Or modify the parameters - i.e. lower the tx power
        currentAdvertisingSet?.enableAdvertising(false, 0, 0)
        // Wait for onAdvertisingEnabled callback...
        // Wait for onAdvertisingEnabled callback...
        currentAdvertisingSet?.setAdvertisingParameters(
            parameters.setTxPowerLevel(
                AdvertisingSetParameters.TX_POWER_LOW
            ).build()
        )
        // Wait for onAdvertisingParametersUpdated callback...
        // Wait for onAdvertisingParametersUpdated callback...
        currentAdvertisingSet?.enableAdvertising(true, 0, 0)
        // Wait for onAdvertisingEnabled callback...

        // When done with the advertising:
        // Wait for onAdvertisingEnabled callback...
        // When done with the advertising:
        advertiser.stopAdvertisingSet(callback)
    }
}