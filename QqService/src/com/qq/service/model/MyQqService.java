/**
 * qq服务器，用来监听某个客户端的连接
 */

package com.qq.service.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.qq.common.*;
import com.qq.service.controller.ManageClientThread;
import com.qq.service.controller.ServiceConClientThread;
import com.qq.service.controller.ServiceThread;
import com.qq.service.db.ServiceConDatabase;

public class MyQqService {
	
	public MyQqService()
	{
		Thread serviceThread = new ServiceThread();
		serviceThread.start();
	}

}
