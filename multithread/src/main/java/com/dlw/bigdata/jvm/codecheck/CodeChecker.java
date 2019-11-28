package com.dlw.bigdata.jvm.codecheck;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.lang.reflect.Method;
import java.util.EnumSet;

/**
 * @author dengliwen
 * @date 2019/8/18
 * @desc 代码命名规范检查器
 */
public class CodeChecker {

    private final Messager messager;

    private CodeCheckScanner scanner = new CodeCheckScanner();

    public CodeChecker(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
    }

    /**
     * 检查命名
     * @param element
     */
    public void checkName(Element element) {
        scanner.scan(element);
    }

    private class CodeCheckScanner extends ElementScanner8<Void,Void> {

        /**
         * 用于检查Java类
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e,aVoid);
            checkCamelCase(e,true);
            return super.visitType(e, aVoid);
        }

        /**
         * 检查方法命名是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                final Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    checkCamelCase(e,false);
                }
            }
            return super.visitExecutable(e, aVoid);
        }

        /**
         * 检查变量是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null
                    || heuristicallyConstant(e)) {
                checkAllCaps(e);
            }else {
                checkCamelCase(e,false);
            }
            return null;
        }

        /**
         * 检查传入的element是否符合命名规范，不符合输出警告信息
         * @param e
         * @param b
         */
        private void checkCamelCase(Element e, boolean b) {
            final String name = e.getSimpleName().toString();
            boolean preUpper = false;
            boolean conventional = true;
            //第一个字符
            final int point = name.codePointAt(0);
            if (Character.isUpperCase(point)) {
                preUpper = true;
                if (!b) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应该小写字母开头",e);
                }
            }else if (Character.isLowerCase(point)) {
                if (b) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应该大写字母开头",e);
                }
            }else {
                conventional = false;
            }
            if (conventional) {
                int cp = point;
                for (int i = Character.charCount(cp);i<name.length();i+=Character.charCount(cp)) {
                    cp = name.codePointAt(i );
                    if (Character.isUpperCase(cp)) {
                        if (preUpper) {
                            conventional = false;
                            break;
                        }
                        preUpper = true;
                    }else {
                        preUpper = false;
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应该符合驼峰命名",e);
            }
        }

        /**
         * 判断一个变量是否是常量
         * @param e
         * @return
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            }else if (e.getKind() == ElementKind.FIELD && e.getModifiers()
                    .containsAll(EnumSet.of(Modifier.PUBLIC,Modifier.STATIC,Modifier.FINAL))) {
                return true;
            }else {
                return false;
            }

        }

        /**
         * 大写命名检查
         * @param e
         */
        private void checkAllCaps(Element e) {
            final String name = e.getSimpleName().toString();
            boolean conventional = true;
            final int point = name.codePointAt(0);
            if (!Character.isUpperCase(point)) {
                conventional = false;
            }else {
                boolean preUnderScore = false;
                int cp = point;
                for (int i=Character.charCount(cp);i<name.length();i+=Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == '_') {
                        if (preUnderScore) {
                            conventional = false;
                            break;
                        }
                        preUnderScore = true;
                    }else {
                        preUnderScore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "常量应该全部大小写并且下划线分割，以字符开头",e);
            }
        }
    }
}
