package net.aihelp.localization.data.model;

import java.util.List;

/**
 * Created by JoeLjt on 2020/3/10.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class LanguageData {

    /**
     * IsRefresh : 1
     * Results : [{"code":"83001049","value":"开启功能：联盟军备222"},{"code":"133170","value":"在联盟中援助盟友{0}部队1次22。."},{"code":"76211108","value":"开启后获得一件蓝色的守护者手套222."},{"code":"104453","value":"75k 军队222扩充。"},{"code":"76211228","value":"开启后获得一件蓝色的守护22者手套."},{"code":"1042253","value":"75k 军222队扩充。"}]
     */

    private int IsRefresh;
    private List<ResultsBean> Results;

    public int getIsRefresh() {
        return IsRefresh;
    }

    public void setIsRefresh(int IsRefresh) {
        this.IsRefresh = IsRefresh;
    }

    public List<ResultsBean> getResults() {
        return Results;
    }

    public void setResults(List<ResultsBean> Results) {
        this.Results = Results;
    }

    public static class ResultsBean {
        /**
         * code : 83001049
         * value : 开启功能：联盟军备222
         */

        private String code;
        private String value;
        private String languageCode;

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "code='" + code + '\'' +
                    ", value='" + value + '\'' +
                    ", languageCode='" + languageCode + '\'' +
                    '}';
        }
    }
}
