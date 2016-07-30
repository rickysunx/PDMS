package com.pxl.ppm.framework;

/**
 * Bean工厂
 * 通过该类来获取服务接口
 * @author Ricky
 * @version 1.0
 */
public class BeanFactory {
	
	/**
	 * 获取服务接口Bean
	 * @param beanName 接口名
	 * @return 接口Bean的实例
	 */
	public static Object getBean(String beanName) throws Exception {
		String className = "com.pxl.ppm.impls."+beanName+"Impl";
		Class c = Class.forName(className);
		return c.newInstance();
	}
	
}
