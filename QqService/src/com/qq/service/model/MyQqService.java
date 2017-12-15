/**
 * qq服务器，用来监听某个客户端的连接
 */

package com.qq.service.model;

import com.qq.service.controller.ServiceThread;

public class MyQqService {
	
	public MyQqService()
	{
		Thread serviceThread = new ServiceThread();
		serviceThread.start();
	}

}
