package com.green.dao.ui.model;

public class UpdateAppVo {

    /**
     * data : {"id":3,"num":"1.0.2","url":"http://www.baidu.com","content":"哈哈哈哈","is_must_update":0,"create_time":"2018-11-26 10:34:08","delete_time":null,"client":"android"}
     * info : ok
     * status : 1
     */

    private DataBean data;
    private String info;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * id : 3
         * num : 1.0.2
         * url : http://www.baidu.com
         * content : 哈哈哈哈
         * is_must_update : 0
         * create_time : 2018-11-26 10:34:08
         * delete_time : null
         * client : android
         */

        private int id;
        private String num;
        private String url;
        private String content;
        private int is_must_update;
        private String create_time;
        private Object delete_time;
        private String client;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_must_update() {
            return is_must_update;
        }

        public void setIs_must_update(int is_must_update) {
            this.is_must_update = is_must_update;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }
    }
}
