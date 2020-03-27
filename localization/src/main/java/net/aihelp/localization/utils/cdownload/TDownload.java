package net.aihelp.localization.utils.cdownload;

import android.util.Log;

import net.aihelp.localization.utils.cdownload.config.CDownloadConfig;
import net.aihelp.localization.utils.cdownload.constants.ExecutorConstant;
import net.aihelp.localization.utils.cdownload.constants.TypeConstant;
import net.aihelp.localization.utils.cdownload.entity.CDownloadTaskEntity;
import net.aihelp.localization.utils.cdownload.listener.CDownloadListener;
import net.aihelp.localization.utils.cdownload.manager.download.FileManager;
import net.aihelp.localization.utils.cdownload.manager.executor.ExecutorManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * Created by zero on 2018/4/26.
 *
 * @author zero
 */

public class TDownload {

    private static TDownload cDownload = new TDownload();
    private CDownloadConfig downloadConfig;
    private CDownloadTaskEntity mTask;

    private TDownload() {
    }

    public static TDownload getInstance() {
        return cDownload;
    }

    public TDownload init(CDownloadConfig downloadConfig) {
        this.downloadConfig = downloadConfig;
        ExecutorManager.init(downloadConfig.getIoThreadPoolConfig());
        FileManager.init(downloadConfig);
        return this;
    }

    public TDownload create(String url, CDownloadListener downloadListener) {
        create(url, TypeConstant.THREAD_POOL_TYPE_IO, ExecutorConstant.SINGLE_THREAD_POOL_TYPE_DEFAULE, downloadListener);
        return this;
    }

    public TDownload create(String url, int threadPoolType, CDownloadListener downloadListener) {
        create(url, threadPoolType, ExecutorConstant.SINGLE_THREAD_POOL_TYPE_DEFAULE, downloadListener);
        return this;
    }

    private TDownload create(String url, int threadPoolType, String singleThreadPoolKey, CDownloadListener downloadListener) {
        CDownloadTaskEntity newTask = new CDownloadTaskEntity(url, downloadListener, threadPoolType, singleThreadPoolKey);
        mTask = newTask;
        return this;
    }

    public void create(CDownloadTaskEntity downloadTaskEntity) {
        if (downloadTaskEntity == null) {
            return;
        }
        create(downloadTaskEntity.getUrl(), downloadTaskEntity.getThreadPoolType(), downloadTaskEntity.getSingleThreadPoolKey(), downloadTaskEntity.getDownloadListener());
    }

    public void start() {
        if (mTask == null) {
            return;
        }

        Executor executorService = ExecutorManager.getExecutor(mTask.getThreadPoolType(), mTask.getSingleThreadPoolKey());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                FileManager.startDownload(mTask);
                stop();
            }
        });
    }

    public void stop(){
        if (mTask == null) {
            return;
        }
        mTask.setHasCancel(true);
        mTask = null;
    }

}
