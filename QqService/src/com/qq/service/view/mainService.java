package com.qq.service.view;

import javax.swing.UIManager;

public class mainService {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new ServiceFrame();
	}

}
