#include <android/log.h>
#include <jni.h>
#include <stdio.h>
#include <sqlite3.h>
#include <math.h>
#include "sqlite3.h"
#include "com_example_lee_ndkdemonativesqlite_util_JniUtil.h"


#define LOG_TAG "native_sqlite3"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

jobject
Java_com_example_lee_ndkdemonativesqlite_util_JniUtil_getDataBaseResultList
        (JNIEnv *env, jclass jclazz, jstring  str_path){

    sqlite3 *db;
    sqlite3_stmt *stmt;
    const char *path = (*env)->GetStringUTFChars(env,str_path, 0);
    if(path == NULL){
        return NULL;
    }
    //JNI 函数 类似反射
    jclass arrayClass =  (*env)->FindClass(env,"java/util/ArrayList");
    //JNI 默认利用 <init>代表构造函数，V 表示 void方法签名
    jmethodID  array_init = (*env)->GetMethodID(env,arrayClass,"<init>","()V");
    //Z 标识 方法返回值为 boolean   (L + packageName/className) Object
    jmethodID  array_add = (*env)->GetMethodID(env,arrayClass,"add","(Ljava/lang/Object;)Z");

    jobject arrayObj = (*env)->NewObject(env,arrayClass,array_init);

    //以 指定 flag 模式打开数据库
    sqlite3_open_v2(path,&db,SQLITE_OPEN_READONLY,NULL);

    (*env)->ReleaseStringUTFChars(env,str_path, path);

    if (sqlite3_prepare(db, "SELECT name FROM result",
                        -1, &stmt, NULL) == SQLITE_OK) {
        int err;
        while ((err = sqlite3_step(stmt)) == SQLITE_ROW) {
            const char *name = (char const *)sqlite3_column_text(stmt, 0);
            jstring  jname =  (*env)->NewStringUTF(env,name);
            (*env)->CallBooleanMethod(env,arrayObj, array_add,jname);
            (*env)->DeleteLocalRef(env,jname);
        }
        if (err != SQLITE_DONE) {
            LOGD("Query execution failed: %s\n", sqlite3_errmsg(db));
        }
        sqlite3_finalize(stmt);
    }
    return arrayObj;
}