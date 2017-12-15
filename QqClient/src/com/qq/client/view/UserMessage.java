package com.qq.client.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class UserMessage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6559146600773266559L;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JTextField userNameTextField;
	private JTextField ageTextField;
	private JTextField phoneTextField;
	private JTextField emailTextField;
	private JTextField birthdayTextField;
	private JButton yesButton;
	private JButton noButton;
	JTextArea signTextArea;
	private JComboBox<String> sexComboBox;
	private JComboBox<String> bloodComboBox;
	
	Message message;
	
	

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
					UserMessage frame = new UserMessage();
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
	public UserMessage() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 404, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDLabel = new JLabel("�ʺţ�");
		IDLabel.setBounds(51, 35, 52, 18);
		contentPane.add(IDLabel);
		
		JLabel userNameLabel = new JLabel("�û�����");
		userNameLabel.setBounds(34, 84, 65, 18);
		contentPane.add(userNameLabel);
		
		JLabel sexLabel = new JLabel("�Ա�");
		sexLabel.setBounds(253, 35, 72, 18);
		contentPane.add(sexLabel);
		
		JLabel ageLabel = new JLabel("���䣺");
		ageLabel.setBounds(253, 138, 52, 18);
		contentPane.add(ageLabel);
		
		JLabel phoneLabel = new JLabel("�ֻ��ţ�");
		phoneLabel.setBounds(34, 194, 72, 18);
		contentPane.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("���䣺");
		emailLabel.setBounds(51, 245, 52, 18);
		contentPane.add(emailLabel);
		
		JLabel birthdayLabel = new JLabel("���գ�");
		birthdayLabel.setBounds(51, 138, 52, 18);
		contentPane.add(birthdayLabel);
		
		IDTextField = new JTextField();
		IDTextField.setBounds(98, 29, 128, 24);
		contentPane.add(IDTextField);
		IDTextField.setColumns(10);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(98, 81, 128, 24);
		contentPane.add(userNameTextField);
		userNameTextField.setColumns(10);
		
		ageTextField = new JTextField();
		ageTextField.setBounds(305, 135, 52, 24);
		contentPane.add(ageTextField);
		ageTextField.setColumns(10);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(98, 191, 259, 24);
		contentPane.add(phoneTextField);
		phoneTextField.setColumns(10);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(98, 242, 259, 24);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);
		
		birthdayTextField = new JTextField();
		birthdayTextField.setBounds(98, 135, 130, 24);
		contentPane.add(birthdayTextField);
		birthdayTextField.setColumns(10);
		
		JLabel bloodLabel = new JLabel("Ѫ�ͣ�");
		bloodLabel.setBounds(253, 84, 72, 18);
		contentPane.add(bloodLabel);
		
		JLabel signLabel = new JLabel("����ǩ����");
		signLabel.setBounds(24, 292, 83, 18);
		contentPane.add(signLabel);
		
		yesButton = new JButton("ȷ���޸�");
		yesButton.setBounds(64, 374, 113, 27);
		contentPane.add(yesButton);
		yesButton.addActionListener(this);
		
		noButton = new JButton("ȡ��");
		noButton.setBounds(225, 374, 113, 27);
		contentPane.add(noButton);
		noButton.addActionListener(this);
		
		signTextArea = new JTextArea();
		signTextArea.setBounds(98, 290, 259, 63);
		contentPane.add(signTextArea);
		
		sexComboBox = new JComboBox<String>();
		sexComboBox.addItem("��");
		sexComboBox.addItem("Ů");		
		sexComboBox.setBounds(305, 32, 52, 24);
		contentPane.add(sexComboBox);
		sexComboBox.setEditable(false);
		
		bloodComboBox = new JComboBox<String>();
		bloodComboBox.addItem("A");
		bloodComboBox.addItem("B");
		bloodComboBox.addItem("AB");
		bloodComboBox.addItem("O");
		bloodComboBox.setBounds(305, 81, 52, 24);
		contentPane.add(bloodComboBox);
		sexComboBox.setEditable(false);
		
		
		
		this.setTitle("��������");
		this.setResizable(false);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource() == yesButton) {
			
			//��ȡ������Ϣ,�������ݿ�ĸ�������
			Message message = getUserDataMassage();
			//���͸����������ȴ����ص���Ϣ
			Message messageReturn = waitServiceMessage(message);
			
			if(messageReturn.getMessageType().equals(MessageType.UserDataUpdateSuccess)) {
				JOptionPane.showMessageDialog(this, "���³ɹ�");
			}
		}
		else if(e.getSource() == noButton) {
			this.dispose();			
		}
	}
	
	public Message waitServiceMessage(Message message) 
	{
		//�������ӣ�������Ϣ
		Socket socket;
		Message messageReturn = null;
		try {
			socket = new Socket("127.0.0.1", 9999);
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(message);
			
			//�ȴ�������������Ϣ
			ObjectInputStream objectIntputStream;
			objectIntputStream = new ObjectInputStream(socket.getInputStream());
			messageReturn=(Message) objectIntputStream.readObject();
			
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
		
		return messageReturn;
	}
	
	//��ȡ����ĸ���������Ϣ
	public Message getUserDataMassage()
	{
		String userId = null, userName = null, sex = null, blood = null, birthday = null, phoneNum = null, email = null, sign = null;
		int age = 0;
		userId = IDTextField.getText().trim();
		userName = userNameTextField.getText().trim();
		sex = sexComboBox.getSelectedItem().toString();
		blood = bloodComboBox.getSelectedItem().toString();
		birthday = birthdayTextField.getText().trim();
		age = Integer.parseInt(ageTextField.getText());
		phoneNum = phoneTextField.getText().trim();
		email = emailTextField.getText().trim();
		sign = signTextArea.getText().trim();
		
		UserData userData = new UserData();
		User user = new User();
		Message message = new Message();
		
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(birthday);
			
			userData.setBirthday(date);
			userData.setAge(age);
			userData.setBlood(blood);
			userData.setEmail(email);
			userData.setPhoneNum(phoneNum);
			userData.setSex(sex);
			userData.setSign(sign);
			
			user.setUserData(userData);
			user.setUserId(userId);
			user.setUserName(userName);
			
			message.setUser(user);
			message.setMessageType(MessageType.UserData);
		} catch (ParseException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		return message;
	}

	//�����ݿ��ж�ȡ���ĸ����������õ�������
	public void setUserDataMessage(Message message) 
	{
		User user = new User();
		UserData userData = new UserData();
		
		user = message.getUser();
		userData = user.getUserData();
		String date = null;
		if(userData.getBirthday() != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date = format.format(userData.getBirthday());
		}
		
		IDTextField.setText(user.getUserId());
		userNameTextField.setText(user.getUserName());
		sexComboBox.setSelectedItem(userData.getSex());
		bloodComboBox.setSelectedItem(userData.getBlood());
		
		birthdayTextField.setText(date);
		ageTextField.setText(Integer.toString((userData.getAge())));
		phoneTextField.setText(userData.getPhoneNum());
		emailTextField.setText(userData.getEmail());
		signTextArea.setText(userData.getSign());
		
	}
	//�������û��ĸ���������Ϊ���ɱ༭
	public void setEdit(boolean isEdit)
	{
		if(isEdit) {
			IDTextField.setEditable(false);
			userNameTextField.setEditable(true);
			sexComboBox.setEditable(true);
			bloodComboBox.setEditable(true);
			birthdayTextField.setEditable(true);
			ageTextField.setEditable(true);
			phoneTextField.setEditable(true);
			emailTextField.setEditable(true);
			signTextArea.setEditable(true);
		}else {
			yesButton.setEnabled(false);
			IDTextField.setEditable(false);
			userNameTextField.setEditable(false);
			sexComboBox.setEditable(false);
			bloodComboBox.setEditable(false);
			birthdayTextField.setEditable(false);
			ageTextField.setEditable(false);
			phoneTextField.setEditable(false);
			emailTextField.setEditable(false);
			signTextArea.setEditable(false);
		}
	}
	
}
