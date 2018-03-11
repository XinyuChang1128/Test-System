package com.tarena.elts.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;

/**
 * ���Դ���
 * 
 */
public class ExamFrame extends JFrame {

	private ClientContext clientContext;
    //��Ӵ����ԣ����ڼ�¼��ǰ������Ϣ
	private ExamInfo examInfo;
	//������ʾ������Ϣ��label
	private JLabel examInfoLabel;
	//������ʾ�����TestArea
	private   JTextArea  questionArea;
	private JLabel questionCount; 
	private JLabel timer;
	private JButton prev;
	private JButton next;
	private JButton send;
	//Option����ô����ĸ��û�ѡ��Ķ�ѡ��
	private Option options[]=new Option[4];
	
	
	public ExamFrame() {
		init();
	}
   public void setClientContext(ClientContext clientContext){
	   this.clientContext=clientContext;
   }
 	
	private void init() {
		this.setTitle("���ڿƼ����߲���");
		this.setSize(600, 380);
		this.setLocationRelativeTo(null);
		this.setContentPane(createContentPane());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
			
				clientContext.send();
			}
		});
	}

	private JPanel createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(6,6,6,6));
		/**
		 * north һ��ͼƬ cent ���Ե������Ϣ��ѡ���panel south ������ť��������Ϣ��panle
		 */
		ImageIcon image = new ImageIcon(this.getClass().getResource(
				"exam_title.png"));
		panel.add(new JLabel(image), BorderLayout.NORTH);

		panel.add(creatCenterPane(), BorderLayout.CENTER);
		
		panel.add(createToolsPane(),BorderLayout.SOUTH);
		return panel;
	}
	private JPanel createToolsPane(){
		JPanel panel=new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(0,10,0,10));
		/**
		 * west ���Ե������ʾ center���ܰ�ť��panel eastʣ��ʱ����ʾ
		 */
		// ע�⣡����������������
	    questionCount=new JLabel("��Ŀ�ǣ�20��1��");
		timer=new JLabel("ʣ��ʱ�䣺222��");
		panel.add(questionCount,BorderLayout.WEST);
		panel.add(createBtnPane(),BorderLayout.CENTER);
		panel.add(timer,BorderLayout.EAST);
		
		return panel;
	}
	/**
	 * ��š���һ�⡱����һ�⡱��������ť��panel
	 * 
	 * @return
	 */
	private JPanel createBtnPane(){
		JPanel panel=new JPanel();
		// ע�⣡��������������������
	    prev=new JButton("��һ��");
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.prev();
			}
		});
	    next=new JButton("��һ��");
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.next();
			}
		});
	    send=new JButton("����");
	    send.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		clientContext.send();
	    	}
	    }
	    );
		panel.add(prev);
		panel.add(next);
		panel.add(send);
		
		
		return panel;
	}
	/**
	 * ������ʾ���Ա��⣬��ɵ��ı����Լ�ѡ���Panel
	 * 
	 * 
	 * @return
	 */
	private JPanel creatCenterPane() {
		JPanel panel = new JPanel(new BorderLayout());
		// ע�� ������
		examInfoLabel=new JLabel("������XXX ����: XXX  ����ʱ��:XXX",
				JLabel.CENTER);
		panel.add(examInfoLabel,BorderLayout.NORTH);
		panel.add(createQuestionPane(),BorderLayout.CENTER);
		panel.add(creatOptionsPane(),BorderLayout.SOUTH);
		return panel;
	}
	/**
	 * ��������4��ѡ��(��ѡ��)��Panel
	 * 
	 * @return
	 */
	private JPanel creatOptionsPane(){
		JPanel panel=new JPanel();
	
		
		Option a=new Option(0,"A");
		Option b=new Option(1,"B");
		Option c=new Option(2,"C");
		Option d=new Option(3,"D");
	
		options[0]=a;
		options[1]=b;
		options[2]=c;
		options[3]=d;
		
		
		
		panel.add(a);
		panel.add(b);
		panel.add(c);
		panel.add(d);
		
		return panel;
	}
	
	/**
	 * ��������������Panel,������ʾ������ı���
	 * 
	 * @return
	 */
   private JScrollPane createQuestionPane(){
	   JScrollPane panel=new JScrollPane();
	   // �����
	   panel.setBorder(new TitledBorder("��Ŀ"));
	   
	  // ע�⣡��������������������
	    questionArea=new JTextArea();
//	    questionArea.setText("����\nA.\nB.\nC.\nD.");
	    questionArea.setLineWrap(true);
	   // �����ı��򲻿ɱ༭
	    questionArea.setEditable(false);
	   // ���ļ�����ӵ�JScrollPanel��viewport�У����������������
	    panel.getViewport().add(questionArea);
	    
	    
	    
	    return panel;
   }
   /**
    * ���¿�����Ϣ
    * @param examInfo
    */
   public void updateExamInfo(ExamInfo examInfo){
	   this.examInfo=examInfo;
	   //��������Ϣ��Label����ʾ
	   examInfoLabel.setText(examInfo.toString());
	   
   }
   /**
    * ���¿���
    * @param question
    */
   public void updateQuestion(QuestionInfo question){
	  //��������ʾ��questionArea��
	   questionArea.setText(question.toString());
	   //������ʾ��ǰ��õ�label
	   int questionIndex=question.getQuestionIndex()+1;
	   int count=examInfo.getQuestionCount();
	   String str="��Ŀ��"+count+"��"+questionIndex+"��";
	   questionCount.setText(str);//��label��������
	   updateButton(question.getQuestionIndex());
	   updateOptions(question.getUserAnswers());
   
   }
   /**
    * �����û��𰸣���4��ѡ��Ķ�ѡ���Ӧ��ѡ
    * @param answers
    */
  private void updateOptions(List<Integer> answers){
	 for(Option option:options){
		 option.setSelected(answers.contains(option.value));//���ù�ѡ
	 }
	  
	  
  }
   
   
   
   /**
    * ���°�ť����ǰ������ʾ���ǵ�һ��ʱ����һ�ⰴť���ɵ�
    * ͬ����һ��
    *
    */
   private void updateButton(int questionIndex){
	   //���ǵ�һ��Ϳɵ� 
	   prev.setEnabled(questionIndex!=0);
	   //�������һ��Ϳɵ�
	   next.setEnabled(questionIndex!=examInfo.getQuestionCount()-1);
	   
   }
   //��ȡ��ǰ�û��Ե�ǰ����Ĵ�
   public List<Integer> getUserAnswers(){
	   List<Integer> answers =new ArrayList<Integer>();
	   for(Option option :options){
		   //��ǰ��ѡ���Ƿ�ѡ��
		   if(option.isSelected()){
			   /**
			    * option��Value�����������Լ������
			    * ����Aѡ����˵value���Ե�ֵΪ0
			    * BΪ1
			    */
			  answers.add(option.value);
		   }
	   }
	   
	   
	   return answers;
	   
   }
public void updateTimer(long h,long m,long s){
	if(h==0&&m<5){
		timer.setForeground(Color.RED);
	
	if(m==0&&s%2==0){
		timer.setForeground(Color.BLACK);
	}
	}
	String str="ʣ��ʱ�䣺"+
	              (h<10?"0"+h:h)+"��"+
	              (m<10?"0"+m:m)+"��"+
	              (s<10?"0"+s:s);
	timer.setText(str);
}   
   
   
  
   /**
	 * ʹ���ڲ��ඨ��һ����ѡ������� Ŀ�ģ����һ�����ԣ������պ����û���ʱ���߼� ��ӵ����ԣ�����𰸱��
	 */
   class Option extends JCheckBox{
	   int value;// �𰸱��
	   public Option(int value,String txt){
		  /**
			 * ���ø���JCheckBox�Ĺ��췽�� ����Ĳ�����һ���ַ������������ڶ�ѡ���Ա���ʾ���ַ����� Ϊ��ѡ����ʾ
			 */
		   super(txt);
		   
		   this.value=value;
	   
	   }
	   
	   
   }


}

