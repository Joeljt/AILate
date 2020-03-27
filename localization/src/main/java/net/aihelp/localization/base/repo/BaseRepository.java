package net.aihelp.localization.base.repo;

import android.content.Context;

import com.tencent.mmkv.MMKV;

import net.aihelp.localization.logic.local.MemoryManager;
import net.aihelp.localization.logic.local.SpManager;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class BaseRepository {

    protected Context context;
    protected SpManager mSpManager;
    protected MMKV mmkv;
    protected MemoryManager mMemoryManager;

    public BaseRepository(Context context) {
        this.context = context;
        mSpManager = SpManager.getInstance(context);
        MMKV.initialize(context.getExternalCacheDir().getPath() + "/mmkv");
        mmkv = MMKV.defaultMMKV();
        mMemoryManager = MemoryManager.getInstance();
    }
}
