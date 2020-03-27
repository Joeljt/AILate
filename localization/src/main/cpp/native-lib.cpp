#include <jni.h>
#include <string>

/*
 * Class:     net_aihelp_localization_AIHelpSDK
 * Method:    initFromJNI
 * Signature: (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
 */
extern "C" JNIEXPORT void JNICALL
Java_net_aihelp_localization_AIHelpSDK_initFromJNI
        (JNIEnv *env, jobject clz, jobject context, jstring appKey, jstring hashKey, jstring lan){
    jclass j_clz = env->GetObjectClass(clz);
    jmethodID j_mid = env->GetStaticMethodID(j_clz, "init", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    env->CallStaticVoidMethod(j_clz, j_mid, context, appKey, hashKey, lan);
}

/*
 * Class:     net_aihelp_localization_AIHelpSDK
 * Method:    getStringFromJNI
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
extern "C" JNIEXPORT jstring JNICALL
Java_net_aihelp_localization_AIHelpSDK_getStringFromJNI
        (JNIEnv *env, jobject clz, jstring code){
    jclass j_clz = env->GetObjectClass(clz);
    jmethodID j_mid = env->GetStaticMethodID(j_clz, "getString", "(Ljava/lang/String;)Ljava/lang/String;");
    jstring j_result = reinterpret_cast<jstring>(env->CallStaticObjectMethod(j_clz, j_mid, code));
    return j_result;
}

/*
 * Class:     net_aihelp_localization_AIHelpSDK
 * Method:    getStringFromJNI
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
extern "C" JNIEXPORT jstring JNICALL
Java_net_aihelp_localization_AIHelpSDK_getInstance
        (JNIEnv *env, jobject clz){
    jclass j_clz = env->GetObjectClass(clz);
    jmethodID j_mid = env->GetStaticMethodID(j_clz, "getSDKInstance", "()Ljava/lang/String;");
    jstring j_result = reinterpret_cast<jstring>(env->CallStaticObjectMethod(j_clz, j_mid));
    return j_result;
}