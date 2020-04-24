package com.xiaochen.module.aop;

import android.annotation.SuppressLint;
import android.util.Log;

import com.xiaochen.module.aop.annotation.Msg;
import com.xiaochen.module.aop.annotation.Test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020-02-28
 */
@Aspect
public class TraceAspect {
    private static final String POINTCUT_METHOD =
            "execution(@com.xiaochen.module.aop.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.xiaochen.module.aop.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {
    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint point) {
        try {
            Signature signature = point.getSignature();
            String className = signature.getDeclaringType().getSimpleName();
            String methodName = signature.getName();
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object result = point.proceed();
            stopWatch.stop();
            DebugLog.log(className, buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));
            printAnnotationMessage();
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @SuppressLint("LongLogTag")
    private void printAnnotationMessage() {
        Class<Test> aClass = Test.class;
        Msg msg = aClass.getAnnotation(Msg.class);
        Log.e("printAnnotationMessage msg", msg.msg());
    }

    /**
     * 创建日志消息
     *
     * @param methodName     方法名称
     * @param methodDuration 延迟毫秒
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("Gintonic --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");
        return message.toString();
    }

}
