package com.tarena.elts.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tarena.elts.entity.User;

/**
 * 菜单窗口
 * 
 */
public class MenuFrame extends JFrame {
	private JLabel info;//用于显示欢迎信息的label
	private ClientContext clientContext;
	private JButton result;
	private JButton message;
	public void setClientContext(ClientContext clientContext){
		this.clientContext=clientContext;
	}
	public MenuFrame() {
		init();
	}

	private void init() {
		this.setTitle("达内科技在线测评");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setContentPane(createContentPane());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				clientContext.exit(MenuFrame.this);
				super.windowClosing(e);
			}
		});
		
	}
	
	/**
	 * 创建Panel
	 * 
	 * @return
	 */
	private JPanel createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		/**
		 * north 一张图 
		 * center一张panel(4个图片按钮) 
		 * south 一个字符串
		 */

		/**
		 * 显示图片的步骤： 
		 *   1：创建一个ImageIcon
		 *   2:创建一个Label用于显示ImagerIcon
		 */
		// 1
		ImageIcon image = new ImageIcon(this.getClass()
				.getResource("title.png"));
		// 2
		JLabel imageLabel = new JLabel(image);
		panel.add(imageLabel, BorderLayout.NORTH);
		panel.add(new JLabel("达内科技－－版权所有 盗版必究", JLabel.RIGHT),
				BorderLayout.SOUTH);
		panel.add(createMenuPane(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createMenuPane() {
		JPanel panel = new JPanel(new BorderLayout());
		/**
		 * north 欢迎文字 
		 * center 存放四个图片按钮的panel
		 */
		//注意！！
        info=new JLabel("XXX同学你好",JLabel.CENTER);
		panel.add(info,BorderLayout.NORTH);
		panel.add(createBtnPane(),BorderLayout.CENTER);
		
		return panel;

	}
	/**
	 * 创建包含4个图片按钮的panel
	 * @return
	 */
	private JPanel createBtnPane(){
		JPanel panel=new JPanel();
		/**
		 * 创建图片按钮步骤
		 * 1：创建ImageIcon
		 * 2:创建Button,使用重载方法的构造方法
		 *  JButoon(String txt,ImageIcon icon)
		 *  或者后期设置图片，调用JButton的方法setIcon(ImageIcon icon)
		 */
		//注意！！！！！！！！！！！！！
		//开始按钮
		
		JButton start=createImageButton("exam.png","开始");
		
		//添加一个按钮的点击事件
		start.addActionListener(new ActionListener(){
			//当开始考试按钮被点击后，通知控制器开始考试
			public void actionPerformed(ActionEvent e){
			clientContext.start();	
			}
		});
        //分数
		result=createImageButton("result.png","分数");
		//考试规则
		result.addActionListener(new ActionListener(){
			//当开始考试按钮被点击后，通知控制器开始考试
			public void actionPerformed(ActionEvent e){
			clientContext.result();	
			}
		});
		
	    message=createImageButton("message.png","考试规则");
		message.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
			     	clientContext.message(MenuFrame.this);
				    }
				    });
		JButton exit=createImageButton("exit.png","离开");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.exit(MenuFrame.this);
			
			}
		});
			
	
		panel.add(start);
		panel.add(result);
		panel.add(message);
		panel.add(exit);  
		
		
		return panel;
		
	
	
	}
	/**
	 * 创建图片按钮步骤
	 * 1：创建ImageIcon
	 * 2:创建Button,使用重载方法的构造方法
	 *  JButoon(String txt,ImageIcon icon)
	 *  或者后期设置图片，调用JButton的方法setIcon(ImageIcon icon)
	 */
	private JButton createImageButton(String imageName ,String txt){
		//1
		ImageIcon icon=new ImageIcon(
				this.getClass().getResource(imageName));
		//2
		JButton btn=new JButton(txt,icon);
		//3是指按钮上的文字在中下放，水平居中，垂直底部
		btn.setVerticalTextPosition(JButton.BOTTOM);
		btn.setHorizontalTextPosition(JButton.CENTER);
		return btn;
	
	}
	/**
	 * 此方法用于更新菜单窗口中的用于显示欢迎信息的JLabel
	 * 
	 * @param user当前登录成功的用户
	 */
	public void updateView(User user){
		String str="欢迎"+user.getName()+"参加考试";
		//向用于显示欢迎信息的label中设置欢迎信息
		info.setText(str);
	}


}
