package com.green.dao.ui.model;

import java.io.Serializable;
import java.util.List;

public class TestVo implements Serializable {

    /**
     * total : 20
     * per_page : 10
     * current_page : 1
     * last_page : 2
     * data : [{"id":1,"video_id":"e7dc24a95786481d9e9b0fb044417913","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":10,"agree_count":5,"videos":{"Id":"e7dc24a95786481d9e9b0fb044417913","Cover":"http://vod.zydl-tec.cn/e7dc24a95786481d9e9b0fb044417913/snapshots/f3fa42dedf424b58a59300a082702194-00002.jpg","Title":"WeChat_20190130104436","VideoUrl":"http://vod.zydl-tec.cn/e7dc24a95786481d9e9b0fb044417913/39fc8ec1454f4c9e92ccadbaabc5bf0c-bff3bc26ef2edf38984c8c99a0b7c678-ld.mp4"}},{"id":2,"video_id":"5bfb2e2e568d4ef395a8aabbeb9eb88b","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":4,"agree_count":3,"videos":{"Id":"5bfb2e2e568d4ef395a8aabbeb9eb88b","Cover":"http://vod.zydl-tec.cn/5bfb2e2e568d4ef395a8aabbeb9eb88b/snapshots/010503bee39e4f7bb5e5dbe0bba41e7a-00001.jpg","Title":"WeChat_20190130104407","VideoUrl":"http://vod.zydl-tec.cn/5bfb2e2e568d4ef395a8aabbeb9eb88b/61bed86a6759422eac1cabcf57fabad3-e7bc999548da7554dacc7e8a0e69d5af-ld.mp4"}},{"id":3,"video_id":"0052a8bf857c45e3ac6703c57870f191","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":3,"agree_count":1,"videos":{"Id":"0052a8bf857c45e3ac6703c57870f191","Cover":"http://vod.zydl-tec.cn/0052a8bf857c45e3ac6703c57870f191/snapshots/610237eb753846458cf016e6acf030e5-00001.jpg","Title":"WeChat_20190130104401","VideoUrl":"http://vod.zydl-tec.cn/0052a8bf857c45e3ac6703c57870f191/d9393186a89b4ec9a435814e5702e98c-be6d1fd643a1dc3c0414382ae405b3dc-ld.mp4"}},{"id":4,"video_id":"7cfa73352ed74e8b80836bc5bb1f16a1","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":2,"agree_count":2,"videos":{"Id":"7cfa73352ed74e8b80836bc5bb1f16a1","Cover":"http://vod.zydl-tec.cn/7cfa73352ed74e8b80836bc5bb1f16a1/snapshots/1e52c44839b440c0a673af31362a7494-00001.jpg","Title":"WeChat_20190130104410","VideoUrl":"http://vod.zydl-tec.cn/7cfa73352ed74e8b80836bc5bb1f16a1/1527c4166de94ca887879d4be33121ae-df0a662ae4fff7a8a4a6e09a3a066b85-ld.mp4"}},{"id":5,"video_id":"1b33b164fcbf4073b400da0ce5b96226","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":1,"agree_count":3,"videos":{"Id":"1b33b164fcbf4073b400da0ce5b96226","Cover":"http://vod.zydl-tec.cn/1b33b164fcbf4073b400da0ce5b96226/snapshots/bd91da51e09041d8ac65566b8600acc5-00002.jpg","Title":"WeChat_20190130104424","VideoUrl":"http://vod.zydl-tec.cn/1b33b164fcbf4073b400da0ce5b96226/a6d7c8433dd04418a2c609fc51c95b3f-2bc63b54471c298c081faa2fd18509f2-ld.mp4"}},{"id":6,"video_id":"b0994380fdc346479c00bb46206ad1be","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":1,"agree_count":2,"videos":{"Id":"b0994380fdc346479c00bb46206ad1be","Cover":"http://vod.zydl-tec.cn/b0994380fdc346479c00bb46206ad1be/snapshots/1f026730509f46588649ad7eca870215-00002.jpg","Title":"WeChat_20190130104419","VideoUrl":"http://vod.zydl-tec.cn/b0994380fdc346479c00bb46206ad1be/f7a85606ee1c403f8d9252c1d8fb38bf-99f6c50803cf2f315658f307393ea599-ld.mp4"}},{"id":7,"video_id":"7cc34bbe3539410e9f7912fdd47f4e38","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":2,"agree_count":2,"videos":{"Id":"7cc34bbe3539410e9f7912fdd47f4e38","Cover":"http://vod.zydl-tec.cn/7cc34bbe3539410e9f7912fdd47f4e38/snapshots/7ecb265edcc749388c61a36ffd2b855f-00002.jpg","Title":"WeChat_20190130095319","VideoUrl":"http://vod.zydl-tec.cn/7cc34bbe3539410e9f7912fdd47f4e38/b8aac49de36e4ee98c0811b77150fd8a-69c9794476420947220029b2bec973cc-ld.mp4"}},{"id":8,"video_id":"08a2a4213a3c46efbc499ed266d51306","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":18,"agree_count":2,"videos":{"Id":"08a2a4213a3c46efbc499ed266d51306","Cover":"http://vod.zydl-tec.cn/08a2a4213a3c46efbc499ed266d51306/snapshots/5a9fc8ebeb4745a2afa43a9d44c80ac9-00001.jpg","Title":"WeChat_20190204192854","VideoUrl":"http://vod.zydl-tec.cn/08a2a4213a3c46efbc499ed266d51306/066763c9398f4da696eef048d508ebe1-431a5af8a9eb0aaa6832f7117e963cdf-ld.mp4"}},{"id":9,"video_id":"9c70d0b62b814682abadda7d632f4706","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":3,"agree_count":2,"videos":{"Id":"9c70d0b62b814682abadda7d632f4706","Cover":"http://vod.zydl-tec.cn/9c70d0b62b814682abadda7d632f4706/snapshots/ee750ad7b0c24209af3fef5f32c016bd-00001.jpg","Title":"WeChat_20190204192849","VideoUrl":"http://vod.zydl-tec.cn/9c70d0b62b814682abadda7d632f4706/f499024f4dc449baa55dee5846cf6505-c5c27a7c755d7a5a25f8872f80002ae9-ld.mp4"}},{"id":10,"video_id":"72558f6ff74a45f899d29ee4a56e57d6","title":null,"content":null,"user_id":0,"create_time":"2019-03-04 10:30:21","delete_time":null,"user_agree":0,"user_flag":0,"reply_count":5,"agree_count":1,"videos":{"Id":"72558f6ff74a45f899d29ee4a56e57d6","Cover":"http://vod.zydl-tec.cn/72558f6ff74a45f899d29ee4a56e57d6/snapshots/1a54f2d0b6704699a5919035d706921d-00001.jpg","Title":"WeChat_20190204192845","VideoUrl":"http://vod.zydl-tec.cn/72558f6ff74a45f899d29ee4a56e57d6/c10bf4273f4c4722a7e08239f1863012-63b54bfa7a0de068a5702e780b37d43b-ld.mp4"}}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * video_id : e7dc24a95786481d9e9b0fb044417913
         * title : null
         * content : null
         * user_id : 0
         * create_time : 2019-03-04 10:30:21
         * delete_time : null
         * user_agree : 0
         * user_flag : 0
         * reply_count : 10
         * agree_count : 5
         * videos : {"Id":"e7dc24a95786481d9e9b0fb044417913","Cover":"http://vod.zydl-tec.cn/e7dc24a95786481d9e9b0fb044417913/snapshots/f3fa42dedf424b58a59300a082702194-00002.jpg","Title":"WeChat_20190130104436","VideoUrl":"http://vod.zydl-tec.cn/e7dc24a95786481d9e9b0fb044417913/39fc8ec1454f4c9e92ccadbaabc5bf0c-bff3bc26ef2edf38984c8c99a0b7c678-ld.mp4"}
         */

