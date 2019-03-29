package com.example.admin.ccb.bean;

import java.util.List;

public class DoubanBean {
    /**
     * count : 1
     * start : 25
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","历史","爱情"],"title":"乱世佳人","casts":[{"alt":"https://movie.douban.com/celebrity/1010506/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp"},"name":"费雯·丽","id":"1010506"},{"alt":"https://movie.douban.com/celebrity/1006997/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5289.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5289.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5289.webp"},"name":"克拉克·盖博","id":"1006997"},{"alt":"https://movie.douban.com/celebrity/1010604/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4239.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4239.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4239.webp"},"name":"奥利维娅·德哈维兰","id":"1010604"}],"collect_count":498755,"original_title":"Gone with the Wind","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1032275/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp"},"name":"维克多·弗莱明","id":"1032275"},{"alt":"https://movie.douban.com/celebrity/1010711/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19067.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19067.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19067.webp"},"name":"乔治·库克","id":"1010711"},{"alt":"https://movie.douban.com/celebrity/1012588/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p54831.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p54831.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p54831.webp"},"name":"山姆·伍德","id":"1012588"}],"year":"1939","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp"},"alt":"https://movie.douban.com/subject/1300267/","id":"1300267"}]
     * title : 豆瓣电影Top250
     */

    private int count;
    private int start;
    private int total;
    private String title;
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":9.2,"stars":"50","min":0}
         * genres : ["剧情","历史","爱情"]
         * title : 乱世佳人
         * casts : [{"alt":"https://movie.douban.com/celebrity/1010506/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp"},"name":"费雯·丽","id":"1010506"},{"alt":"https://movie.douban.com/celebrity/1006997/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5289.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5289.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5289.webp"},"name":"克拉克·盖博","id":"1006997"},{"alt":"https://movie.douban.com/celebrity/1010604/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4239.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4239.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4239.webp"},"name":"奥利维娅·德哈维兰","id":"1010604"}]
         * collect_count : 498755
         * original_title : Gone with the Wind
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1032275/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp"},"name":"维克多·弗莱明","id":"1032275"},{"alt":"https://movie.douban.com/celebrity/1010711/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19067.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19067.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19067.webp"},"name":"乔治·库克","id":"1010711"},{"alt":"https://movie.douban.com/celebrity/1012588/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p54831.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p54831.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p54831.webp"},"name":"山姆·伍德","id":"1012588"}]
         * year : 1939
         * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp"}
         * alt : https://movie.douban.com/subject/1300267/
         * id : 1300267
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<CastsBean> casts;
        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean {
            /**
             * max : 10
             * average : 9.2
             * stars : 50
             * min : 0
             */

            private int max;
            private double average;
            private String stars;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp
             * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp
             * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1963126880.webp
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1010506/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp"}
             * name : 费雯·丽
             * id : 1010506
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3151.webp
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1032275/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp"}
             * name : 维克多·弗莱明
             * id : 1032275
             */

            private String alt;
            private AvatarsBeanX avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBeanX getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBeanX avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBeanX {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p11303.webp
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
