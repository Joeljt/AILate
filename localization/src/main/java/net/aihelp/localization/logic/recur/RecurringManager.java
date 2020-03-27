package net.aihelp.localization.logic.recur;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import net.aihelp.localization.init.AIHelpConfig;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description: 轮询作业
 */
public class RecurringManager {

    private static final String SHARED_PREF_NAME = "net.aihelp.translation.config";
    private static final String TRANSLATION_SDK_CONFIG = "translation_sdk_config";
    private static final String WORKER_UUID = "worker_uuid";
    private static final String WORK_STATE = "work_key";
    private static final String WORK_STARTED = "started";
    private static final String WORK_CANCELED = "canceled";

    private static PeriodicWorkRequest downloadRequest;

    public static void setPeriodicUpdates(Context context, AIHelpConfig config) {
        if (WORK_STARTED.equals(getRecurringState(context))) return;

        saveConfig(context, config);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        downloadRequest = new PeriodicWorkRequest.Builder(
                DownloadWorker.class, 18 * 1000 * 60, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(downloadRequest);
        saveRecurringState(context, WORK_STARTED);
        saveJobId(context, downloadRequest.getId());

    }

    public static void cancel(Context context) {
        UUID jobId = getJobId(context);
        if (jobId != null) {
            WorkManager.getInstance(context).cancelWorkById(jobId);
            saveRecurringState(context, WORK_CANCELED);
        }
    }

    private static void saveJobId(Context context, UUID id) {
        String json = new Gson().toJson(id);
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(WORKER_UUID, json).apply();
    }

    private static UUID getJobId(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(WORKER_UUID, null);

        return value == null ? null : new Gson().fromJson(value, UUID.class);
    }

    private static void saveConfig(Context context, AIHelpConfig config) {
        String json = new Gson().toJson(config);
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(TRANSLATION_SDK_CONFIG, json).apply();
    }

    private static String getConfig(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Gson().fromJson(
                sharedPreferences.getString(TRANSLATION_SDK_CONFIG, ""),
                String.class
        );
    }

    private static void saveRecurringState(Context context, String state) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(WORK_STATE, state).apply();
    }

    private static String getRecurringState(Context context){
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(WORK_STATE, "");
    }

}
