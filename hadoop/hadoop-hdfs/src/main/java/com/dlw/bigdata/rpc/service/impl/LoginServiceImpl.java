package com.dlw.bigdata.rpc.service.impl;


import com.dlw.bigdata.rpc.service.LoginServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginServiceImpl implements LoginServiceInterface {

	@Override
	public String login(String username, String password) {
		
//		System.out.println(username + "你总算来了，等死我了");
		log.info("hello!{}",username );
		
		return username + "successfully loged in , welcome......";
	}

}
