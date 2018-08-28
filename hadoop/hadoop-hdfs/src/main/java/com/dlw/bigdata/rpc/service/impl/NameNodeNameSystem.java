package com.dlw.bigdata.rpc.service.impl;


import com.dlw.bigdata.rpc.service.ClientNameNodeProtocol;

public class NameNodeNameSystem implements ClientNameNodeProtocol {

	@Override
	public String getMetaData(String path) {

		return path+"[blk_01,blk_02,blk_03]\n{blk_01:node01,node02}";
		
	}
	

}
