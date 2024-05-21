package com.linearity.utils;

import static com.linearity.utils.LoggerUtils.disableMethod;
import static com.linearity.utils.ReturnReplacements.returnNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookerThread{

    public static final String[] TIMHookedPackagesPart4 = new String[]{
            "com.tencent.bugly.Bugly", "com.tencent.bugly.BuglyStrategy",
            "com.tencent.bugly.crashreport.biz.b", "com.tencent.bugly.crashreport.biz.UserInfoBean",
            "com.tencent.bugly.crashreport.common.info.a", "com.tencent.bugly.crashreport.common.info.AppInfo",
            "com.tencent.bugly.crashreport.common.info.b", "com.tencent.bugly.crashreport.common.info.PlugInBean",
            "com.tencent.bugly.crashreport.common.strategy.a", "com.tencent.bugly.crashreport.common.strategy.StrategyBean",
            "com.tencent.bugly.crashreport.crash.anr.a", "com.tencent.bugly.crashreport.crash.anr.b",
            "com.tencent.bugly.crashreport.crash.anr.TraceFileHelper", "com.tencent.bugly.crashreport.crash.jni.a",
            "com.tencent.bugly.crashreport.crash.jni.b", "com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler",
            "com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler", "com.tencent.bugly.crashreport.crash.a",
            "com.tencent.bugly.crashreport.crash.b", "com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver",
            "com.tencent.bugly.crashreport.crash.c", "com.tencent.bugly.crashreport.crash.CrashDetailBean",
            "com.tencent.bugly.crashreport.crash.d", "com.tencent.bugly.crashreport.crash.e",
            "com.tencent.bugly.crashreport.crash.f", "com.tencent.bugly.crashreport.a",
            "com.tencent.bugly.crashreport.BuglyHintException", "com.tencent.bugly.crashreport.BuglyLog",
            "com.tencent.bugly.proguard.a", "com.tencent.bugly.proguard.aa", "com.tencent.bugly.proguard.ab",
            "com.tencent.bugly.proguard.ac", "com.tencent.bugly.proguard.ad", "com.tencent.bugly.proguard.ae",
            "com.tencent.bugly.proguard.af", "com.tencent.bugly.proguard.ag", "com.tencent.bugly.proguard.ah",
            "com.tencent.bugly.proguard.ai", "com.tencent.bugly.proguard.aj", "com.tencent.bugly.proguard.ak",
            "com.tencent.bugly.proguard.al", "com.tencent.bugly.proguard.am", "com.tencent.bugly.proguard.an",
            "com.tencent.bugly.proguard.ao", "com.tencent.bugly.proguard.ap", "com.tencent.bugly.proguard.aq",
            "com.tencent.bugly.proguard.ar", "com.tencent.bugly.proguard.b", "com.tencent.bugly.proguard.c",
            "com.tencent.bugly.proguard.d", "com.tencent.bugly.proguard.e", "com.tencent.bugly.proguard.f",
            "com.tencent.bugly.proguard.g", "com.tencent.bugly.proguard.h", "com.tencent.bugly.proguard.i",
            "com.tencent.bugly.proguard.j", "com.tencent.bugly.proguard.k", "com.tencent.bugly.proguard.l",
            "com.tencent.bugly.proguard.m", "com.tencent.bugly.proguard.n", "com.tencent.bugly.proguard.o",
            "com.tencent.bugly.proguard.p", "com.tencent.bugly.proguard.q", "com.tencent.bugly.proguard.r",
            "com.tencent.bugly.proguard.s", "com.tencent.bugly.proguard.t", "com.tencent.bugly.proguard.u",
            "com.tencent.bugly.proguard.v", "com.tencent.bugly.proguard.w", "com.tencent.bugly.proguard.x",
            "com.tencent.bugly.proguard.y", "com.tencent.bugly.proguard.z", "com.tencent.bugly.a", "com.tencent.bugly.b",
    };
    private final String[][] classesToDisable;
    private final ClassLoader classLoader;
    public HookerThread(ClassLoader classLoader,String[]... classesToDisable){
        this.classesToDisable = classesToDisable;
        this.classLoader = classLoader;
    }

    public void run() {
//        synchronized (this){
            Class<?> hookClass = null;
            for (String[] sArr : classesToDisable) {
                for (String s:sArr){
                    hookClass = XposedHelpers.findClassIfExists(s, classLoader);
                    if (hookClass != null && !Modifier.isAbstract(hookClass.getModifiers()) && !Modifier.isInterface(hookClass.getModifiers())) {
                        XposedBridge.hookAllConstructors(hookClass, returnNull);
                        for (Method m : hookClass.getDeclaredMethods()) {
                            disableMethod(m, hookClass);
                        }
                    }
                }
            }
//        }
    }
}
