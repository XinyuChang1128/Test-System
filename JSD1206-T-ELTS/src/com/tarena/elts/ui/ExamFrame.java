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
 * 考试窗口
 * 
 */
public class ExamFrame extends JFrame {

	private ClientContext clientContext;
    //添加此属性，用于记录当前考试信息
	private ExamInfo examInfo;
	//用于显示考试信息的label
	private JLabel examInfoLabel;
	//用于显示考题的TestArea
	private   JTextArea  questionArea;
	private JLabel questionCount; 
	private JLabel timer;
	private JButton prev;
	private JButton next;
	private JButton send;
	//Option数组么存放四个用户选项的多选框
	private Option options[]=new Option[4];
	
	
	public ExamFrame() {
		init();
	}
   public void setClientContext(ClientContext clientContext){
	   this.clientContext=clientContext;
   }
 	
	private void init() {
		this.setTitle("达内科技在线测评");
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
		 * north 一张图片 cent 考试的题干信息及选项的panel south 操作按钮及考试信息的panle
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
		 * west 考试的题号显示 center功能按钮的panel east剩余时间显示
		 */
		// 注意！！！！！！！！！
	    questionCount=new JLabel("题目是：20的1题");
		timer=new JLabel("剩余时间：222秒");
		panel.add(questionCount,BorderLayout.WEST);
		panel.add(createBtnPane(),BorderLayout.CENTER);
		panel.add(timer,BorderLayout.EAST);
		
		return panel;
	}
	/**
	 * 存放“上一题”“下一题”“交卷”按钮的panel
	 * 
	 * @return
	 */
	private JPanel createBtnPane(){
		JPanel panel=new JPanel();
		// 注意！！！！！！！！！！！
	    prev=new JButton("上一题");
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.prev();
			}
		});
	    next=new JButton("下一题");
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.next();
			}
		});
	    send=new JButton("交卷");
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
	 * 创建显示考试标题，题干的文本域以及选项的Panel
	 * 
	 * 
	 * @return
	 */
	private JPanel creatCenterPane() {
		JPanel panel = new JPanel(new BorderLayout());
		// 注意 ！！！
		examInfoLabel=new JLabel("姓名：XXX 考试: XXX  考试时间:XXX",
				JLabel.CENTER);
		panel.add(examInfoLabel,BorderLayout.NORTH);
		panel.add(createQuestionPane(),BorderLayout.CENTER);
		panel.add(creatOptionsPane(),BorderLayout.SOUTH);
		return panel;
	}
	/**
	 * 创建带有4个选项(多选框)的Panel
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
	 * 创建带滚动条的Panel,用于显示考题的文本域
	 * 
	 * @return
	 */
   private JScrollPane createQuestionPane(){
	   JScrollPane panel=new JScrollPane();
	   // 标题框
	   panel.setBorder(new TitledBorder("题目"));
	   
	  // 注意！！！！！！！！！！！
	    questionArea=new JTextArea();
//	    questionArea.setText("问题\nA.\nB.\nC.\nD.");
	    questionArea.setLineWrap(true);
	   // 设置文本域不可编辑
	    questionArea.setEditable(false);
	   // 把文件域添加到JScrollPanel的viewport中，否则滚动条不正常
	    panel.getViewport().add(questionArea);
	    
	    
	    
	    return panel;
   }
   /**
    * 更新考试信息
    * @param examInfo
    */
   public void updateExamInfo(ExamInfo examInfo){
	   this.examInfo=examInfo;
	   //将考试信息在Label中显示
	   examInfoLabel.setText(examInfo.toString());
	   
   }
   /**
    * 更新考题
    * @param question
    */
   public void updateQuestion(QuestionInfo question){
	  //将考题显示在questionArea上
	   questionArea.setText(question.toString());
	   //用于显示当前题好的label
	   int questionIndex=question.getQuestionIndex()+1;
	   int count=examInfo.getQuestionCount();
	   String str="题目："+count+"的"+questionIndex+"题";
	   questionCount.setText(str);//给label设置文字
	   updateButton(question.getQuestionIndex());
	   updateOptions(question.getUserAnswers());
   
   }
   /**
    * 根据用户答案，将4个选项的多选框对应勾选
    * @param answers
    */
  private void updateOptions(List<Integer> answers){
	 for(Option option:options){
		 option.setSelected(answers.contains(option.value));//设置勾选
	 }
	  
	  
  }
   
   
   
   /**
    * 更新按钮，当前窗口显示的是第一题时，上一题按钮不可点
    * 同理下一题
    *
    */
   private void updateButton(int questionIndex){
	   //不是第一题就可点 
	   prev.setEnabled(questionIndex!=0);
	   //不是最后一题就可点
	   next.setEnabled(questionIndex!=examInfo.getQuestionCount()-1);
	   
   }
   //获取当前用户对当前考题的答案
   public List<Integer> getUserAnswers(){
	   List<Integer> answers =new ArrayList<Integer>();
	   for(Option option :options){
		   //当前多选框是否被选中
		   if(option.isSelected()){
			   /**
			    * option的Value属性是我们自己定义的
			    * 对于A选项来说value属性的值为0
			    * B为1
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
	String str="剩余时间："+
	              (h<10?"0"+h:h)+"："+
	              (m<10?"0"+m:m)+"："+
	              (s<10?"0"+s:s);
	timer.setText(str);
}   
   
   
  
   /**
	 * 使用内部类定义一个多选框的子类 目的：添加一个属性，便于日后处理用户答案时的逻辑 添加的属性，代表答案编号
	 */
   class Option extends JCheckBox{
	   int value;// 答案编号
	   public Option(int value,String txt){
		  /**
			 * 调用父类JCheckBox的构造方法 传入的参数是一个字符串，作用是在多选框旁边显示此字符串作 为多选的提示
			 */
		   super(txt);
		   
		   this.value=value;
	   
	   }
	   
	   
   }


}

