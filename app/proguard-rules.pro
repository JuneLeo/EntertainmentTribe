# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

##optimizationpasses表示对代码进行迭代优化的次数，optimization可以对代码进行各种优化，每次优化后还可以继续优化，故称之迭代优化；
#-optimizationpasses 5
##混淆时不产生混合大小写的类名
#-dontusemixedcaseclassnames
##指定不去忽略非公共的库类
#-dontskipnonpubliclibraryclasses
##不预校验
#-dontpreverify
#
##显示混淆的log，帮助排错
#-verbose
#
##代码混淆采用的算法，一般不改变，使用谷歌默认算法即可
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
##如果项目中有用到注释，则加入
#-keepattributes *Annotation*
#-keepattributes Signature
#
##用到的相关支持包
#-libraryjars libs/apns_1.0.6.jar
#-libraryjars libs/armeabi/libBaiduMapSDK_v2_3_1.so
#-libraryjars libs/armeabi/liblocSDK4.so
#-libraryjars libs/baidumapapi_v2_3_1.jar
#-libraryjars libs/core.jar
#-libraryjars libs/gesture-imageview.jar
#-libraryjars libs/gson-2.0.jar
#-libraryjars libs/infogracesound.jar
#-libraryjars libs/locSDK_4.0.jar
#-libraryjars libs/ormlite-android-4.48.jar
#-libraryjars libs/ormlite-core-4.48.jar
#-libraryjars libs/universal-image-loader-1.9.0.jar
#
#-keep class org.bouncycastle.** {*;}
#-keep class com.android.signapk.** {*;}
#
#
##指定类不进行混淆，保持原样
#-keep class com.baidu.** { *; }
#-keep class vi.com.gdi.bgl.android.**{*;}
#
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.support.v4.**
#-keep public class com.android.vending.licensing.ILicensingService
#
#-keep class com.google.gson.stream.** { *; }
#-keep class com.google.gson.examples.android.model.** { *; }
#-keep class com.uuhelper.Application.** { *; }
#-keep class net.sourceforge.zbar.** { *; }
#-keep class com.google.android.gms.** { *; }
#
#-keep class com.bank.pingan.model.** { *; }
#
#-keep public class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
#-keep public class * extends com.j256.ormlite.android.apptools.OpenHelperManager
#
#-keep class com.android.vending.licensing.ILicensingService
#-keep class android.support.v4.** { *; }
#-keep class org.apache.commons.net.** { *; }
#-keep class com.tencent.** { *; }
#
#-keep class com.umeng.** { *; }
#-keep class com.umeng.analytics.** { *; }
#-keep class com.umeng.common.** { *; }
#-keep class com.umeng.newxp.** { *; }
#
#-keep class com.j256.ormlite.** { *; }
#-keep class com.j256.ormlite.android.** { *; }
#-keep class com.j256.ormlite.field.** { *; }
#-keep class com.j256.ormlite.stmt.** { *; }
#
#
##这些有警告也不去处理
#-dontwarn android.support.v4.**
#-dontwarn org.apache.commons.net.**
#-dontwarn com.tencent.**
#
##如果用到了webview的复杂操作，则加入
#-keepclassmembers class * extends android.webkit.WebViewClient {
#     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
#     public boolean *(android.webkit.WebView,java.lang.String);
#}
#
#-keepclassmembers class * extends android.webkit.WebChromeClient {
#     public void *(android.webkit.WebView,java.lang.String);
#}
#
##保持native方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
##保持自定义控件类，不被混淆
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context);
#}
##保持枚举类不进行混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
##保持 Parcelable 类不被混淆
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
#
#
#-dontshrink
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#
#-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
#-keepattributes SourceFile,LineNumberTable
#
#-keep public interface com.facebook.**
#-keep public interface com.tencent.**
#-keep public interface com.umeng.socialize.**
#-keep public interface com.umeng.socialize.sensor.**
#-keep public interface com.umeng.scrshot.**
#
##不混淆该类及其成员变量
#-keep public class com.umeng.socialize.* {*;}
#-keep public class javax.**
#-keep public class android.webkit.**
#
#-keep class com.facebook.**
#-keep class com.umeng.scrshot.**
#-keep public class com.tencent.** {*;}
#-keep class com.umeng.socialize.sensor.**
#
#-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
#
#-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
#
#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#
#-keep public class [your_pkg].R$*{
#    public static final int *;
#}
