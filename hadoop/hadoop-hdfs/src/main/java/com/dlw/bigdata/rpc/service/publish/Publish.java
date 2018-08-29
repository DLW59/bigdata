package com.dlw.bigdata.rpc.service.publish;

import com.dlw.bigdata.rpc.service.ClientNameNodeProtocol;
import com.dlw.bigdata.rpc.service.LoginServiceInterface;
import com.dlw.bigdata.rpc.service.impl.LoginServiceImpl;
import com.dlw.bigdata.rpc.service.impl.NameNodeNameSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;

/**
 * 发布服务 服务器
 */
public class Publish {

	public static void main(String[] args) throws Exception {

		Builder builder = new Builder(new Configuration());
		builder.setBindAddress("localhost")
				.setPort(9999)
				.setProtocol(LoginServiceInterface.class)
				.setInstance(new LoginServiceImpl());
		Server server1 = builder.build();
		System.out.println("server1启动了.....");
		server1.start();

		/*Builder builder2 = new Builder(new Configuration());
		builder2.setBindAddress("master").setPort(1314)
				.setProtocol(ClientNameNodeProtocol.class)
				.setInstance(new NameNodeNameSystem());
		Server server2 = builder2.build();
		System.out.println("server2启动了.....");
		server2.start();*/

	}


}
