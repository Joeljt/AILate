package net.aihelp.localization.logic.recur;

import android.content.Context;
import android.util.Log;

import net.aihelp.localization.init.AIHelpTranslate;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class DownloadWorker extends Worker {

    public DownloadWorker(Context context, WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("AI_HELP", "doWork" );
        AIHelpTranslate.getInstance().forceUpdate();
        return Result.success();
    }

}
