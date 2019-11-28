package com.dlw.bigdata.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyPerson {

	public static void main(String[] args) {
		//此属性是产生代理类class文件
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		final MyPerson p = new MyPerson();
		PersonInterface proxy = (PersonInterface) Proxy.newProxyInstance(MyPerson.class.getClassLoader(), MyPerson.class.getInterfaces(), new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("proxy is coming....");

				if (method.getName() == "saySomeThing") {
					System.out.println("say some thing is special handled.....");
					method.invoke(p,args);
//					p.saySomeThing();
				} else {
					Object invoke = method.invoke(p, args); // 调用任何public方法都拦截

					System.out.println("proxy is leaving....");

				}
				return null;
			}
		});
		proxy.doSomeThing();
		proxy.saySomeThing();

	}

}
