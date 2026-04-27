## ============================================================
## GENERAL ANDROID
## ============================================================
#-keepattributes SourceFile,LineNumberTable
#-renamesourcefileattribute SourceFile
#-keepattributes *Annotation*
#-keepattributes Signature
#-keepattributes Exceptions
#
## ============================================================
## YOUR APP CLASSES
## ============================================================
#-keep class com.example.rentcar.** { *; }
#-keepclassmembers class com.example.rentcar.** { *; }
#
## ============================================================
## BUILDCONFIG (Protects your secure URL)
## ============================================================
#-keep class com.example.rentcar.BuildConfig { *; }
#
## ============================================================
## CHROME CUSTOM TABS
## ============================================================
#-keep class androidx.browser.** { *; }
#-dontwarn androidx.browser.**
#
## ============================================================
## RETROFIT + OKHTTP
## ============================================================
#-keep class retrofit2.** { *; }
#-keepclassmembers,allowshrinking,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
#-dontwarn retrofit2.**
#-dontwarn okhttp3.**
#-dontwarn okio.**
#-keep class okhttp3.** { *; }
#-keep interface okhttp3.** { *; }
#
## ============================================================
## GSON
## ============================================================
#-keep class com.google.gson.** { *; }
#-keepclassmembers class * {
#    @com.google.gson.annotations.SerializedName <fields>;
#}
#-dontwarn com.google.gson.**
#
## ============================================================
## MOSHI
## ============================================================
#-keep class com.squareup.moshi.** { *; }
#-keepclassmembers class ** {
#    @com.squareup.moshi.FromJson *;
#    @com.squareup.moshi.ToJson *;
#}
#-dontwarn com.squareup.moshi.**
#
## ============================================================
## ROOM DATABASE
## ============================================================
#-keep class * extends androidx.room.RoomDatabase { *; }
#-keep @androidx.room.Entity class * { *; }
#-keep @androidx.room.Dao class * { *; }
#-dontwarn androidx.room.**
#
## ============================================================
## GLIDE
## ============================================================
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class com.bumptech.glide.** { *; }
#-dontwarn com.bumptech.glide.**
#
## ============================================================
## EVENTBUS
## ============================================================
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#-dontwarn org.greenrobot.**
#
## ============================================================
## LOTTIE
## ============================================================
#-keep class com.airbnb.lottie.** { *; }
#-dontwarn com.airbnb.lottie.**
#
## ============================================================
## GOOGLE PLAY SERVICES
## ============================================================
#-keep class com.google.android.gms.** { *; }
#-dontwarn com.google.android.gms.**
#
## ============================================================
## COROUTINES
## ============================================================
#-keep class kotlinx.coroutines.** { *; }
#-dontwarn kotlinx.coroutines.**
#
## ============================================================
## DATASTORE
## ============================================================
#-keep class androidx.datastore.** { *; }
#-dontwarn androidx.datastore.**
#
## ============================================================
## WORK MANAGER
## ============================================================
#-keep class * extends androidx.work.Worker
#-keep class * extends androidx.work.ListenableWorker {
#    public <init>(android.content.Context, androidx.work.WorkerParameters);
#}
#-dontwarn androidx.work.**
#
## ============================================================
## VIEW BINDING / DATA BINDING
## ============================================================
#-keep class * extends androidx.viewbinding.ViewBinding { *; }
#-dontwarn androidx.databinding.**
#
## ============================================================
## NAVIGATION COMPONENT
## ============================================================
#-keep class androidx.navigation.** { *; }
#-dontwarn androidx.navigation.**