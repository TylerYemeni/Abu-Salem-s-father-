# الاحتفاظ بجميع الكلاسات المرتبطة بـ Google Maps
-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }

# الاحتفاظ بجميع الكلاسات المتعلقة بتحديد الموقع
-keep class android.location.** { *; }

# الاحتفاظ بكل الكلاسات التي تنفذ عبر BroadcastReceiver
-keep class com.abusalem.netsentinel.receiver.** { *; }

# السماح بكود Kotlin والإضافات
-dontwarn kotlin.**
-keep class kotlin.** { *; }

# تعطيل أي تحسين قد يخرب التطبيق
-dontoptimize
-dontshrink
