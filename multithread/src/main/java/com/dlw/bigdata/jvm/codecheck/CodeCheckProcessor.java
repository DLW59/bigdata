package com.dlw.bigdata.jvm.codecheck;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author dengliwen
 * @date 2019/8/18
 * @desc 代码检查处理器
 */
@SupportedAnnotationTypes("*") //表示对哪些注解感兴趣  *代表所有
@SupportedSourceVersion(SourceVersion.RELEASE_8) //表示可以处理哪些版本的代码
public class CodeCheckProcessor extends AbstractProcessor {

    private CodeChecker codeChecker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.codeChecker = new CodeChecker(processingEnv);
    }

    /**
     * 对语法树进行检查
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                codeChecker.checkName(element);
            }
        }
        return false;
    }
}
