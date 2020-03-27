package net.aihelp.localization.logic.local;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mmkv.MMKV;

import net.aihelp.localization.data.model.LanguageData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class FileUtil {


    public static String getFilePath(Context context) {
        return context.getExternalCacheDir() + File.separator + "language.json";
    }

    public static void saveToLocalKV(Context context, String filePath) {
        LanguageData languageData = JSONObject.parseObject(getJsonFromFile(filePath), LanguageData.class);

        if (languageData != null && languageData.getResults() != null) {
            MemoryManager.getInstance().saveLanguageData(languageData.getResults());

            for (LanguageData.ResultsBean language : languageData.getResults()) {
                MMKV.defaultMMKV().encode(String.valueOf(language.getCode()), language.getValue());
//                SpManager.getInstance(context).put(String.valueOf(language.getCode()), language.getValue());
            }
        } else {
            Log.e("ljt", "词条数据为空");
        }
    }

    public static boolean writeFileToDisk(Context context, InputStream ins) {
        try {
            File targetFile = new File(getFilePath(context));

            if (targetFile.exists()) {
                targetFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                inputStream = ins;
                outputStream = new FileOutputStream(targetFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) break;
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private static String getJsonFromFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempStr;
                while ((tempStr = reader.readLine()) != null) {
                    sb.append(tempStr);
                }
                reader.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return "";
    }

}
