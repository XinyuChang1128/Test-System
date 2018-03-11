package com.tarena.elts.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * ��¼���� ���ڹ��߲�ʹ��awt��ʹ��swing
 */
public class LoginFrame extends JFrame {
	/**
	 * ���췽��
	 * 
	 */
	private JTextField idField;
	private JPasswordField passwordField;
	private ClientContext clientContext;
	private JLabel message;//������ʾ������Ϣ��label
	
	


	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public LoginFrame() {
		init();// ��ʼ������

	}

	/**
	 * �����ʼ��
	 * 
	 */
	private void init() {
		// ���ô��ڱ���
		this.setTitle("��¼ϵͳ");
		// ���ô��ڴ�С
		this.setSize(260, 200);
		// ����
		this.setLocationRelativeTo(null);
		// �򴰿���������
		this.setContentPane(createContentPane());
		
		/**���ô���Ĭ�ϵĹرշ�ʽ
		 * Ĭ������µ�������ڹرհ�ťʱ��Ĭ�ϲ���Ϊ��ǰ����
		 * ����
		 * ����Ҫ����Ĭ�ϵĹرշ�ʽ��������������
		 * �������õ�Ĭ�Ϲرշ�ʽ�ǣ�ɶҲ����
		 * 
		 */
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/**
		 * Ϊ��ǰ������Ӵ��ڼ��������Դ��ڹر��¼�������Ӧ
		   WindowAdapter����������
		   ����������һ��������ģʽ��
		   ��������Ҫʹ�ö���һ���ࣨ�����ǲ���ʹ�������෽ʽ��ȥʵ��
		   ģ���ӿ�ʱ��������Ҫ��ֻ�Դ˽ӿ��ж����ĳ�������󷽷�����Ȥ
		   ���ǣ���Ϊ�ӿڵ��ص㣬���ǵ������Ҫ�����еķ�����ʵ�֣�������
		   ���ǲ�����Ҫȥ��д��Щ�����߼����������������ǵ����оͻ��кܶ�շ���
		   ��������û���߼�����Ϊ�˱����������������������ͳ�ƻ�ʹ��������ģʽ
		   ������ģʽ�У�����һ����ʵ���˽ӿڵ����з������������ڶ�����
		   ��ʱ�򣬾Ͳ���Ҫֱ��ʵ�ָýӿڣ�����ȥ�̳��������࣬��������ֻ��Ҫ��д���Ǹ���Ȥ�ķ���
		   �������ˣ����轫���еķ�������д��
		 */
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) { 
			
				clientContext.exit(LoginFrame.this);
				
				super.windowClosing(e);
			}
			
			
			
		});
		
	}

	// ������Panel
	private JPanel createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		//ΪPanel�ӱ߿�
		panel.setBorder(new EmptyBorder(15,10,10,10));
		/**
		 * North ��һ��label 
		 * center��һ��panel(����label�����������) 
		 * south ��һ��panel(������ť)
		 * 
		 */
		// JLabel label =new JLabel("��¼ϵͳ");
		panel.add(new JLabel("��¼ϵͳ",JLabel.CENTER), BorderLayout.NORTH);
		panel.add(createCenterPane(), BorderLayout.CENTER);
		panel.add(creatBtnPane(),BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createCenterPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(8,8,8,8));
		/**
		 * north����û�������������panel;
		 */
		panel.add(creatIdPwdPane(), BorderLayout.NORTH);
	/**
	 * center���������ʾ������Ϣ��label
	 */
	     message=new JLabel("",JLabel.CENTER);
		panel.add(message,BorderLayout.CENTER);
		return panel;
	}

	private JPanel creatIdPwdPane() {
		/**
		 * ����һ�У�ÿ�д��һ�����ݣ�id �����,���� �����
		 */
		JPanel panel = new JPanel(new GridLayout(2, 1,0,6));
		
		// ��һ��
		panel.add(createIdPane());
		// �ڶ���
		panel.add(createPwdPane());
		return panel;
	}

	private JPanel createIdPane() {
		JPanel panel = new JPanel(new BorderLayout(6,0));
		panel.add(new JLabel("��ţ�"), BorderLayout.WEST);
		/**
		 * ע�⣡������
		 */
		 idField = new JTextField();
		panel.add(idField, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createPwdPane() {
		JPanel panel = new JPanel(new BorderLayout(6,0));
		panel.add(new JLabel("���룺"), BorderLayout.WEST);
		/**
		 * ע��
		 */
	    passwordField=new JPasswordField();
		//������������뷨��Ӧ��linux����������
		passwordField.enableInputMethods(true);
		panel.add(passwordField, BorderLayout.CENTER);
		return panel;
	}
	/**
	 * ����������ť��Panel
	 */
	private JPanel creatBtnPane(){
		JPanel panel=new JPanel();
		JButton login=new JButton("Login");
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.login();
			}
		});
		
		
		
		JButton cancel=new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.exit(LoginFrame.this);
			
			}
		});
			
	

		panel.add(login);
		panel.add(cancel);
		
		return panel;
	}

	public int getId(){
		String info=idField.getText();
		return Integer.parseInt(info);
		
	}

	public String getPassword(){
		char[] password=passwordField.getPassword();
		return new String(password);
}
//�˷��������õ�¼������ʾ������Ϣ
public void showError(String message){
	/**
	 * this.message��LoginFrame�����ԣ���һ��JLabel��������ʾ������Ϣ��label
	 * message�ǵ�ǰ�����Ĳ������Ǵ�����Ϣ���ַ���
	 */
    this.message.setText(message);
    this.message.setForeground(Color.RED);
    //���ڶ���
    final Timer timer=new Timer();
    //��ȡ��ǰ���ڵ����꣬��һ��Pointʵ�����棬������x��y
    final Point start=this.getLocation();
    //��Timer���һ���������������Ըı䴰�����꣬ģ��ζ�Ч��
    timer.schedule(new TimerTask(){
    	int []offset={-1,-2,-1,0,1,2,1,0};
    	int i=0;
    	public void run(){
    	Point p=getLocation();
    	p.x+=offset[i++ % offset.length];
    	setLocation(p);
    	}
    	
    },0,50);
    //��Timer���һ�������������������ֹͣTimer��ֹͣ�ζ�Ч��
    timer.schedule(new TimerTask(){
    	public void run(){
    		setLocation(start);//ֹͣǰ��ԭ��������
    		timer.cancel();//ֹͣTimer
    	}
    	
    },1000);
}
}
