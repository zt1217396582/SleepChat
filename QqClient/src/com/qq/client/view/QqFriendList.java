/**
 * �ҵĺ����б�Ҳ����İ���ˣ�������
 */

package com.qq.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.qq.client.controller.*;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class QqFriendList extends JFrame implements ActionListener, MouseListener{
	//�����һ�ſ�Ƭ(�ҵĺ���)
	
	JPanel jPanelFriend, jPanelFriend2, jPanelFriend3;
	JButton jPanelFriend_Button, jPanelFriend_Button2, jPanelFriend_Button3;
	JScrollPane jScrollPaneFriend;
	String ownerId;
	//����ڶ��ſ�Ƭ(İ����)
	
	JPanel jPanelStranger, jPanelStranger2, jPanelStranger3;
	JButton jPanelStranger_Button, jPanelStranger_Button2, jPanelStranger_Button3;
	JScrollPane jScrollPaneStranger;
	JLabel [] jLabels;
	//������JFrame���ó�CardLayout
	CardLayout cardLayout;
	final PopupMenu menu;
	MenuItem item;
	
	String userId;
	
	Message message = new Message();
	//��ȡ���һ�ε�¼ʱ��
	java.util.Date daTe=ClientLoginFrame.date;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		QqFriendList qqFriendList = new QqFriendList("1");

	}
	
	//�������ߺ������
	public void updateFriend(Message message){
		String onLineFriend[]=message.getMessage().split(" ");
		for(int i=0;i<onLineFriend.length;i++){
			jLabels[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
		}
	}
	
	//���������ж�
	public void offlineFriend(Message message){
		String offLineUserID=message.getMessage();
		System.out.println(offLineUserID);
		jLabels[Integer.parseInt(offLineUserID)-1].setEnabled(false);
		
	}

	public int userCount(Message message){
		//�������ӣ������������������Ϣ
		Socket socket;
		int usercount = 50;
		try {
			message.setMessageType(MessageType.GetuserCount);
			socket = new Socket("127.0.0.1", 9999);
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(message);
			
			//�ȴ����ݿⷵ�غ�����������Ϣ
			ObjectInputStream objectIntputStream;
			objectIntputStream = new ObjectInputStream(socket.getInputStream());
			Message messagereturn=(Message) objectIntputStream.readObject();
			
			if(messagereturn.getMessageType().equals(MessageType.GetuserCountSuccess)) {
				usercount=Integer.parseInt(messagereturn.getMessage());
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
		return usercount;
	}

	//�������ӣ������û�������Ϣ��
	public void setOffLineUser(Message message){
		try {
			ObjectOutputStream objectOutputStream=new ObjectOutputStream
					(ManageClientConServerThread.getClientConServerThread
							(ClientLoginFrame.userID).getSocket().getOutputStream());
			message.setMessageType(MessageType.UserOffLine);
			message.setSendTime(daTe);
			message.setMessage(ClientLoginFrame.userID);
			objectOutputStream.writeObject(message);
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
	
	public QqFriendList(String ownerId)
	{
		this.ownerId = ownerId;
		//�����һ�ſ�Ƭ����ʾ�����б�
		jPanelFriend_Button = new JButton("�ҵĺ���");
		jPanelFriend_Button2 = new JButton("İ����");
		jPanelFriend_Button2.addActionListener(this);
		jPanelFriend_Button3 = new JButton("������");
		
		jPanelFriend = new JPanel(new BorderLayout());
		//�ٶ���50������
		jPanelFriend2 = new JPanel(new GridLayout(50,1,4,4));
		//��jPanelFriend2��ʼ��50������
		jLabels = new JLabel[50];
		for(int i = 0; i < userCount(message); i++)
		{
			jLabels[i] = new JLabel( i+1+"", new ImageIcon("image/headphoto.gif"), JLabel.LEFT);
			jLabels[i].setEnabled(false);
			if(jLabels[i].getText().equals(ownerId)){
				jLabels[i].setEnabled(true);
			}
			jLabels[i].addMouseListener(this);
			jPanelFriend2.add(jLabels[i]);
		}

		jPanelFriend3 = new JPanel(new GridLayout(2,1));
		//��������ť���뵽jPanelFriend3
		jPanelFriend3.add(jPanelFriend_Button2);		
		jPanelFriend3.add(jPanelFriend_Button3);
		
		jScrollPaneFriend = new JScrollPane(jPanelFriend2);
		
		//��jPanelFriend��ʼ��
		jPanelFriend.add(jPanelFriend_Button, "North");
		jPanelFriend.add(jScrollPaneFriend, "Center");
		jPanelFriend.add(jPanelFriend3, "South");

		
		
		//����ڶ��ſ�Ƭ
		jPanelStranger_Button = new JButton("�ҵĺ���");
		jPanelStranger_Button.addActionListener(this);
		jPanelStranger_Button2 = new JButton("İ����");
		jPanelStranger_Button3 = new JButton("������");
		
		jPanelStranger = new JPanel(new BorderLayout());
		//�ٶ���20��İ����
		jPanelStranger2 = new JPanel(new GridLayout(20,1,4,4));	
		
		//��jPanelStranger2��ʼ��20��İ����
		JLabel [] jLabels2 = new JLabel[20];
		
		for(int i = 0; i < jLabels2.length; i++)
		{
			jLabels2[i] = new JLabel( i+1+"", new ImageIcon("image/headphoto.gif"), JLabel.LEFT);
			jPanelStranger2.add(jLabels2[i]);
		}
		
		jPanelStranger3 = new JPanel(new GridLayout(2,1));
		//��������ť���뵽jPanelFriend3
		jPanelStranger3.add(jPanelStranger_Button);		
		jPanelStranger3.add(jPanelStranger_Button2);
		
		jScrollPaneStranger = new JScrollPane(jPanelStranger2);
		
		//��jPanelStranger��ʼ��
		jPanelStranger.add(jPanelStranger3, "North");
		jPanelStranger.add(jScrollPaneStranger, "Center");
		jPanelStranger.add(jPanelStranger_Button3, "South");
		
		
		cardLayout = new CardLayout();
		this.setLayout(cardLayout);
		this.add(jPanelFriend, "1");
		this.add(jPanelStranger, "2");
		//�ڴ�����ʾ�Լ��ı��
		this.setTitle(ownerId);	
		
		//�Ҽ��˵�������
		menu=new PopupMenu();
		item = new MenuItem();
		item.setLabel("�û�����");
		menu.add(item);
		this.add(menu);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == item) {
					UserMessage userMessage= new UserMessage();
					
					Message message = new Message();
					User user = new User();
					user.setUserId(userId);
					message.setUser(user);
					message.setMessageType(MessageType.GetUserData);
					
					Message messageReturn = userMessage.waitServiceMessage(message);
					
					userMessage.setUserDataMessage(messageReturn);
					
					if(messageReturn.getUser().getUserId().equals(ClientLoginFrame.userID))
					{
						userMessage.setEdit(true);
					}else {
						userMessage.setEdit(false);
					}
					
				}
			}
		});
		
		
		//this.add(jPanelStranger, "Center");
		
		this.setSize(250,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�Թرմ���������Ӧ
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			setOffLineUser(message);
			System.exit(0);
			}
		});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		//��������İ���˰�ť������ʾ�ڶ��ſ�Ƭ
		if(arg0.getSource() == jPanelFriend_Button2)
		{
			cardLayout.show(this.getContentPane(), "2");
		}else if(arg0.getSource() == jPanelStranger_Button)
		{
			cardLayout.show(this.getContentPane(), "1");
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		//��Ӧ�û�˫�����¼������õ����ѵı��
		if(arg0.getClickCount() == 2 && arg0.getButton() == MouseEvent.BUTTON1)
		{
			//�õ��ú��ѵı��
			String friendNo = ((JLabel)arg0.getSource()).getText();
			//System.out.println("������" + friendNo + "����");
			QqChat qqChat=new QqChat(this.ownerId, friendNo);
		
			//�����������뵽������
			ManangeQqChat.addQqChat(this.ownerId+" "+friendNo, qqChat);
		
		}
		else if(arg0.getButton() == MouseEvent.BUTTON3)
		{
			menu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
			System.out.println("x:" + arg0.getX() + "y:" + arg0.getY());
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		JLabel j1 = (JLabel)arg0.getSource();
		j1.setForeground(Color.red);
		userId = j1.getText();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		JLabel j1 = (JLabel)arg0.getSource();
		j1.setForeground(Color.BLACK);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

}
