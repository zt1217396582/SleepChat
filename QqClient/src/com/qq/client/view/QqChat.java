/**
 * ���������Ľ���
 * ��Ϊ�ͻ���Ҫ���ڶ�ȡ��״̬������ǰ�������һ���߳�
 */

package com.qq.client.view;

import javax.swing.*;

import com.qq.client.controller.ManageClientConServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QqChat extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2870356635447654905L;
	
	JTextArea jTextArea;
	JTextField jTextField;
	JButton jButton;
	JPanel jPanel;
	String ownerId;
	String friendId;
	JScrollPane jscrollPane;
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//QqChat qqChat = new QqChat("1", "2");
		
	}
	
	public QqChat(String ownerId, String friend)
	{
		this.ownerId = ownerId;
		this.friendId = friend;
		jTextArea = new JTextArea();
		jTextArea.setEditable(false);// ����Ϊ�޷��༭���촰�ڵ���Ϣ
		jTextArea.setLineWrap(true);// �����Զ����й���  
		jTextArea.setWrapStyleWord(true);// ������в����ֹ��� 
		jscrollPane = new JScrollPane(jTextArea);// ���ı�����ӹ���������  
		
		jTextField = new JTextField(15);
		jButton = new JButton("����");
		// ���÷���ΪĬ�ϰ�ť
		this.getRootPane().setDefaultButton(jButton);
		jButton.addActionListener(this);
		jPanel = new JPanel();
		jPanel.add(jTextField);
		jPanel.add(jButton);
		
		this.add(jscrollPane,"Center");
		this.add(jPanel, "South");
		this.setTitle(ownerId + " ���ں� " + friend + " ����");
		this.setIconImage((new ImageIcon("image/headphoto.gif").getImage()));
		this.setSize(500,400);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		
	}

	//��ʾ��Ϣ
	public void showMessage(Message message){
		//����Ϊ����Ϣ����ʾ���ı���
		if(!(message.getMessage().equals(null) || message.getMessage().equals(""))) {
			System.out.println("message:" + message.getMessage());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E kk:mm:ss");
			String date = dateFormat.format(message.getSendTime());
			
			String info= date + ":\n" + 
					message.getSender()+" �� "+message.getGetter()+" ˵��"+message.getMessage()+"\r\n\n";
			this.jTextArea.append(info);
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
		//����û�����˷��Ͱ�ť,��message�����ݴ�����͵������
		if(arg0.getSource() == jButton)
		{
			Message message = new Message();
			message.setMessageType(MessageType.CommonChat);
			message.setGetter(this.friendId);
			message.setSender(this.ownerId);
			message.setMessage(jTextField.getText());
			message.setSendTime(new Date());
			this.showMessage(message);
			
			this.jTextField.setText("");
			
			//���͸������� 
			ObjectOutputStream objectOutputStream;
			try {
				objectOutputStream = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(ownerId).getSocket().getOutputStream());
				objectOutputStream.writeObject(message);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}

}
