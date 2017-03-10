#EntertainmentTribe
<img src="http://a2.qpic.cn/psb?/V13Up1yH3rFCOm/mF6cdXUyJxMAo6*pWsHHeWy.Jd4s9pvY4gX6nsE7cH0!/b/dLIAAAAAAAAA&bo=gAJyBAAAAAAFB9A!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a2.qpic.cn/psb?/V13Up1yH3rFCOm/9Dicjj1oV.RALq769GYWt7r7eZn*BKeNFnI1PTu0RjY!/b/dLIAAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a1.qpic.cn/psb?/V13Up1yH3rFCOm/*cMi22yL5hYxpyuoV8RMvmwt5smGPFob.jVV40uL9ok!/b/dCABAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a3.qpic.cn/psb?/V13Up1yH3rFCOm/eB5HoCYM2tvOj*pzZwJTj*neHsn4kqctT4ymDlrfgKA!/b/dB8BAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a3.qpic.cn/psb?/V13Up1yH3rFCOm/VuP5T0f5D3KSAF2YBCz0DY3HHUHQpxh..QZe.NJ4pYM!/b/dLAAAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a3.qpic.cn/psb?/V13Up1yH3rFCOm/q7YC0BSKNBMNJmr8qZ*piUEeoiVo3oe*Fa.hqFg4jBA!/b/dB8BAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a3.qpic.cn/psb?/V13Up1yH3rFCOm/hVC06g23mDQVB1xKWrBTH0dKbOOwVGnM30BJ0mXKkmk!/b/dPgAAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />
<img src="http://a1.qpic.cn/psb?/V13Up1yH3rFCOm/LqR7CiiAL0aKl7SBgNO7J1HWlX9ihLYF.MCA9938OEM!/b/dCABAAAAAAAA&bo=gAJyBAAAAAAFANc!&rf=viewer_4" alt="" height="384" width="216" />

本项目中引入了React Native，所以下载后请先配置RN环境。<br />
<br />
clone后请先下载react-native 包，具体操作为<br />
<br />
1.install node.js<br />
<br />
2.进入项目根目录（package.json文件所在目录），在命令中输入&nbsp;&nbsp; npm install 即可安装react-native相关包（这个包maven中本地仓）根目录的gradle中<br />
<p>
	<br />
</p>
<p>
	上面即为react-native的包，项目中涉及react-native的类都在maven本地仓中
</p>
解释：package.json 中为&nbsp;&nbsp; react-native的一些配置环境，里面包含了你要下载的react-native包的版本。<br />
<p>
	<br />
</p>
<p>
	assets/index.android.bundle&nbsp; 为启动项目的入口，在react-native项目中编译生成，编译方法：在react-native的根目录下输入下面命令
</p>
<p>
	<br />
</p>
<p>
	'react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output G:/bighero/EntertainmentTribe/app/src/main/assets/index.android.bundle --assets-dest
</p>
<p>
	G:/bighero/EntertainmentTribe/app/src/main/res'
</p>
<p>
	<br />
</p>
<p>
	<strong><span style="background-color:#E53333;font-size:14px;">本项目中使用了</span></strong> 
</p>
<p>
	1.Rxjava+Retrofit+OkHttp，并且设置了缓存拦截器
</p>
<p>
	2.PhotoView 进行了泛型T封装，传入一个list对象集合时，需要指定其要对象中url字段。适用于任何list对象数据。
</p>
<p>
	3.BGARefresh 下拉刷新，上拉加载。
</p>
<p>
	4.glide图片加载，自己比较喜欢使用何红辉的simpleImageloader，并且对其源码进行了详细的研究与拓展。
</p>
<p>
	5.circleimageview 圆形view
</p>
<p>
	6.greendao的使用
</p>
<p>
	7.butterknife&nbsp; 很方便的组件加载
</p>
<p>
	8.pinYin4j 引入，网上copy的2个方法，汉字转化为拼音首字母和全拼，公司项目中有使用过。
</p>
<p>
	9.log4j 日志
</p>
<p>
	10.debugBlockcanary，debugLeakcanary内存泄漏检测
</p>
<p>
	11.BaiduLBS_Android 百度地图相关功能的集成，公司项目中涉及到了，POI检索，GEO检索，附件检索，覆盖物生成，定位，附件场所等。
</p>
<p>
	12.TabLayout&nbsp; github上第一篇人气最高的框架。
</p>
<p>
	13.ActionSheetForAndroidLib&nbsp;&nbsp; 底部弹出框，并且对其进行了修改。
</p>
<p>
	14.rxandroid+rxjava+rxlifecycle+rxbinding，其中&nbsp; jakewharton的rxbinding没有使用。
</p>
<p>
	15.jsoup 爬虫，项目主要使用了jsoup框架。
</p>
<p>
	16.cardview
</p>
<p>
	17.multidex&nbsp;&nbsp;&nbsp; dex分割
</p>
<p>
	18.按照渠道打包方式
</p>
<p>
	19.系统版本通过设置在了文件中，gradle中通过读取文件获取文件版本（config.gradle,根目录下的build.gradle、app下的build.gradle中很多都值得大家学习）
</p>
<p>
	20.BaseRecyclerViewAdapter，亮点简化了代码，封装了ViewHolder，使用起来代码清晰，新浪微博的的recyclerview用法。（亮点）
</p>
<p>
	21.两个自定义组件，一个是弹性容器，一个是菊花loading。
</p>
<p>
	22.CrashExceptionHandler
</p>
<p>
	23.React配置接入，下一步计划应用到项目中。
</p>
<p>
	24.StateView
</p>
<p>
	25 各种Util配置 项目中的common文件
</p>
<p>	
<br />
</p>

