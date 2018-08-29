package com.dlw.bigdata.rpc.service;

/**
 * 登录服务
 * @author Administrator
 */
public interface LoginServiceInterface {
	
	public static final long versionID=1L;
	
	String login(String username, String password);
	

}
