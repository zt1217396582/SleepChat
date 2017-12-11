package com.qq.service.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.qq.service.model.MyQqService;

import javax.swing.JButton;

public class ServiceFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton openButton, closeButton;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 351, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����������
		openButton = new JButton("����������");
		// ���õ�¼ΪĬ�ϰ�ť
		this.getRootPane().setDefaultButton(openButton);
		openButton.setBounds(28, 36, 119, 48);
		contentPane.add(openButton);
		openButton.addActionListener(this);
		//�رշ�����
		closeButton = new JButton("�رշ�����");
		closeButton.setBounds(194, 36, 111, 48);
		contentPane.add(closeButton);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("������");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource() == openButton)
		{
			this.setVisible(false);
			new ManagerFrame();
			new MyQqService();
		}
	}
}
