package com.wooou.library.updateApp;

/**
 * Created by Lan Long on 2017/5/17 15:30.
 * 检测升级配置
 */
public class UpdateConfig {

    protected String url;
    protected boolean download_gprs_enabled = false;
    //先下载成功,再提示升级
    protected boolean download_success_notice = true;
    protected int download_thread_count = 10;
    protected final static int DOWNLOAD_THREAD_COUNT_DEFAULT = 10;
    protected int download_fail_retry = 5;
    protected final static int DOWNLOAD_FAIL_RETRY_DEFAULT = 0;
    protected final static int DOWNLOAD_FAIL_RETRY_MAX = 66;
    protected boolean download_fail_retry_notice = true;

    private FormatType formatType = FormatType.JSON;

    private UpdateConfig(String url) {
        this.url = url;
    }


    public static class Builder {

        private UpdateConfig config;


        public Builder(String url) {
            config = new UpdateConfig(url);
        }

        public UpdateConfig build(){
            return config;
        }

        /**
         * 是否允许在GPRS数据网络下 下载APK
         *
         * @return
         */
        public Builder withGrps() {
            config.download_gprs_enabled = true;
            return this;
        }

        /**
         * 下载线程数量
         *
         * @param number
         * @return
         */
        public Builder thread(int number) {
            if (number <= 0 || number > DOWNLOAD_THREAD_COUNT_DEFAULT) {
                config.download_thread_count = DOWNLOAD_THREAD_COUNT_DEFAULT;
            } else {
                config.download_thread_count = number;
            }
            return this;
        }

        /**
         * 重试次数
         *
         * @param number
         * @return
         */
        public Builder retry(int number) {
            if (number <= -1 || number > DOWNLOAD_FAIL_RETRY_MAX) {
                config.download_fail_retry = DOWNLOAD_FAIL_RETRY_DEFAULT;
            } else {
                config.download_fail_retry = number;
            }
            return this;
        }

        /**
         * 下载失败,重试之前先提示
         *
         * @return
         */
        public Builder noticeAfter() {
            config.download_fail_retry_notice = true;
            return this;
        }

        /**
         * 下载失败,重试之前先提示
         *
         * @return
         */
        public Builder noticeBeforeRetry() {
            config.download_success_notice = true;
            return this;
        }


        /**
         * 服务器返回的数据格式
         *
         * @return
         */
        public Builder xml() {
            config.formatType = FormatType.XML;
            return this;
        }
    }
}
