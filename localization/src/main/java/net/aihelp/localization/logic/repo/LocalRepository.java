package net.aihelp.localization.logic.repo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.tencent.mmkv.MMKV;

import net.aihelp.localization.base.repo.BaseRepository;
import net.aihelp.localization.data.model.InitData;
import net.aihelp.localization.logic.local.FileUtil;
import net.aihelp.localization.logic.local.MemoryManager;
import net.aihelp.localization.logic.local.SpManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class LocalRepository extends BaseRepository {

    public static final String TOKEN_KEY = "translate_token_key";

    public LocalRepository(Context context) {
        super(context);
    }

    public void saveDataToLocal(InitData initData) {
        mSpManager.put(TOKEN_KEY, initData.getToken());
        mmkv.encode(TOKEN_KEY, initData.getToken());
    }

    public void loadLocalData() {
        String[] allKeys = MMKV.defaultMMKV().allKeys();
        if (allKeys != null && allKeys.length > 0) {
            for (String key : allKeys) {
                try {
                    MemoryManager.getInstance().saveString(key, MMKV.defaultMMKV().decodeString(key));
                } catch (Exception e) {
                }
            }
        }

        mSpManager.loadLocalData();
    }


    public void writeFileToDisk(InputStream inputStream) {
        FileUtil.writeFileToDisk(context, inputStream);
    }

    public void copyJsonToAndroidData() {
        FileUtil.saveToLocalKV(context, FileUtil.getFilePath(context));
    }

    public HashMap<String, String> getAllData() {
        return new HashMap<>(mMemoryManager.getAll());
    }

    public String getStringById(String code) {
        String memoryData = mMemoryManager.getString(code);
        if (!TextUtils.isEmpty(memoryData)) {
            return memoryData;
        }
        String localData = mSpManager.getString(String.valueOf(code));
        if (!TextUtils.isEmpty(localData)) {
            MemoryManager.getInstance().saveString(code, localData);
            return localData;
        }
        return "";
    }

    public String getCacheLocation() {
        return FileUtil.getFilePath(context);
    }
}
