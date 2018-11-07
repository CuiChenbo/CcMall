package com.example.admin.ccb.utils;

import com.example.admin.ccb.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/3/6 14:17.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class ResCcb implements Serializable {
    public static String datas[] = {"对Kotlin的第一印象"
            ,"Android.Kotlin的杀手锏"
            ,"Kotlin 很简洁"
            ,"Kotlin 很擅长“拿来主义"
            ,"其实我也就是走马观花"
            ,"并且你也沉溺于 Java 曾经是一门非常优秀的语言的想法"
            ,"我特别喜欢Kotlin的地方有哪些？"
            ,"那么为什么要给这种语言起名叫Kotlin呢"};

    public static String datas1[] = {"说真的，其实我不想去攻击你们的语言信仰……至少不会“大大地”想。毕竟你喜欢的语言大放异彩的时期，可能要追溯到冰川时代了，对不？如果你喜欢的语言到今天还没有死，那只能说明这门语言在逐渐地改进和更新，保持与时俱进"
            ,"但改进的速度呢？好吧……假设你现在用的语言碰巧是 Java，并且你也沉溺于 Java 曾经是一门非常优秀的语言的想法，那么你就完蛋了。而且是早就完蛋了。尽管人类都不太喜欢思考终极命运问题，但是相比于在 20 多年前刚刚问世，Java 8 仅仅是做了部分语言特性的替换，对此你禁不住要想：“我真的要和这种语言来共度过的余生么？还是说 Java 也就只能这样了？”"
            ,"因为终于要开始做 Android 开发了，我把各种老旧的语言问题又过了一遍。我写过一个老游戏 Wyvern，这个游戏已经有一个 iOS 版本了，最近我决定再搞一个 Android 版本。我从来没料到计算机语言会折腾到让我“思考人生”（例如“我这是他娘的在浪费生命么？”）如果你写过 Android 程序的话，你就知道在 Android 领域，语言的问题是会让你相当难熬的。"
            ,"Android 确实有几个很糟糕的“红灯”API。例如 Fragments，这就是在 Andoird “红灯” API 中的招牌 API。这个 API 的整个生命周期的糟糕程度达到了令人发指的地步，好吧其实 Activities 和 Fragments 都是如此。说句不中听的，iOS 反而却没有这么糟糕的API。去年夏天我试了试这些API，它们是如此之糟糕以至于我当时就放弃了。我算是彻底服了。去它的，我还是以后找个人来帮我写这些程序吧。"
            ,"我一直听说，有一种新语言叫做 Kotlin，可以用来写基于 JVM 或者 Android 程序。发明这种语言的不是别国，正是战斗的民族俄罗斯。更具体一点儿，它是由 JetBrains 开发。JetBrains 可是世界知名的 IDE 开发商，代表作是 Intellij IDEA，还有他们那可爱而且大名鼎鼎的橘色，绿色，紫色和黑色混合的暗色“Darcula”主题。"
            ,"那么为什么要给这种语言起名叫 Kotlin 呢？好吧，有一种说法是因为 Java 的第一个字母是“J”，而 Kotlin 则用了Java 的下一个字母“K”作为开头。除此之外，有人还猜想（这种猜想可能来自加州大学伯克利分校），“Kotlin”这个名字的灵感还来源于“克里姆林宫”，“赫鲁晓夫”以及“克格勃”。这些都是前苏联的骄傲，所以他们就用了一个前苏联军事基地的名字“Kotlin”来命名这种语言。总之这个名字不错，而且你会习惯它的。"
            ,"我发现去年业界关于 Kotlin 只是有不少“嘀咕（buzz）”。注意，只是“嘀咕”，不是天花乱坠地吹牛宣传。人们只是低调地“嘀咕”着。好吧，总之，我当时看了一下，然后就觉得这个语言和我过去 15 年看过的 50 ~ 100 种计算机语言一样，这也是一门可以替代 Java 的语言，当然，我认为任何理性的语言都能替代 Java。"
            ,"我第一次看到 Kotlin 的时候，真心觉得这种语言不可能在现实生活中用到，真的是一点可能性都没有。其实我也就是走马观花。我的第一印象？这个语言也没什么大问题。它很简洁，也具有先进的特性。如果说它时髦也行，因为它几乎囊括了计算机语言设计上的所有最新潮流。不过这也没什么大不了，因为很多语言也都满足这一点。比如，Rust。Rust 也是一门健壮的，名字起得很好的，但是没什么人用的语言。"
            ,"Kotlin 给我的一种奇怪的感觉是“似曾相识”，后来我才反应过来，原来这是因为它和 Swift 很像。我之所以没有马上反应过来，是因为我的 iOS 程序因为历史代码的原因不得不用 Objective-C 而不是 Swift 来写。当然现在我也弄明白了：其实 Kotlin 的历史比 Swift 要悠久几年，所以正确的说法应该是：Swift 和 Kotlin 很像。"
            ,"Kotlin 很像 Java。它长得不像 Clojure 或者 Scala 那么奇怪（承认现实把，这两种语言就是挺奇怪的）。所以你学 Kotlin 应该很快。这门语言显然就是写给 Java 开发者来用的。"
            ,"它比 Java 更安全。Java 很多需要 annotation processors 来做的事情在 Kotlin 里则是内置的，例如 overriding，nullability等等。而且 Kotlin 对数值转换的规则也更安全，虽然我不太喜欢 Kotlin 的处理方式，但是我还是很感谢这门语言强制我去思考我的数值表现形式。"
            ,"Kotlin 和 Java 是可以互译的。真的是指无缝互译。我见过很多 JVM 语言最后挂掉，就是因为其不支持子类继承机制。我不知道什么时候就会用到静态内部类，非静态内部类，或者什么破玩意儿类。Kotlin 在设计时就把和Java互译的属性放在第一位，也就是说，把 Java 翻译到 Kotlin 可以逐渐进行，一次可以直接转换一个文件。"
            ,"Kotlin 很简洁。你要知道我也算是个高尔夫球手，所以我实话实说。如果在一切都相同的情况下，我更喜欢短的程序，只要它写的逻辑清楚。Kotlin 对我来说就像是打了一轮好局。平均下来我发现 Kotlin 的代码长度比相同逻辑的 Jython 代码短了 5%-10%（这可是我用某种“黄金标准”测出来的），而且还能保证程序的可读性和类型安全。"
            ,"Kotlin 更贴近实际。Kotlin 支持在一个文件里定义多个类，支持一等方法，操作符重载，扩展方法，类型别名，字符串模板，还有一堆看上去没什么新意的语言特性它都支持。而我就是不明白为什么 Java 什么都不支持，连人们需要的语言特性它都不做。"
            ,"Kotlin 进化很快。例如刚刚宣布启动的对 coroutine 的支持，将会成为提供 asyn/await， geneator 以及所有其他无锁并发特性的基础设施。"
            ,"Kotlin 很擅长“拿来主义”。Kotlin 经常从其他的计算机语言设计中抄点子，并且也毫不避讳。他们说：“我们很喜欢 C# 的处理方式，所以我们就自己照着撸了一个。 "
            ,"Kotlin 支持 DSL。DSL 不到深思熟虑万不得已的话千万不要引入，但是不得不承认 DSL 威力巨大。例如你看 Gradle 的 DSL 和 Maven 比，在典型的 Maven 项目里，配置代码肯定要超过上千行。所以 Kotlin 是来给你降工作压力的。"
            ,"Kotlin 的 IDE 是在是太棒了。刚才不久我才在 Emacs 里写程序文件，结果 Emacs 报了一对错。而我把同样的代码拷贝到 IntelliJ 里面，然后按 Alt-Enter 逐一自动修复了 50 多条，然后所有的错误就都搞定了。这真是帮了大忙啊"
            ,"Kotlin 很有趣。我跟你们说，Kotlin 就是有趣。也许这是我发自潜意识地在做广告。Kolint 的关键字和方法名都很有意思。Kotlin 把我从一个只会死编程的码农转变为了一个计算机语言的爱好者。"
            ,"我也知道还有好多其他的程序员也对 Kotlin 感觉非常好，打算成为 Kotlin 程序员。其中大部分的人应该在 1-2 年之内水平就能超过我了。我们私下里谈论过，互相都说“Kotlin 让编程感觉美好的感觉又回来了。”还不错，再我们尚未对编程全部失去兴趣之前，Kotlin 来了。就仿佛你只要把语言的语法学会了。这回把你带回到那种第一次学编程的感觉，无论写什么都觉得自己很牛逼。"
    };

    public static String bannerimages[] = {"http://img.hb.aicdn.com/6aea5f9b4c648aa76ed2df9a6ff90f337d7cef813b364-RWZdSO_fw658"
            ,"http://p1.so.qhimgs1.com/bdr/_240_/t018967c2e37c0f5171.jpg"
            ,"http://img.zcool.cn/community/0174af57a2d8750000012e7e1316bd.jpg"
            ,"http://qimg.hxnews.com/2018/0211/1518309063167.gif"
            ,"http://img.52z.com/upload/news/image/20180209/20180209100304_34383.jpeg"
            ,"http://img.zcool.cn/community/01e6d456a6e48932f875520f751355.jpg"
            ,"http://img.zcool.cn/community/01e56557a2d8c80000012e7eff209b.jpg"};
    public static String Goodsimages2[] = {"http://img003.hc360.cn/y2/M03/DF/FF/wKhQdFSkSiSECyMCAAAAAHXEJI0839.jpg"
    ,"http://img004.hc360.cn/k1/M08/22/76/wKhQwFmYISCEPbV4AAAAAMiiYwQ956.jpg"
    ,"http://img2.imgtn.bdimg.com/it/u=1908121152,4237511778&fm=26&gp=0.jpg"
    ,"http://m.360buyimg.com/mobilecms/s750x750_jfs/t19687/59/417334426/304006/a20286e3/5a784fe8Na00c5b20.jpg%21q80.jpg"
    ,"http://img14.360buyimg.com/n1/s350x449_jfs/t20146/171/580517645/463060/79e4110e/5b117504Naeadc702.jpg%21cc_350x449.jpg"};

    public static String searchLists[] = {"ins超火的裤子","猕猴桃 奇异果","香蕉","2018最新款牛仔裤","萌萌哒面包服","我的客服","时尚运动套装女春秋款","银丝亮边！拼接时尚运动款","谢","进口零食送女男友","脉动"};
    public static String menus[] = {"抢购","我的快递","记账本","天天有料","我的客服","智能设备","理财","奖励金","电影票","早晨"};
    public static String classifys[] = {"推荐分类","CB超市","国际名牌","奢侈品","全球购","男装","女装","童装","男鞋","女鞋","内衣配饰","箱包手袋","美妆个护","钟表珠宝","休闲养生"};
    public static Integer spcimangs[] = {R.mipmap.spc1,R.mipmap.spc2,R.mipmap.spc3,R.mipmap.spc4,R.mipmap.spc5,R.mipmap.spc6,R.mipmap.spc7,R.mipmap.spc8,R.mipmap.spc9};
    public static String spcGoodsImages[] = {"烟管裤修身正装休闲裤女九分黑色正装九分裤"
            ,"鹿皮绒高腰小西装裤子女九分阔腿裤侧边条纹"
            ,"显瘦休闲运动裤女春秋薄款直筒长裤宽松卫裤"
            ,"春季浅色格子裤女九分裤松紧腰捆绳宽松显瘦千鸟格哈伦休闲裤"
            ,"高腰牛仔裤女春秋2018新款ins超火的裤子韩版紧身小脚九分裤显瘦高腰chic弹力显瘦小脚 多色可选"
            ,"秋冬新款宽松毛呢小脚哈伦裤休闲裤女显瘦呢子长裤加厚款大码潮"
            ,"半高领针织打底衫长袖套头毛衣女秋冬装新款韩版宽松百搭短款春装提前双十二价，时尚新颖，保暖性好"
            ,"春秋季新款心型碎花松紧腰长袖中长款连衣裙女宽松显瘦波点裙"
            ,"运动服套装女春秋时尚2018新款修身韩版卫衣春季休闲跑步两件套潮"
            ,"职业装女装套装时尚西装连衣裙ol气质工作服女春秋正装套装裙"
            ,"2017春夏新款印花时尚休闲运动服套装女两件套韩版短袖卫衣长裤潮不掉色！不掉色！不掉色！重要事情说三遍！"
            ,"秋季卫衣女韩版学生百搭上衣宽松显瘦连帽2017时尚新款长袖外套潮"};
    public static String menuimages[] = {
            "http://p5.so.qhimgs1.com/bdr/_240_/t011ff8278aec8a891b.gif"
    ,"http://p4.so.qhmsg.com/bdr/_240_/t01292f9fb188c20f69.jpg"
    ,"http://p1.so.qhmsg.com/bdr/_240_/t01b92c845d492e5886.jpg"
    ,"http://p5.so.qhimgs1.com/bdr/_240_/t010aca8616434235bb.jpg"
    ,"http://p0.so.qhimgs1.com/bdr/_240_/t012d28581e47d578d0.jpg"
    ,"http://p0.so.qhimgs1.com/bdr/_240_/t01a88bab5da81aef10.gif"
    ,"http://img2.hao661.com/uploads/allimg/c140915/1410L50AE550-549523.gif"
    ,"http://p1.so.qhimgs1.com/bdr/_240_/t015ceec3767ce5cef1.png"
    ,"http://img2.hao661.com/uploads/allimg/c140915/1410L5063CI0-42L04.gif"
    ,"http://img2.hao661.com/uploads/allimg/c140915/1410L506450960-4I618.gif"
    ,"http://wenwen.soso.com/p/20111204/20111204131342-2059028019.jpg"
    ,"http://pic.qqtn.com/file/2013/2013-5/2013051515113135806.png"
    ,"http://pic.qqtn.com/up/2018-2/2018022311043218785.jpg"
    ,"http://p3.so.qhmsg.com/bdr/200_200_/t017ec6eccb7136a278.gif"
    ,"http://p0.so.qhimgs1.com/bdr/_240_/t011e39a1d0df3b1660.jpg"};

    public static String goodsimages[] = {"http://ent.k618.cn/ylzx/201702/W020170219339614751336.jpeg"
            ,"http://imgx.xiawu.com/xzimg/i4/i2/T1gBriFedXXXay0Ew__110720.jpg"
            ,"http://imgx.xiawu.com/xzimg/i4/i2/10063043534980002/T1djW2FctgXXXXXXXX_!!0-item_pic.jpg"
            ,"http://p4.so.qhmsg.com/bdr/_240_/t0180939e12903e61b9.jpg"
            ,"http://p0.so.qhmsg.com/bdr/_240_/t01fbf1c306595344e5.jpg"
            ,"http://img02.taobaocdn.com/imgextra/i2/729167156/T2mK4mXpNXXXXXXXXX_!!729167156.jpg"
            ,"http://p2.so.qhimgs1.com/bdr/_240_/t01f9d10c2e3fcfbdf2.jpg"
            ,"http://pic1.hebei.com.cn/0/14/36/61/14366114_938470.jpg"
            ,"http://d05.res.meilishuo.net/pic/_o/f3/4a/588b22ed8ff168725bb17336fad7_750_750.c6.jpeg"
            ,"http://p0.so.qhimgs1.com/t017d663d2b1894bc70.jpg"
            ,"http://p1.so.qhimgs1.com/bdr/_240_/t01cc1863a3ee1b97ed.jpg"
            ,"http://imgx.xiawu.com/xzimg/i4/i2/10511042027704180/T17NSHFm8bXXXXXXXX_!!0-item_pic.jpg"
            ,"http://img2.china-ef.com/news/2012/201211010251196.jpg"
            ,"http://d04.res.meilishuo.net/pic/_o/27/cf/4390bbc4f16da851747942421d4d_500_500.jpg"
            ,"http://cdnq.duitang.com/uploads/item/201505/03/20150503035125_yTuiC.jpeg"
            ,"http://npic7.fangtoo.com/cn/zixun/zh-chs/2018-01/03/424028-201801031848495331.jpg"
    ,"https://gdp.alicdn.com/imgextra/i2/263817957/TB2OtfibruWBuNjSszgXXb8jVXa-263817957.jpg"
    ,"https://img.alicdn.com/bao/uploaded/i1/1766047907/TB1rDxKetLO8KJjSZFxXXaGEVXa_!!0-item_pic.jpg_b.jpg"
    ,"https://img.alicdn.com/bao/uploaded/i1/2143439042/TB10nealBDH8KJjSszcXXbDTFXa_!!0-item_pic.jpg_b.jpg"
    ,"https://img.alicdn.com/bao/uploaded/i1/2970503593/TB1YwQjnf6H8KJjSspmXXb2WXXa_!!0-item_pic.jpg_b.jpg"
    ,"https://img.alicdn.com/bao/uploaded/i6/TB1nYsXkJfJ8KJjy0FeYXFKEXXa_M2.SS2_b.jpg"
    ,"https://img.alicdn.com/bao/uploaded/i2/2970503593/TB1tjIbhxTI8KJjSspiXXbM4FXa_!!0-item_pic.jpg_b.jpg"};


    public static List<String> getDatas(){
        return Arrays.asList(datas);
    }
    public static List<String> getDatas1(){
        return Arrays.asList(datas1);
    }
    public static List<String> getMenus(){
        return Arrays.asList(menus);
    }
    public static List<String> getMenuImages(){
        return Arrays.asList(menuimages);
    }
    public static List<String> getClassifys(){
        return Arrays.asList(classifys);
    }
    public static List<String> getBannerImages(){
        return Arrays.asList(bannerimages);
    }
    public static List<String> getGoodsImages(){
        return Arrays.asList(goodsimages);
    }
    public static List<Integer> getSpcImages(){
        return Arrays.asList(spcimangs);
    }
    public static List<String> getspcGoodsImages(){
        return Arrays.asList(spcGoodsImages);
    }
    public static List<String> getGoodsimages2(){
        return Arrays.asList(Goodsimages2);
    }
}
