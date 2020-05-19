package com.xiaochen.butterknife.compiler.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.xiaochen.butterknife.annotation.BindView;
import com.xiaochen.butterknife.compiler.utils.Constant;
import com.xiaochen.butterknife.compiler.utils.Log;
import com.xiaochen.butterknife.compiler.utils.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


@AutoService(Processor.class)
/**
 * 指定使用的Java版本 替代 {@link AbstractProcessor#getSupportedSourceVersion()} 函数
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
/**
 * 注册给哪些注解的  替代 {@link AbstractProcessor#getSupportedAnnotationTypes()} 函数
 */
@SupportedAnnotationTypes(Constant.ANNOTATION_TYPE_BIND_VIEW)
/**
 * @author : xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 * @author admin
 */
public class BindViewProcessor extends AbstractProcessor {

    static final String VIEW_TYPE = "android.view.View";

    static final String ACTIVITY_TYPE = "android.app.Activity";

    /**
     * 节点工具类 (类、函数、属性都是节点)
     */
    private Elements elementUtils;

    /**
     * type(类信息)工具类
     */
    private Types typeUtils;

    /**
     * 文件生成器 类/资源
     */
    private Filer filerUtils;

    private Log log;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("BindViewProcessor init");
        //获得apt的日志输出
        log = Log.newLog(processingEnvironment.getMessager());
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        filerUtils = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    /**
     * @param set              使用了支持处理注解的节点集合
     * @param roundEnvironment 表示当前或是之前的运行环境,可以通过该对象查找找到的注解。
     * @return true 表示后续处理器不会再处理(已经处理)
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("BindViewProcessor init");
        if (!Utils.isEmpty(set)) {
            //被BindView注解的节点集合
            Set<? extends Element> rootElements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
            if (!Utils.isEmpty(rootElements)) {
                processBindViews(rootElements);
            }
            return true;
        }
        return false;
    }


    private void processBindViews(Set<? extends Element> rootElements) {

        for (Element element : rootElements) {
            String packageStr = element.getEnclosingElement().toString();
            String classStr = element.getSimpleName().toString();
            ClassName className = ClassName.get(packageStr, classStr + "_ViewBinding");

            System.out.println("packageStr: "+ packageStr);
            System.out.println("classStr: "+ classStr);
            System.out.println("className: "+ className);
            MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageStr, classStr), "activity");

            for (Element e: element.getEnclosedElements()) {
                BindView bindView = e.getAnnotation(BindView.class);
                builder.addStatement("activity.$N = activity.findViewById($L)",
                        e.getSimpleName(),bindView.value());
            }

            TypeSpec typeSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(builder.build())
                    .build();

            try {
                JavaFile.builder(packageStr,typeSpec)
                        .build()
                        .writeTo(filerUtils);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
