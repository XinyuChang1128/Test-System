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
 * �˵�����
 * 
 */
public class MenuFrame extends JFrame {
	private JLabel info;//������ʾ��ӭ��Ϣ��label
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
		this.setTitle("���ڿƼ����߲���");
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
	 * ����Panel
	 * 
	 * @return
	 */
	private JPanel createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		/**
		 * north һ��ͼ 
		 * centerһ��panel(4��ͼƬ��ť) 
		 * south һ���ַ���
		 */

		/**
		 * ��ʾͼƬ�Ĳ��裺 
		 *   1������һ��ImageIcon
		 *   2:����һ��Label������ʾImagerIcon
		 */
		// 1
		ImageIcon image = new ImageIcon(this.getClass()
				.getResource("title.png"));
		// 2
		JLabel imageLabel = new JLabel(image);
		panel.add(imageLabel, BorderLayout.NORTH);
		panel.add(new JLabel("���ڿƼ�������Ȩ���� ����ؾ�", JLabel.RIGHT),
				BorderLayout.SOUTH);
		panel.add(createMenuPane(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createMenuPane() {
		JPanel panel = new JPanel(new BorderLayout());
		/**
		 * north ��ӭ���� 
		 * center ����ĸ�ͼƬ��ť��panel
		 */
		//ע�⣡��
        info=new JLabel("XXXͬѧ���",JLabel.CENTER);
		panel.add(info,BorderLayout.NORTH);
		panel.add(createBtnPane(),BorderLayout.CENTER);
		
		return panel;

	}
	/**
	 * ��������4��ͼƬ��ť��panel
	 * @return
	 */
	private JPanel createBtnPane(){
		JPanel panel=new JPanel();
		/**
		 * ����ͼƬ��ť����
		 * 1������ImageIcon
		 * 2:����Button,ʹ�����ط����Ĺ��췽��
		 *  JButoon(String txt,ImageIcon icon)
		 *  ���ߺ�������ͼƬ������JButton�ķ���setIcon(ImageIcon icon)
		 */
		//ע�⣡������������������������
		//��ʼ��ť
		
		JButton start=createImageButton("exam.png","��ʼ");
		
		//���һ����ť�ĵ���¼�
		start.addActionListener(new ActionListener(){
			//����ʼ���԰�ť�������֪ͨ��������ʼ����
			public void actionPerformed(ActionEvent e){
			clientContext.start();	
			}
		});
        //����
		result=createImageButton("result.png","����");
		//���Թ���
		result.addActionListener(new ActionListener(){
			//����ʼ���԰�ť�������֪ͨ��������ʼ����
			public void actionPerformed(ActionEvent e){
			clientContext.result();	
			}
		});
		
	    message=createImageButton("message.png","���Թ���");
		message.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
			     	clientContext.message(MenuFrame.this);
				    }
				    });
		JButton exit=createImageButton("exit.png","�뿪");
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
	 * ����ͼƬ��ť����
	 * 1������ImageIcon
	 * 2:����Button,ʹ�����ط����Ĺ��췽��
	 *  JButoon(String txt,ImageIcon icon)
	 *  ���ߺ�������ͼƬ������JButton�ķ���setIcon(ImageIcon icon)
	 */
	private JButton createImageButton(String imageName ,String txt){
		//1
		ImageIcon icon=new ImageIcon(
				this.getClass().getResource(imageName));
		//2
		JButton btn=new JButton(txt,icon);
		//3��ָ��ť�ϵ����������·ţ�ˮƽ���У���ֱ�ײ�
		btn.setVerticalTextPosition(JButton.BOTTOM);
		btn.setHorizontalTextPosition(JButton.CENTER);
		return btn;
	
	}
	/**
	 * �˷������ڸ��²˵������е�������ʾ��ӭ��Ϣ��JLabel
	 * 
	 * @param user��ǰ��¼�ɹ����û�
	 */
	public void updateView(User user){
		String str="��ӭ"+user.getName()+"�μӿ���";
		//��������ʾ��ӭ��Ϣ��label�����û�ӭ��Ϣ
		info.setText(str);
	}


}
