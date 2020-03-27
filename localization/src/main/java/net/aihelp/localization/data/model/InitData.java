package net.aihelp.localization.data.model;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class InitData {

    /**
     * flag : true
     * code : 0
     * desc :
     * data : {"translateFile":"https://local.aihelp.net/Elva/translate/5db4a87c535848be5b711e4e73f61713.json","cdnUrl":"https://local.aihelp.net/Elva"}
     * url :
     * remark : null
     * token : null
     * time : 1583891709866
     */

    private boolean flag;
    private int code;
    private String desc;
    private DataBean data;
    private String url;
    private String remark;
    private String token;
    private long time;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static class DataBean {
        /**
         * translateFile : https://local.aihelp.net/Elva/translate/5db4a87c535848be5b711e4e73f61713.json
         * cdnUrl : https://local.aihelp.net/Elva
         */

        private String translateFile;
        private String cdnUrl;

        public String getTranslateFile() {
            return translateFile;
        }

        public void setTranslateFile(String translateFile) {
            this.translateFile = translateFile;
        }

        public String getCdnUrl() {
            return cdnUrl;
        }

        public void setCdnUrl(String cdnUrl) {
            this.cdnUrl = cdnUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "translateFile='" + translateFile + '\'' +
                    ", cdnUrl='" + cdnUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "InitData{" +
                "flag=" + flag +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                ", token='" + token + '\'' +
                ", time=" + time +
                '}';
    }
}
