package com.qq.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class ChangePasswordFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8264690432156941782L;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JPasswordField NowpasswdField;
	private JPasswordField NewpasswdField;
	private JPasswordField repasswdField;
	private JButton yesButton;
	private JButton noButton;
	

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new ChangePasswordFrame();
	}

	public ChangePasswordFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 293, 234);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDLabel = new JLabel("�ʺţ�");
		IDLabel.setBounds(35, 13, 60, 18);
		contentPane.add(IDLabel);
		
		JLabel nowpasswdLabel = new JLabel("ԭ���룺");
		nowpasswdLabel.setBounds(35, 44, 75, 18);
		contentPane.add(nowpasswdLabel);
		
		JLabel newPasswdLabel = new JLabel("�����룺");
		newPasswdLabel.setBounds(35, 74, 75, 18);
		contentPane.add(newPasswdLabel);
		
		JLabel rePasswdLabel = new JLabel("ȷ�����룺");
		rePasswdLabel.setBounds(20, 104, 75, 18);
		contentPane.add(rePasswdLabel);
		
		IDTextField = new JTextField();
		IDTextField.setBounds(109, 10, 127, 24);
		contentPane.add(IDTextField);
		IDTextField.setColumns(10);
		
		NowpasswdField = new JPasswordField();
		NowpasswdField.setBounds(109, 41, 127, 24);
		contentPane.add(NowpasswdField);
		NowpasswdField.setColumns(10);
		
		NewpasswdField = new JPasswordField();
		NewpasswdField.setBounds(109, 72, 127, 24);
		contentPane.add(NewpasswdField);
		NewpasswdField.setColumns(10);
		
		repasswdField = new JPasswordField();
		repasswdField.setBounds(109, 103, 127, 24);
		contentPane.add(repasswdField);
		repasswdField.setColumns(10);
		
		yesButton = new JButton("ȷ��");
		yesButton.setBounds(38, 147, 92, 27);
		contentPane.add(yesButton);
		yesButton.addActionListener(this);
		this.getRootPane().setDefaultButton(yesButton);
		
		noButton = new JButton("ȡ��");
		noButton.setBounds(144, 147, 92, 27);
		contentPane.add(noButton);
		noButton.addActionListener(this);
		
		this.setTitle("�޸�����");
		this.setVisible(true);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==yesButton){
			
			Message message = getPasswordMessage();
			
			//�������ӣ������޸�������Ϣ
			Socket socket;
			try {
				socket = new Socket("127.0.0.1", 9999);
				ObjectOutputStream objectOutputStream;
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(message);
				
				//�ȴ����ݿⷵ�������޸ĳɹ�����Ϣ��Ȼ��رս��棬��ʾ�����޸ĳɹ��ĶԻ���
				ObjectInputStream objectIntputStream;
				objectIntputStream = new ObjectInputStream(socket.getInputStream());
				Message messagereturn=(Message) objectIntputStream.readObject();
				
				if(messagereturn.getMessageType().equals(MessageType.ChangePasswordSuccess)) {
					JOptionPane.showMessageDialog(this, "�����޸ĳɹ���");
					this.dispose();
				}
				else if(messagereturn.getMessageType().equals(MessageType.ChangePasswordFail)){
					JOptionPane.showMessageDialog(this, "�����޸�ʧ�ܣ�");
				}
				
			} catch (UnknownHostException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==noButton){
			this.dispose();
		}
	}
	//��ȡ�޸�������Ϣ��
		public Message getPasswordMessage()
		{
			Message message = new Message();
			
			String userName = IDTextField.getText().trim();
			String nowpassword = new String(NowpasswdField.getPassword());
			String newpassword=new String(NewpasswdField.getPassword());
			String rePassword = new String(repasswdField.getPassword());
			
			User user = new User();
			message.setMessageType(MessageType.ChangePassword);
			
			if(newpassword.equals(rePassword)) {
				user.setUserId(userName);
				user.setPassword(nowpassword);			
				message.setUser(user);
				message.setMessage(newpassword);
				
			}else {
				JOptionPane.showMessageDialog(this, "�����벻һ�£�����������");
				NowpasswdField.setText("");
				NewpasswdField.setText("");
				repasswdField.setText("");
			}
			return message;
		}
}
