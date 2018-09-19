# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/erichua/Documents/program/AndroidSdkForAS/tools/proguard/proguard-android.txt
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

#--------------------------------------------x5混淆-------------------------------------------------
-include x5_proguard.cfg.pro
#-dontwarn dalvik.**
#或者可以直接keep住整个包
#-keep class com.tencent.smtt.**
#-keep class com.tencent.tbs.video.**
#-dontwarn com.tencent.smtt.**
#-dontwarn com.tencent.tbs.video.**

#--------------------------------------------bridgewebview混淆--------------------------------------
#-----其实这个不需要,因为都是我们自己用,不会给别人用------
-keep class com.webank.mbank.web.webview.**{
	public *;
}

-keep class com.webank.mbank.web.iweb.**{
	public *;
}
-keep class com.webank.mbank.web.webview.NativeJsActionPlugin{
	public <methods>;
	public <fields>;
	protected <methods>;
	protected <fields>;
}

-dontwarn com.webank.mbank.web.**
#--------------------------------------------# wesdk混淆--------------------------------------------
-keep class com.webank.walletsdk.WeWalletSDK{
	public static final <fields>;
	public  <methods>;
}
#--------------------------------------------# base SDK混淆--------------------------------------------
-keep class com.webank.mbank.wepower.**{
    public <methods>;
}
#保留所有内部类
-keep class com.webank.mbank.wepower.**$*{
    *;
}

-keep public interface com.webank.walletsdk.WeWalletSDK$WalletPayCallBack {*;}
-keep public interface com.webank.walletsdk.WeWalletSDK$WalletConfirmCallback {*;}
-keep public interface com.webank.walletsdk.WeWalletSDK$WalletRegisterCallback {*;}
-keep public interface com.webank.walletsdk.WeWalletSDK$WalletCallback {*;}
-keep public class com.webank.walletsdk.WeWalletSDK$WeWalletCallback {*;}
-keep public class com.webank.walletsdk.WeWalletSDK$OnSdkListener {*;}

#--------------------------------------------# bugly混淆--------------------------------------------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


#--------------------------------------------# 其他--------------------------------------------
# Note: duplicate definition of library class [android.net.http.SslCertificate]
-dontnote android.net.http.*
-dontnote org.apache.http.**
#android.jar和android/optional/org.apache.http.legacy.jar中都有导致
#-dontnote com.google.vending.licensing.ILicensingService
#-dontnote com.android.vending.licensing.ILicensingService
#-dontnote dalvik.system.VMStack
#-keep class com.webank.mbank.web.plugin.BackType
#-dontnote android.support.**
