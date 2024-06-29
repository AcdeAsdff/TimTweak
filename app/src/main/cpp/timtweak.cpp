// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("deviceaddresstweaker");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("deviceaddresstweaker")
//      }
//    }
#include <stdarg.h>
#include <cstdio>
#include <jni.h>
#include <string>
#include <dlfcn.h>
#include <android/log.h>
#include "timtweak.h"
#include "datutils.h"

#define  TAG    "[linearity-Native-timtweak]"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)

using namespace datutils;

static HookFunType hook_func = nullptr;
uint8_t nop[4] = {0xD5,0x3,0x20,0x1F};
uint8_t * nop_ptr = nop;

JNIEnv *env = nullptr;

void on_library_loaded(const char *name, void *handle) {

    if (name == nullptr){ return;}
    // hooks on `libtarget.so`
//    void* target = nullptr;
//    std::string namestr(name);
    LOGD("[libraryLoad2]%s", name);

    if (strEndWith(name,"qzone_plugin.odex")){
        LOGD("found lib");
        if (env != nullptr){
            jclass hookTIMClass = env->FindClass("com/linearity/timtweak/HookTIMClass");
            jmethodID methodID = env->GetStaticMethodID(hookTIMClass,"hookFromNative", "()V");
            env->CallStaticVoidMethod(hookTIMClass,methodID);
        }
    }

}

extern "C" [[gnu::visibility("default")]] [[gnu::used]]
jint JNI_OnLoad(JavaVM *jvm, void*) {
//    JNIEnv *env = nullptr;
    jvm->GetEnv((void **)&env, JNI_VERSION_1_6);
//    hook_func((void *)env->functions->FindClass, (void *)fake_FindClass, (void **)&backup_FindClass);
//    env->RegisterNatives( env->FindClass("android/media/MediaDrm"),gMethods, (sizeof(gMethods)/sizeof(gMethods[0])));
    return JNI_VERSION_1_6;
}

extern "C" [[gnu::visibility("default")]] [[gnu::used]]
NativeOnModuleLoaded native_init(const NativeAPIEntries *entries) {
    hook_func = entries->hook_func;
//    // system hooks
//    hook_func((void*) fopen, (void*) fake_fopen, (void**) &backup_fopen);
//    hook_func((void*) popen, (void*) fake_popen, (void**) &backup_popen);
////    hook_func((void*)recvfrom,(void*) fake_recvfrom,(void**) &backup_recvfrom);
//    hook_func((void*)__system_property_get,(void*) fake__system_property_get,(void**) &backup__system_property_get);
//    hook_func((void*)getifaddrs, (void *) fake_getifaddrs, (void **) &backup_getifaddrs);
    return on_library_loaded;
}
