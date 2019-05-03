package com.sse.util;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-05-03 16:39
 */

public interface DownloadListener {
    /**
     * 下载成功
     */
    void onDownloadSuccess();

    /**
     * @param progress 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载失败
     */
    void onDownloadFailed();
}
