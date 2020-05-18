package com.xiaochen.butterknife.core;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>简易butterKnife</p >
 *
 * @author zhenglecheng
 * @date 2020/5/18
 */
public final class EasyButterKnife {

    private EasyButterKnife() {
        throw new AssertionError("No instances.");
    }

    private static final String TAG = "EasyButterKnife";
    private static boolean debug = false;

    static final Map<Class<?>, Constructor> BINDINGS = new LinkedHashMap<>();

    public static void setDebug(boolean debug) {
        EasyButterKnife.debug = debug;
    }

    @UiThread
    public static void bind(@NonNull Activity target) {
        View decorView = target.getWindow().getDecorView();
        bind(target, decorView);
    }

    @UiThread
    public static void bind(@NonNull Activity target, @NonNull View decorView) {
        Class<? extends Activity> targetClass = target.getClass();
        if (debug) {
            Log.e(TAG, "Looking up binding for " + targetClass.getName());
        }
        Constructor constructor = findBindingConstructorForClass(targetClass);
        if (constructor == null) {
            return;
        }
        try {
            constructor.newInstance(target, decorView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Constructor findBindingConstructorForClass(Class<?> tClass) {
        if (tClass == null) {
            return null;
        }
        Constructor constructor = BINDINGS.get(tClass);
        if (constructor != null) {
            return constructor;
        }
        String className = tClass.getName();
        if (className.startsWith("android.") || className.startsWith("java.")
                || className.startsWith("androidx.")) {
            if (debug) {
                Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
            }
            return null;
        }
        try {
            Class<?> bindingClass = tClass.getClassLoader().loadClass(className + "_ViewBinding");
            if (debug) {
                Log.d(TAG, "HIT: Loaded binding class and constructor.");
            }
            return bindingClass.getConstructor(tClass, View.class);
        } catch (ClassNotFoundException e) {
            if (debug) {
                Log.d(TAG, "Not found. Trying superclass " + (tClass.getSuperclass() != null ?
                        tClass.getSuperclass().getName() : ""));
            }
            return findBindingConstructorForClass(tClass.getSuperclass());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
