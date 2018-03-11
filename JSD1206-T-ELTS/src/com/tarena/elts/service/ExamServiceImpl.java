package com.tarena.elts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.Question;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;

/**
 * 业务逻辑实现类
 * 实现了业务逻辑接口，完成相应的核心功能逻辑
 */
public class ExamServiceImpl implements ExamService{

	/**此属性的作用是保存开始考试后生成的所有考题，这里集合中
	 * 存放的是Question而不是Question
	，因为考题要包含用户答案**/
	private List<QuestionInfo> paper =new ArrayList<QuestionInfo>();
	/**
	 * 此属性的作用是保存当前的登录用户信息
	 */
	private User user;
	private int score;
	private boolean finish;

	
	
	//提供数据的类
	private EntityContext entityContext;
	public void setEntityContext(EntityContext entityContext){
		this.entityContext=entityContext;
		/**
		 * 提供该属性的setter方法
		 * 不需要提供getter方法，因为当前属性只在当前类内使用，不需要提供给外界
		 * 
		 * 次方法的作用是让但前业务逻辑类认识一下为它提供数据的类
		 */
		
	}
	
	/**
    * 实现接口的用户登录逻辑
    */
	public User login(int id,String password)throws IdOrPwdException{
	  /**
	   * 1：通过提供数据的类EntityContext提供给定的用户id获取这个
	   * 用户的信息（User实例）
	   * 2：判断EntityContext返回的 User实例是否存在，若为null。说明
	   * 用户输入的id是错误的，查无此人（应告知控制台，抛出异常）
	   * 3：若User实例不为null，判断给定的密码和User实例中此人的密码
	   * 是否一致的，若不一致，密码输入错误，应告知控制层（抛出异常）
	   * 4：若密码输入一致，返回这个用户的User实例，表示登录成功
	   * 
	   * 
	   * 
	   */
	   //1
	   User user=entityContext.findUserById(id);
	   //2
	   if(user==null){
		 throw new IdOrPwdException("查无此人");
	   }
	   //3若用户输入的密码和实际密码不一致
	   if(!user.getPassword().equals(password)){
		   throw new IdOrPwdException("密码输入错误");
	   }
	   //4
	   this.user=user;//登录用户保存在属性上，供其他方法使用
	   return user;
	   }
/**
 * 开始考试的方法
 * 先进行判断，若finish为true说明考试已经结束了，那么抛出异常
 * 给控制器，告知此情况
 * 此方法需要完成的事情是：生成考题，生成考试信息(ExamInfo)
 * 
 */
	public ExamInfo start() {
		//1：生成考卷（生成考题）
	  if(finish){
		  throw new RuntimeException("考试已经结束了");
	  }
		
		
		createPaper();
		//2创建考试信息对象（ExamInfo），并完善内容
		ExamInfo examInfo=new ExamInfo();
		examInfo.setQuestionCount(paper.size());//考题总数
		examInfo.setTitle(entityContext.getTitle());//考试科目
		examInfo.setTimeLimit(entityContext.getTimeLimit());//考试时间
		examInfo.setUser(user);
		
		return examInfo;
	}
	/**
	 * 生成考卷方法
	 * 每个难度更选两题，将这些题放入属性paper集合中作为考题
	 *
	 */
	private void createPaper(){
		Random random=new Random();
		int questionIndex=0;//考题的题号
		//循环所有的难度
		for(int level=Question.LEVEL1;level<=Question.LEVEL10;level++){

			//根据当前等级，从EntityContext中获取该等级下的所有考题
			List<Question> questions =entityContext.findQuestionByLevel(level);
			
			//从集合中随即下边删除这个元素并获取到
			Question q1=questions.remove(random.nextInt(questions.size()));
			//从集合中随即下标获取这个元素（不会重复，因为上一个被删除了）
			Question q2=questions.get(random.nextInt(questions.size()));
			/**将选好的两道题转换为QuestionInfo对象，这样才能包含用户答案	
		    *然后将QuestionInfo放入paper集合中
		    *在转换QuestionInfo时要将考题编号
		    *
		    ***/
			QuestionInfo qi1=new QuestionInfo(questionIndex++,q1);
			QuestionInfo qi2=new QuestionInfo(questionIndex++,q2);
		    paper.add(qi1);
		    paper.add(qi2);
		    
		}
		
	}
/**
 * 根据题号获取对应考题
 */
	public QuestionInfo getQuestion(int questionIndex){
		return paper.get(questionIndex);
		
	}
/**
 * 根据题号保存对于的用户答案
 * 1：因为所有的考题都在paper里，所以根据题号获取考题就是从paper
 * 2：这个List中get出来对应的题
 * 3：拿到这个题后应将旧用户答案清除
 * 4：再将新的用户答案存入QuestionInfo
 * 
 */
	public void saveUserAnswers(int questionIndex, List<Integer> userAnswers) {
	
		//1根据考题取出对于的考题Question
		QuestionInfo question=paper.get(questionIndex);
		//清除旧答案

		question.getUserAnswers().clear();
		//将新答案保存起来
		question.getUserAnswers().addAll(userAnswers);
		
		
    }
/**
 * 交卷方式
 * 此方法包含几个功能，判题，出成绩
 */
public int over() {
	int score=0;
	for(QuestionInfo question:paper){
		//获取对应的考题Question
		Question q=question.getQuestion();
		//用Question中正确答案集合和用户答案集合进行equals比较
		//若返回true，说明用户做对了
		q.getAnswers().equals(question.getUserAnswers());
		if(q.getAnswers().equals(question.getUserAnswers())){
			score+=q.getScore();
		}
	
	}
	//设置考试已经结束了
	finish =true;
	return score;
}
public void  result(){
	if(!finish){
		throw new RuntimeException("您还没参加考试呢！");
	}
}


}

