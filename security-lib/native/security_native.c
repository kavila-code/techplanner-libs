#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

static char *trim_copy(const char *s)
{
    if (!s)
        return NULL;
    const char *start = s;
    while (*start && isspace((unsigned char)*start))
        start++;
    const char *end = s + strlen(s) - 1;
    while (end >= start && isspace((unsigned char)*end))
        end--;
    size_t len = (end >= start) ? (end - start + 1) : 0;
    char *out = (char *)malloc(len + 1);
    if (!out)
        return NULL;
    if (len > 0)
        memcpy(out, start, len);
    out[len] = '\0';
    return out;
}

static void to_upper_inplace(char *s)
{
    if (!s)
        return;
    for (; *s; ++s)
        *s = toupper((unsigned char)*s);
}

static void add_role_item(char ***items_p, int *count_p, int *cap_p, const char *raw)
{
    if (!raw)
        return;

    char *trimmed = trim_copy(raw);
    if (!trimmed)
        return;

    if (strlen(trimmed) == 0)
    {
        free(trimmed);
        return;
    }

    to_upper_inplace(trimmed);

    const char *prefix = "ROLE_";
    size_t newlen = strlen(prefix) + strlen(trimmed);
    char *final = (char *)malloc(newlen + 1);
    if (!final)
    {
        free(trimmed);
        return;
    }

    strcpy(final, prefix);
    strcat(final, trimmed);
    free(trimmed);

    char **items = *items_p;
    int count = *count_p;
    int cap = *cap_p;

    for (int i = 0; i < count; ++i)
    {
        if (strcmp(items[i], final) == 0)
        {
            free(final);
            return;
        }
    }

    if (count >= cap)
    {
        int ncap = cap * 2;
        char **nitems = (char **)realloc(items, sizeof(char *) * ncap);
        if (!nitems)
        {
            free(final);
            return;
        }
        items = nitems;
        cap = ncap;
        *items_p = items;
        *cap_p = cap;
    }

    items[count++] = final;
    *count_p = count;
}

JNIEXPORT jobjectArray JNICALL Java_com_techplanner_securitylib_security_NativeSecurity_normalizeRolesNative(JNIEnv *env, jclass cls, jobjectArray realmRoles, jobjectArray resourceRoles)
{
    (void)cls;

    // Collect processed unique roles in C
    const int INITIAL_CAP = 32;
    char **items = (char **)malloc(sizeof(char *) * INITIAL_CAP);
    int cap = INITIAL_CAP;
    int count = 0;

    if (!items)
    {
        return NULL;
    }

    // Process realmRoles
    if (realmRoles != NULL)
    {
        jsize len = (*env)->GetArrayLength(env, realmRoles);
        for (jsize i = 0; i < len; ++i)
        {
            jstring js = (jstring)(*env)->GetObjectArrayElement(env, realmRoles, i);
            if (js == NULL)
                continue;
            const char *s = (*env)->GetStringUTFChars(env, js, NULL);
            add_role_item(&items, &count, &cap, s);
            (*env)->ReleaseStringUTFChars(env, js, s);
            (*env)->DeleteLocalRef(env, js);
        }
    }

    // Process resourceRoles
    if (resourceRoles != NULL)
    {
        jsize len = (*env)->GetArrayLength(env, resourceRoles);
        for (jsize i = 0; i < len; ++i)
        {
            jstring js = (jstring)(*env)->GetObjectArrayElement(env, resourceRoles, i);
            if (js == NULL)
                continue;
            const char *s = (*env)->GetStringUTFChars(env, js, NULL);
            add_role_item(&items, &count, &cap, s);
            (*env)->ReleaseStringUTFChars(env, js, s);
            (*env)->DeleteLocalRef(env, js);
        }
    }

    // Build Java String[] result
    jclass stringClass = (*env)->FindClass(env, "java/lang/String");
    if (stringClass == NULL)
    {
        for (int i = 0; i < count; ++i)
        {
            free(items[i]);
        }
        free(items);
        return NULL;
    }

    jobjectArray result = (*env)->NewObjectArray(env, count, stringClass, NULL);
    for (int i = 0; i < count; ++i)
    {
        jstring js = (*env)->NewStringUTF(env, items[i]);
        (*env)->SetObjectArrayElement(env, result, i, js);
        (*env)->DeleteLocalRef(env, js);
        free(items[i]);
    }
    free(items);
    (*env)->DeleteLocalRef(env, stringClass);
    return result;
}
