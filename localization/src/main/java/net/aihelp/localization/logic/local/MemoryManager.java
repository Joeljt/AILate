package net.aihelp.localization.logic.local;

import android.util.SparseArray;

import net.aihelp.localization.data.model.LanguageData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JoeLjt on 2020/3/10.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class MemoryManager {

    private HashMap<String, String> mStringsData = new HashMap<>();

    private MemoryManager() { }

    private volatile static MemoryManager INSTANCE;

    public static MemoryManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MemoryManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MemoryManager();
                }
            }
        }
        return INSTANCE;
    }


    public void saveLanguageData(List<LanguageData.ResultsBean> languageList) {
        if (languageList != null && languageList.size() > 0) {
            for (LanguageData.ResultsBean languageData : languageList) {
                mStringsData.put(languageData.getCode(), languageData.getValue());
            }
        }
    }

    public String getString(String code) {
        return mStringsData.get(code);
    }

    public void saveString(String code, String data) {
        mStringsData.put(code, data);
    }

    public HashMap<String, String> getAll() {
        return mStringsData;
    }


}
