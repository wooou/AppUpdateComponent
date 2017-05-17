package com.wooou.library.updateApp;

/**
 * Created by Lan Long on 2017/5/17 15:11.
 */
public interface BeanInterface {
    int getVersionCode();
    boolean isMustUpgrade();
    String getVersionName();
    String getDownloadUrl();
    String getDesc();
}
