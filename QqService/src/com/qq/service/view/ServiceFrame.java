package com.qq.service.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.qq.service.model.MyQqService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ServiceFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5728815773042844065L;
	
	private JPanel contentPane;
	private JButton openButton, closeButton;
	private JTextField adminTextField;
	private JTextField passwdTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiceFrame frame = new ServiceFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServiceFrame() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//开启服务器
		openButton = new JButton("启动服务器");
		// 设置登录为默认按钮
		this.getRootPane().setDefaultButton(openButton);
		openButton.setBounds(43, 120, 119, 48);
		contentPane.add(openButton);
		openButton.addActionListener(this);
		//关闭服务器
		closeButton = new JButton("退出服务器");
		closeButton.setBounds(193, 120, 111, 48);
		contentPane.add(closeButton);
		closeButton.addActionListener(this);
		
		JLabel adminLabel = new JLabel("管理员帐号：");
		adminLabel.setBounds(30, 23, 90, 39);
		contentPane.add(adminLabel);
		
		adminTextField = new JTextField();
		adminTextField.setBounds(126, 30, 151, 24);
		contentPane.add(adminTextField);
		adminTextField.setColumns(10);
		
		JLabel passwdLabel = new JLabel("密码：");
		passwdLabel.setBounds(73, 68, 45, 39);
		contentPane.add(passwdLabel);
		
		passwdTextField = new JTextField();
		passwdTextField.setBounds(126, 75, 151, 24);
		contentPane.add(passwdTextField);
		passwdTextField.setColumns(10);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("服务器");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == openButton)	{
			this.setVisible(false);
			new ManagerFrame();
			new MyQqService();
		}
		else if(e.getSource() == closeButton) {
			this.dispose();
		}
	}
}
