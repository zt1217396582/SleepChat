package com.qq.client.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.qq.client.controller.ManageClientConServerThread;
import com.qq.client.controller.ManageQqFriendList;
import com.qq.client.model.QqClientUser;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class ClientLoginFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField UserIdTextField;
	private JPasswordField passwordTextField;
	JButton changePasswordButton, registerButton, exitButton, LoginButton;
	JLabel pictureLabel, UserIdLabel, passwordLabel;
	
	User user = new User();
	static String userID;
	static java.util.Date date=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLoginFrame frame = new ClientLoginFrame();
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
	public ClientLoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pictureLabel = new JLabel(new ImageIcon("image/logo.gif"));
		pictureLabel.setBounds(0, 0, 399, 178);
		contentPane.add(pictureLabel);
		
		UserIdLabel = new JLabel("�ʺţ�");
		UserIdLabel.setBounds(24, 182, 58, 46);
		contentPane.add(UserIdLabel);
		
		passwordLabel = new JLabel("���룺");
		passwordLabel.setBounds(24, 223, 58, 46);
		contentPane.add(passwordLabel);
		
		UserIdTextField = new JTextField();
		UserIdTextField.setBounds(70, 191, 171, 29);
		contentPane.add(UserIdTextField);
		UserIdTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(70, 232, 171, 29);
		contentPane.add(passwordTextField);
		
		changePasswordButton = new JButton("�޸�����");
		changePasswordButton.setBounds(255, 233, 113, 27);
		contentPane.add(changePasswordButton);
		
		registerButton = new JButton("ע���ʺ�");
		registerButton.setBounds(255, 192, 113, 27);
		contentPane.add(registerButton);
		
		exitButton = new JButton("�˳�");
		exitButton.setBounds(211, 274, 113, 27);
		contentPane.add(exitButton);
		
		LoginButton = new JButton("��¼");
		LoginButton.setBounds(53, 274, 113, 27);
		contentPane.add(LoginButton);
		
		//����
		LoginButton.addActionListener(this);
		registerButton.addActionListener(this);
		exitButton.addActionListener(this);
		changePasswordButton.addActionListener(this);
		

		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("������");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	//��������״̬
	public Message setOnlineUser(Message message){
		//�������ӣ����������û���Ϣ��
		Socket socket;
		Message messagereturn = null;
		try {
			message.setMessageType(MessageType.UserOnline);
			socket = new Socket("127.0.0.1", 9999);
			ObjectOutputStream objectOutputStream;
			message.setUser(user);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(message);
			
			//�ȴ�����������message
			ObjectInputStream objectIntputStream;
			objectIntputStream = new ObjectInputStream(socket.getInputStream());
			messagereturn=(Message) objectIntputStream.readObject();
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return messagereturn;
	}
	
	//��ʼ������¼ʱ�䣬�Լ��ʺ�
	public void getLastLoginTimeAndUser(Message messagereturn){
		date=messagereturn.getSendTime();
		userID=messagereturn.getMessage();
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
		//����û������¼
		if(arg0.getSource() == LoginButton)
		{
			System.out.println("������֤����");
			QqClientUser qqClientUser = new QqClientUser();
			//User user = new User();
			user.setUserId(UserIdTextField.getText().trim());
			user.setPassword(new String(passwordTextField.getPassword()));
			System.out.println("�û����͵�id��" + user.getUserId() + "���룺" + user.getPassword());
			Message message = new Message();
			message.setUser(user);
			message.setMessageType(MessageType.LoginRequest);
			if(qqClientUser.checkUser(message))
			{
				//���������û�״̬,����ʼ������¼ʱ���Լ��ʺ�
				getLastLoginTimeAndUser(setOnlineUser(message));
				
				
				try {	
					
				//���������б�
					QqFriendList qqList=new QqFriendList(user.getUserId());
					ManageQqFriendList.addQqFriendList(user.getUserId(), qqList);
					
				//����һ��Ҫ�󷵻����ߺ��ѵ�����	
					ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(user.getUserId()).getSocket().getOutputStream());
				
				// ����һ��Message������������Ϣ
					Message messageSend=new Message();
					messageSend.setMessageType(MessageType.GetOnlineFriend);
					
					//ָ����½�˺ŵĺ������
					messageSend.setSender(user.getUserId());
					oos.writeObject(messageSend);
					
				
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				//�رյ�¼����
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this, "�û��������");
			}
		}
		//����û����ע��
		else if(arg0.getSource() == registerButton)
		{
			new RegisterFrame();
		}
		else if(arg0.getSource() == changePasswordButton)
		{
			new ChangePasswordFrame();
		}
		else if(arg0.getSource() == exitButton)
		{
			this.dispose();
		}
	}
}
