package com.qq.service.view;

import javax.swing.UIManager;

public class mainService {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new ServiceFrame();
	}

}
