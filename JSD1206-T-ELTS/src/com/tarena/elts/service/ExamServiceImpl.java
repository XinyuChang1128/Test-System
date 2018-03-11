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
 * ҵ���߼�ʵ����
 * ʵ����ҵ���߼��ӿڣ������Ӧ�ĺ��Ĺ����߼�
 */
public class ExamServiceImpl implements ExamService{

	/**�����Ե������Ǳ��濪ʼ���Ժ����ɵ����п��⣬���Ｏ����
	 * ��ŵ���Question������Question
	����Ϊ����Ҫ�����û���**/
	private List<QuestionInfo> paper =new ArrayList<QuestionInfo>();
	/**
	 * �����Ե������Ǳ��浱ǰ�ĵ�¼�û���Ϣ
	 */
	private User user;
	private int score;
	private boolean finish;

	
	
	//�ṩ���ݵ���
	private EntityContext entityContext;
	public void setEntityContext(EntityContext entityContext){
		this.entityContext=entityContext;
		/**
		 * �ṩ�����Ե�setter����
		 * ����Ҫ�ṩgetter��������Ϊ��ǰ����ֻ�ڵ�ǰ����ʹ�ã�����Ҫ�ṩ�����
		 * 
		 * �η������������õ�ǰҵ���߼�����ʶһ��Ϊ���ṩ���ݵ���
		 */
		
	}
	
	/**
    * ʵ�ֽӿڵ��û���¼�߼�
    */
	public User login(int id,String password)throws IdOrPwdException{
	  /**
	   * 1��ͨ���ṩ���ݵ���EntityContext�ṩ�������û�id��ȡ���
	   * �û�����Ϣ��Userʵ����
	   * 2���ж�EntityContext���ص� Userʵ���Ƿ���ڣ���Ϊnull��˵��
	   * �û������id�Ǵ���ģ����޴��ˣ�Ӧ��֪����̨���׳��쳣��
	   * 3����Userʵ����Ϊnull���жϸ����������Userʵ���д��˵�����
	   * �Ƿ�һ�µģ�����һ�£������������Ӧ��֪���Ʋ㣨�׳��쳣��
	   * 4������������һ�£���������û���Userʵ������ʾ��¼�ɹ�
	   * 
	   * 
	   * 
	   */
	   //1
	   User user=entityContext.findUserById(id);
	   //2
	   if(user==null){
		 throw new IdOrPwdException("���޴���");
	   }
	   //3���û�����������ʵ�����벻һ��
	   if(!user.getPassword().equals(password)){
		   throw new IdOrPwdException("�����������");
	   }
	   //4
	   this.user=user;//��¼�û������������ϣ�����������ʹ��
	   return user;
	   }
/**
 * ��ʼ���Եķ���
 * �Ƚ����жϣ���finishΪtrue˵�������Ѿ������ˣ���ô�׳��쳣
 * ������������֪�����
 * �˷�����Ҫ��ɵ������ǣ����ɿ��⣬���ɿ�����Ϣ(ExamInfo)
 * 
 */
	public ExamInfo start() {
		//1�����ɿ������ɿ��⣩
	  if(finish){
		  throw new RuntimeException("�����Ѿ�������");
	  }
		
		
		createPaper();
		//2����������Ϣ����ExamInfo��������������
		ExamInfo examInfo=new ExamInfo();
		examInfo.setQuestionCount(paper.size());//��������
		examInfo.setTitle(entityContext.getTitle());//���Կ�Ŀ
		examInfo.setTimeLimit(entityContext.getTimeLimit());//����ʱ��
		examInfo.setUser(user);
		
		return examInfo;
	}
	/**
	 * ���ɿ�����
	 * ÿ���Ѷȸ�ѡ���⣬����Щ���������paper��������Ϊ����
	 *
	 */
	private void createPaper(){
		Random random=new Random();
		int questionIndex=0;//��������
		//ѭ�����е��Ѷ�
		for(int level=Question.LEVEL1;level<=Question.LEVEL10;level++){

			//���ݵ�ǰ�ȼ�����EntityContext�л�ȡ�õȼ��µ����п���
			List<Question> questions =entityContext.findQuestionByLevel(level);
			
			//�Ӽ������漴�±�ɾ�����Ԫ�ز���ȡ��
			Question q1=questions.remove(random.nextInt(questions.size()));
			//�Ӽ������漴�±��ȡ���Ԫ�أ������ظ�����Ϊ��һ����ɾ���ˣ�
			Question q2=questions.get(random.nextInt(questions.size()));
			/**��ѡ�õ�������ת��ΪQuestionInfo�����������ܰ����û���	
		    *Ȼ��QuestionInfo����paper������
		    *��ת��QuestionInfoʱҪ��������
		    *
		    ***/
			QuestionInfo qi1=new QuestionInfo(questionIndex++,q1);
			QuestionInfo qi2=new QuestionInfo(questionIndex++,q2);
		    paper.add(qi1);
		    paper.add(qi2);
		    
		}
		
	}
/**
 * ������Ż�ȡ��Ӧ����
 */
	public QuestionInfo getQuestion(int questionIndex){
		return paper.get(questionIndex);
		
	}
/**
 * ������ű�����ڵ��û���
 * 1����Ϊ���еĿ��ⶼ��paper����Ը�����Ż�ȡ������Ǵ�paper
 * 2�����List��get������Ӧ����
 * 3���õ�������Ӧ�����û������
 * 4���ٽ��µ��û��𰸴���QuestionInfo
 * 
 */
	public void saveUserAnswers(int questionIndex, List<Integer> userAnswers) {
	
		//1���ݿ���ȡ�����ڵĿ���Question
		QuestionInfo question=paper.get(questionIndex);
		//����ɴ�

		question.getUserAnswers().clear();
		//���´𰸱�������
		question.getUserAnswers().addAll(userAnswers);
		
		
    }
/**
 * ����ʽ
 * �˷��������������ܣ����⣬���ɼ�
 */
public int over() {
	int score=0;
	for(QuestionInfo question:paper){
		//��ȡ��Ӧ�Ŀ���Question
		Question q=question.getQuestion();
		//��Question����ȷ�𰸼��Ϻ��û��𰸼��Ͻ���equals�Ƚ�
		//������true��˵���û�������
		q.getAnswers().equals(question.getUserAnswers());
		if(q.getAnswers().equals(question.getUserAnswers())){
			score+=q.getScore();
		}
	
	}
	//���ÿ����Ѿ�������
	finish =true;
	return score;
}
public void  result(){
	if(!finish){
		throw new RuntimeException("����û�μӿ����أ�");
	}
}


}

