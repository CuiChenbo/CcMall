package com.example.admin.ccb.bean;

import java.util.List;

public class GankBean {
    /**
     * error : false
     * results : [{"_id":"5c6a4ae99d212226776d3256","createdAt":"2019-02-18T06:04:25.571Z","desc":"2019-02-18","publishedAt":"2019-02-18T06:05:41.975Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg","used":true,"who":"lijinshanmx"},{"_id":"5c6385b39d21225dd7a417ce","createdAt":"2019-02-13T02:49:23.946Z","desc":"2019-02-13","publishedAt":"2019-02-13T02:49:33.16Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1g04lsmmadlj31221vowz7.jpg","used":true,"who":"lijinshanmx"},{"_id":"5c4578db9d212264d4501d40","createdAt":"2019-01-21T07:46:35.304Z","desc":"2019-01-21","publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fze94uew3jj30qo10cdka.jpg","used":true,"who":"lijinshanmx"},{"_id":"5c2dabdb9d21226e068debf9","createdAt":"2019-01-03T06:29:47.895Z","desc":"2019-01-03","publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg","used":true,"who":"lijinshanmx"},{"_id":"5c25db189d21221e8ada8664","createdAt":"2018-12-28T08:13:12.688Z","desc":"2018-12-28","publishedAt":"2018-12-28T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg","used":true,"who":"lijinshanmx"},{"_id":"5c12216d9d21223f5a2baea2","createdAt":"2018-12-13T09:07:57.2Z","desc":"2018-12-13","publishedAt":"2018-12-13T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg","used":true,"who":"lijinshanmx"},{"_id":"5bfe1a5b9d2122309624cbb7","createdAt":"2018-11-28T04:32:27.338Z","desc":"2018-11-28","publishedAt":"2018-11-28T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg","used":true,"who":"lijinshanmx"},{"_id":"5bf22fd69d21223ddba8ca25","createdAt":"2018-11-19T03:36:54.950Z","desc":"2018-11-19","publishedAt":"2018-11-19T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg","used":true,"who":"lijinshanmx"},{"_id":"5be14edb9d21223dd50660f8","createdAt":"2018-11-06T08:20:43.656Z","desc":"2018-11-06","publishedAt":"2018-11-06T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg","used":true,"who":"lijinshanmx"},{"_id":"5bcd71979d21220315c663fc","createdAt":"2018-10-22T06:43:35.440Z","desc":"2018-10-22","publishedAt":"2018-10-22T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg","used":true,"who":"lijinshanmx"},{"_id":"5bc434ac9d212279160c4c9e","createdAt":"2018-10-15T06:33:16.497Z","desc":"2018-10-15","publishedAt":"2018-10-15T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fw8wzdua6rj30sg0yc7gp.jpg","used":true,"who":"lijinshanmx"},{"_id":"5bbb0de09d21226111b86f1c","createdAt":"2018-10-08T07:57:20.978Z","desc":"2018-10-08","publishedAt":"2018-10-08T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg","used":true,"who":"lijinshanmx"},{"_id":"5ba206ec9d2122610aba3440","createdAt":"2018-09-19T08:21:00.295Z","desc":"2018-09-19","publishedAt":"2018-09-19T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fvexaq313uj30qo0wldr4.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b9771a29d212206c1b383d0","createdAt":"2018-09-11T07:41:22.491Z","desc":"2018-09-11","publishedAt":"2018-09-11T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fv5n6daacqj30sg10f1dw.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b830bba9d2122031f86ee51","createdAt":"2018-08-27T04:21:14.703Z","desc":"2018-08-27","publishedAt":"2018-08-28T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b7b836c9d212201e982de6e","createdAt":"2018-08-21T11:13:48.989Z","desc":"2018-08-21","publishedAt":"2018-08-21T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b74e9409d21222c52ae4cb4","createdAt":"2018-08-16T11:02:24.289Z","desc":"2018-08-16","publishedAt":"2018-08-16T00:00:00.0Z","source":"api","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg","used":true,"who":"lijinshan"},{"_id":"5b7102749d2122341d563844","createdAt":"2018-08-13T12:00:52.458Z","desc":"2018-08-13","publishedAt":"2018-08-13T00:00:00.0Z","source":"api","type":"福利","url":"https://ww1.sinaimg.cn/large/0065oQSqly1fu7xueh1gbj30hs0uwtgb.jpg","used":true,"who":"lijinshan"},{"_id":"5b6bad449d21226f45755582","createdAt":"2018-08-09T10:56:04.962Z","desc":"2018-08-09","publishedAt":"2018-08-09T00:00:00.0Z","source":"web","type":"福利","url":"https://ww1.sinaimg.cn/large/0065oQSqgy1fu39hosiwoj30j60qyq96.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b67b7fd9d2122195bdbd806","createdAt":"2018-08-06T10:52:45.809Z","desc":"2018-08-06","publishedAt":"2018-08-06T00:00:00.0Z","source":"api","type":"福利","url":"https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg","used":true,"who":"lijinshan"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5c6a4ae99d212226776d3256
         * createdAt : 2019-02-18T06:04:25.571Z
         * desc : 2019-02-18
         * publishedAt : 2019-02-18T06:05:41.975Z
         * source : web
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg
         * used : true
         * who : lijinshanmx
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        public int viewHeight;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
