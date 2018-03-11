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
 * 登录界面 窗口工具不使用awt，使用swing
 */
public class LoginFrame extends JFrame {
	/**
	 * 构造方法
	 * 
	 */
	private JTextField idField;
	private JPasswordField passwordField;
	private ClientContext clientContext;
	private JLabel message;//用于显示错误信息的label
	
	


	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public LoginFrame() {
		init();// 初始化界面

	}

	/**
	 * 界面初始化
	 * 
	 */
	private void init() {
		// 设置窗口标题
		this.setTitle("登录系统");
		// 设置窗口大小
		this.setSize(260, 200);
		// 居中
		this.setLocationRelativeTo(null);
		// 向窗口中添加组件
		this.setContentPane(createContentPane());
		
		/**设置窗口默认的关闭方式
		 * 默认情况下当点击窗口关闭按钮时的默认操作为当前窗口
		 * 隐藏
		 * 我们要设置默认的关闭方式，进制它这样做
		 * 我们设置的默认关闭方式是：啥也不干
		 * 
		 */
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/**
		 * 为当前窗口添加窗口监听器，对窗口关闭事件作出响应
		   WindowAdapter叫做适配器
		   这里引出了一个适配器模式：
		   当我们需要使用定义一个类（无论是不是使用匿名类方式）去实现
		   模个接口时，我们需要是只对此接口中定义的某几个抽象方法感兴趣
		   但是，因为接口的特点，我们的类必须要将所有的方法都实现，无论我
		   们是不是需要去书写这些方法逻辑，但是这样作我们的类中就会有很多空方法
		   （方法中没有逻辑），为了避免这样的情况发生，我们统称会使用适配器模式
		   适配器模式中，会有一个类实现了接口的所有方法，当我们在定义类
		   的时候，就不需要直接实现该接口，而是去继承适配器类，这样我们只需要重写我们感兴趣的方法
		   己可以了，无需将所有的方法都重写。
		 */
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) { 
			
				clientContext.exit(LoginFrame.this);
				
				super.windowClosing(e);
			}
			
			
			
		});
		
	}

	// 创建主Panel
	private JPanel createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		//为Panel加边框
		panel.setBorder(new EmptyBorder(15,10,10,10));
		/**
		 * North 放一个label 
		 * center放一个panel(两个label和两个输入框) 
		 * south 放一个panel(两个按钮)
		 * 
		 */
		// JLabel label =new JLabel("登录系统");
		panel.add(new JLabel("登录系统",JLabel.CENTER), BorderLayout.NORTH);
		panel.add(createCenterPane(), BorderLayout.CENTER);
		panel.add(creatBtnPane(),BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createCenterPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(8,8,8,8));
		/**
		 * north存放用户名密码的输入框panel;
		 */
		panel.add(creatIdPwdPane(), BorderLayout.NORTH);
	/**
	 * center存放用于显示错误信息的label
	 */
	     message=new JLabel("",JLabel.CENTER);
		panel.add(message,BorderLayout.CENTER);
		return panel;
	}

	private JPanel creatIdPwdPane() {
		/**
		 * 两行一列，每列存放一组内容，id 输入框,密码 输入框
		 */
		JPanel panel = new JPanel(new GridLayout(2, 1,0,6));
		
		// 第一行
		panel.add(createIdPane());
		// 第二行
		panel.add(createPwdPane());
		return panel;
	}

	private JPanel createIdPane() {
		JPanel panel = new JPanel(new BorderLayout(6,0));
		panel.add(new JLabel("编号："), BorderLayout.WEST);
		/**
		 * 注意！！！！
		 */
		 idField = new JTextField();
		panel.add(idField, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createPwdPane() {
		JPanel panel = new JPanel(new BorderLayout(6,0));
		panel.add(new JLabel("密码："), BorderLayout.WEST);
		/**
		 * 注意
		 */
	    passwordField=new JPasswordField();
		//允许密码框输入法，应对linux的输入问题
		passwordField.enableInputMethods(true);
		panel.add(passwordField, BorderLayout.CENTER);
		return panel;
	}
	/**
	 * 创建两个按钮的Panel
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
//此方法用于让登录窗口显示错误信息
public void showError(String message){
	/**
	 * this.message是LoginFrame的属性，是一个JLabel，用于显示错误信息的label
	 * message是当前方法的参数，是错误信息的字符串
	 */
    this.message.setText(message);
    this.message.setForeground(Color.RED);
    //窗口抖动
    final Timer timer=new Timer();
    //获取当前窗口的坐标，用一个Point实例保存，其中有x和y
    final Point start=this.getLocation();
    //给Timer添加一个任务，用于周期性改变窗口坐标，模拟晃动效果
    timer.schedule(new TimerTask(){
    	int []offset={-1,-2,-1,0,1,2,1,0};
    	int i=0;
    	public void run(){
    	Point p=getLocation();
    	p.x+=offset[i++ % offset.length];
    	setLocation(p);
    	}
    	
    },0,50);
    //给Timer添加一个任务，用于在若干秒后停止Timer，停止晃动效果
    timer.schedule(new TimerTask(){
    	public void run(){
    		setLocation(start);//停止前还原窗口坐标
    		timer.cancel();//停止Timer
    	}
    	
    },1000);
}
}
