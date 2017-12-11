/**
 * 
 */

package com.qq.client.model;

import com.qq.common.*;

public class QqClientUser {

	public boolean checkUser(Message message)
	{
		boolean b = new QqClientConServer().SendLoginInfoToServer(message);
		return b;
	}
	

	
}
