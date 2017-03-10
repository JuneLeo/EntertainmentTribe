#EntertainmentTribe
本项目中引入了React Native，所以下载后请先配置RN环境。

clone后请先下载react-native 包，具体操作为

1.install node.js

2.进入项目根目录（package.json文件所在目录），在命令中输入   npm install 即可安装react-native相关包（这个包maven中本地仓）根目录的gradle中

maven {
            // All of React Native (JS, Android binaries) is installed from npm
            url "$rootDir/node_modules/react-native/android"
        }
上面即为react-native的包，项目中涉及react-native的类都在maven本地仓中



解释：package.json 中为   react-native的一些配置环境，里面包含了你要下载的react-native包的版本。

assets/index.android.bundle  为启动项目的入口，在react-native项目中编译生成，编译方法：在react-native的根目录下输入下面命令

react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output G:/bighero/EntertainmentTribe/app/src/main/assets/index.android.bundle --assets-dest G:/bighero/EntertainmentTribe/app/src/main/res
本项目中使用了
1.Rxjava+Retrofit+OkHttp，并且设置了缓存拦截器
2.PhotoView 进行了泛型T封装，传入一个list对象集合时，需要指定其要对象中url字段。适用于任何list对象数据。
3.BGARefresh 下拉刷新，上拉加载。
4.glide图片加载，自己比较喜欢使用何红辉的simpleImageloader，并且对其源码进行了详细的研究与拓展。
5.circleimageview 圆形view
6.greendao的使用
7.butterknife  很方便的组件加载
8.pinYin4j 引入，网上copy的2个方法，汉字转化为拼音首字母和全拼，公司项目中有使用过。
9.log4j 日志
10.debugBlockcanary，debugLeakcanary内存泄漏检测
11.BaiduLBS_Android 百度地图相关功能的集成，公司项目中涉及到了，POI检索，GEO检索，附件检索，覆盖物生成，定位，附件场所等。
12.TabLayout  github上第一篇人气最高的框架。
13.ActionSheetForAndroidLib   底部弹出框，并且对其进行了修改。
14.rxandroid+rxjava+rxlifecycle+rxbinding，其中  jakewharton的rxbinding没有使用。
15.jsoup 爬虫，项目主要使用了jsoup框架。
16.cardview
17 multidex    dex分割
18.按照渠道打包方式

