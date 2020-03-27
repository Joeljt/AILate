package net.aihelp.localization.logic.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

public class SpManager {

    public static final String FILE_NAME = "ai_help_share_data";

    private Context context;

    private SpManager(Context context) {
        this.context = context;
    }

    private volatile static SpManager INSTANCE;

    public static SpManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpManager(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取json
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getJson(String key, Class<T> clazz) {
        String value = getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        return JSONObject.parseObject(value, clazz);
    }

    /**
     * 保存json
     *
     * @param key
     * @param object
     */
    public void putJson(String key, Object object) {
        put(key, JSON.toJSON(object));
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (object == null) return;

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();
    }


    public long getLong(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, 0);
    }

    public long getLong(String key, long defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultObject);
    }

    public float getFloat(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, 0f);
    }

    public float getFloat(String key, float defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultObject);
    }

    public int getInt(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public int getInt(String key, int defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultObject);
    }

    public boolean getBoolean(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultObject);
    }

    public String getString(String key, String defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultObject);
    }

    public String getString(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).apply();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public void loadLocalData() {
        Map<String, ?> languageMap = getAll();
        Set<String> keySet = languageMap.keySet();

        if (keySet.size() > 0) {
            for (String key : keySet) {
                MemoryManager.getInstance().saveString(key, (String) languageMap.get(key));
            }
        }

    }

}