        private int id;
        private String video_id;
        private Object title;
        private Object content;
        private int user_id;
        private String create_time;
        private Object delete_time;
        private int user_agree;
        private int user_flag;
        private int reply_count;
        private int agree_count;
        private VideosBean videos;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public int getUser_agree() {
            return user_agree;
        }

        public void setUser_agree(int user_agree) {
            this.user_agree = user_agree;
        }

        public int getUser_flag() {
            return user_flag;
        }

        public void setUser_flag(int user_flag) {
            this.user_flag = user_flag;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public int getAgree_count() {
            return agree_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public VideosBean getVideos() {
            return videos;
        }

        public void setVideos(VideosBean videos) {
            this.videos = videos;
        }

        public static class VideosBean {
            /**
             * Id : e7dc24a95786481d9e9b0fb044417913
             * Cover : http://vod.zydl-tec.cn/e7dc24a95786481d9e9b0fb044417913/snapshots/f3fa42dedf424b58a59300a082702194-00002.jpg
             * Title : WeChat_20190130104436
             * VideoUrl : http://vod.zydl-tec.cn/e7dc24a95786481d9e9b0fb044417913/39fc8ec1454f4c9e92ccadbaabc5bf0c-bff3bc26ef2edf38984c8c99a0b7c678-ld.mp4
             */

            private String Id;
            private String Cover;
            private String Title;
            private String VideoUrl;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getCover() {
                return Cover;
            }

            public void setCover(String Cover) {
                this.Cover = Cover;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getVideoUrl() {
                return VideoUrl;
            }

            public void setVideoUrl(String VideoUrl) {
                this.VideoUrl = VideoUrl;
            }
        }
    }
}
