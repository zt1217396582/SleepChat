package com.qq.client.view;

import javax.swing.UIManager;

public class mainClient {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new ClientLoginFrame();
	}

}
