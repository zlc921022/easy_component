package com.xiaochen.module.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020-02-26
 */
@Aspect
public class TestAspect {

    @Before("execution(* android.app.Activity.onCreate(..))")
    public void testMethod(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Log.e(signature.getDeclaringType().getSimpleName(), signature.getName());
    }

    @Around("execution(* android.view.View.OnClickListener+.onClick(..))")
    public void testClick(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        Log.e("testClick before", signature.getName());
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e("testClick after", signature.getName());
    }

    @Around("call(* android.app.**.startActivity**(..))")
    public void testStartActivity(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        Log.e("startActivity before", signature.getDeclaringType().getSimpleName());
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e("startActivity after", signature.getDeclaringType().getSimpleName());
    }
}