package com.qq.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.common.UserData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;

public class RegisterFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5626509258328046087L;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JPasswordField passwdField;
	private JPasswordField repasswdField;
	private JTextField phoneTextField;
	private JButton yesButton;
	private JButton noButton;

	
	int UserCount=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new RegisterFrame();
	}

	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 293, 234);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDLabel = new JLabel("�û�����");
		IDLabel.setBounds(35, 13, 60, 18);
		contentPane.add(IDLabel);
		
		JLabel passwdLabel = new JLabel("���룺");
		passwdLabel.setBounds(50, 44, 45, 18);
		contentPane.add(passwdLabel);
		
		JLabel rePasswdLabel = new JLabel("ȷ�����룺");
		rePasswdLabel.setBounds(20, 74, 75, 18);
		contentPane.add(rePasswdLabel);
		
		JLabel phoneLabel = new JLabel("�ֻ��ţ�");
		phoneLabel.setBounds(35, 105, 60, 18);
		contentPane.add(phoneLabel);
		
		IDTextField = new JTextField();
		IDTextField.setBounds(109, 10, 127, 24);
		contentPane.add(IDTextField);
		IDTextField.setColumns(10);
		
		passwdField = new JPasswordField();
		passwdField.setBounds(109, 41, 127, 24);
		contentPane.add(passwdField);
		passwdField.setColumns(10);
		
		repasswdField = new JPasswordField();
		repasswdField.setBounds(109, 72, 127, 24);
		contentPane.add(repasswdField);
		repasswdField.setColumns(10);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(109, 102, 127, 24);
		contentPane.add(phoneTextField);
		phoneTextField.setColumns(10);
		
		yesButton = new JButton("ȷ��");
		yesButton.setBounds(38, 147, 92, 27);
		contentPane.add(yesButton);
		yesButton.addActionListener(this);
		this.getRootPane().setDefaultButton(yesButton);
		
		noButton = new JButton("ȡ��");
		noButton.setBounds(144, 147, 92, 27);
		contentPane.add(noButton);
		noButton.addActionListener(this);
		
		this.setTitle("ע���ʺ�");
		this.setVisible(true);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
		if(arg0.getSource() == yesButton) {
			
			Message message = getRegiserMassage();
			
			//�������ӣ�����ע����Ϣ
			Socket socket;
			try {
				socket = new Socket("127.0.0.1", 9999);
				ObjectOutputStream objectOutputStream;
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(message);
				
				//�ȴ����ݿⷵ��ע��ɹ�����Ϣ��Ȼ��ر�ע����棬��ʾע��ɹ��ĶԻ���
				ObjectInputStream objectIntputStream;
				objectIntputStream = new ObjectInputStream(socket.getInputStream());
				Message messagereturn=(Message) objectIntputStream.readObject();
				
				if(messagereturn.getMessageType().equals(MessageType.RegisterSuccess)) {
					JOptionPane.showMessageDialog(this, "ע��ɹ�");
					this.dispose();
				}
				
				
			} catch (UnknownHostException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}
		else if(arg0.getSource() == noButton) {
			this.dispose();
		}
	}
	
	//��ȡע����Ϣ��
	public Message getRegiserMassage()
	{
		Message message = new Message();
		
		String userName = IDTextField.getText().trim();
		String password = new String(passwdField.getPassword());
		String rePassword = new String(repasswdField.getPassword());
		String phoneNum = phoneTextField.getText().trim();
		
		User user = new User();
		UserData userData = new UserData();
		if(password.equals(rePassword)) {
			
			userData.setPhoneNum(phoneNum);
			
			user.setUserName(userName);
			user.setPassword(password);			
			user.setUserData(userData);
			
			message.setUser(user);
			message.setMessageType(MessageType.Register);
			
		}else {
			JOptionPane.showMessageDialog(this, "���벻һ�£�����������");
			passwdField.setText("");
			repasswdField.setText("");
		}
		return message;
	}
}
