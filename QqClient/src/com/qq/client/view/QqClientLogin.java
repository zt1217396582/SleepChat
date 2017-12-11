/**
 * qq�ͻ��˵�¼����
 */

package com.qq.client.view;

import javax.swing.*;

import com.qq.client.controller.*;
import com.qq.client.model.*;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

public class QqClientLogin extends JFrame implements ActionListener{

	//���山����Ҫ�����
	JLabel jLabel;
	
	//�����в���Ҫ�����
	//�в�������JPanel����һ��ѡ����ڹ���
	JTabbedPane jTabbedPane;
	JPanel jPanel2, jPanel3, jPanel4;
	JLabel jPanel2_Label, jPanel2_Label2, jPanel2_Label3, jPanel2_Label4;
	JButton jPanel2_Button;
	JTextField jPanel2_TextField;
	JPasswordField jPanel2_PasswordField;
	JCheckBox jPanel2_CheckBox, jPanel2_CheckBox2;
	
	//�����ϲ���Ҫ�����
	JPanel jPanel;
	JButton jPanel_Button, jPanel_Button2, jPanel_Button3;
	
	User user = new User();
	static String userID;
	static java.util.Date date=null;
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		QqClientLogin qqClientLogin = new QqClientLogin();
	}
	
	public QqClientLogin()
	{
		//������
		jLabel = new JLabel(new ImageIcon("image/logo.gif"));
		
		//�����в�
		jPanel2 = new JPanel(new GridLayout(3,3));
		jPanel2_Label = new JLabel("QQ����", JLabel.CENTER);
		jPanel2_Label2 = new JLabel("QQ����", JLabel.CENTER);
		jPanel2_Label3 = new JLabel("��������", JLabel.CENTER);
		jPanel2_Label3.setForeground(Color.blue);
		jPanel2_Label4 = new JLabel("�������뱣��", JLabel.CENTER);
		jPanel2_Label4.setForeground(Color.blue);
		jPanel2_Button = new JButton("�޸ĺ���");
		jPanel2_TextField = new JTextField();
		jPanel2_PasswordField = new JPasswordField();
		jPanel2_CheckBox = new JCheckBox("�����¼");
		jPanel2_CheckBox2 = new JCheckBox("��ס����");
		
		//�ѿؼ����뵽jPanel2
		jPanel2.add(jPanel2_Label);
		jPanel2.add(jPanel2_TextField);
		jPanel2.add(jPanel2_Button);
		jPanel2.add(jPanel2_Label2);
		jPanel2.add(jPanel2_PasswordField);
		jPanel2.add(jPanel2_Label3);
		jPanel2.add(jPanel2_CheckBox);
		jPanel2.add(jPanel2_CheckBox2);
		jPanel2.add(jPanel2_Label4);
		//����ѡ�����
	/*	jTabbedPane = new JTabbedPane();
		jTabbedPane.add("QQ����",jPanel2);
		jPanel3 = new JPanel();
		jTabbedPane.add("�ֻ�����",jPanel3);
		jPanel4 = new JPanel();
		jTabbedPane.add("�����ʼ�",jPanel4);*/
		
		//�����ϲ�
		jPanel = new JPanel();
		jPanel_Button = new JButton("��¼");
		// ���õ�¼ΪĬ�ϰ�ť
		this.getRootPane().setDefaultButton(jPanel_Button);
		
		//��Ӧ�û������¼
		jPanel_Button.addActionListener(this);
		
		jPanel_Button2 = new JButton("ע��");
		
		//��Ӧ�û����ע��
		jPanel_Button2.addActionListener(this);
		
		//jPanel_Button3 = new JButton("ע����");
		
		//��������ť����jPanel
		jPanel.add(jPanel_Button);
		jPanel.add(jPanel_Button2);
		//jPanel.add(jPanel_Button3);
		
		this.add(jLabel, "North");
		this.add(jPanel2, "Center");
		this.add(jPanel,"South");
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("������");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
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
		if(arg0.getSource() == jPanel_Button)
		{
			System.out.println("������֤����");
			QqClientUser qqClientUser = new QqClientUser();
			//User user = new User();
			user.setUserId(jPanel2_TextField.getText().trim());
			user.setPassword(new String(jPanel2_PasswordField.getPassword()));
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
		else if(arg0.getSource() == jPanel_Button2)
		{
			new RegisterFrame();
		}
	}

}
