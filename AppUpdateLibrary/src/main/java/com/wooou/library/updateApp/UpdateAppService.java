package com.wooou.library.updateApp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;


public class UpdateAppService extends IntentService implements IntentServiceConstant {


    public UpdateAppService() {
        super("UpdateAppService");
    }

    public static void startCheck(Context context, UpdateConfig config) {
        Intent intent = new Intent(context, UpdateAppService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_URL, config.url);
        intent.putExtra(EXTRA_DOWNLOAD_GPRS_ENABLED, config.download_gprs_enabled);
        intent.putExtra(EXTRA_DOWNLOAD_QUIET, config.download_success_notice);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_URL);
                final boolean param2 = intent.getBooleanExtra(EXTRA_DOWNLOAD_GPRS_ENABLED, false);
                final boolean param3 = intent.getBooleanExtra(EXTRA_DOWNLOAD_QUIET, false);
                handleActionUpdate(param1, param2, param3);
            }
        }
    }

    /**
     * Handle action Update in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdate(String url, boolean gprsDownload, boolean quietDownload) {
        if (url == null || url.length() < 2) return;
    }

    private boolean isWiFi() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    private String getNetworkType() {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                Log.e(LOG_TAG, "Network getSubtypeName : " + _strSubTypeName);
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
                Log.e(LOG_TAG, "Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }
        Log.e(LOG_TAG, "Network Type : " + strNetworkType);
        return strNetworkType;
    }
}
