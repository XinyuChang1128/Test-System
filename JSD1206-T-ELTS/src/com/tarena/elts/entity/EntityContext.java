package com.tarena.elts.entity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tarena.elts.util.Config;

/**
 * ʵ�����ݷ�����
 * �������������ܣ�
 * 1:��ȡ�����������ļ�����������ʵ�����ʵ������
 * 2��Ϊ�������ṩ����(�ṩ��Ӧ���ݵ�ʵ����ʵ��)
 * @author soft01
 *
 */
public class EntityContext {
	private Config config;//���ڶ�ȡproperties�ļ�����
	/**
	 * ʹ��HashMap�������е��û�����
	 * Key���û���ID
	 * value:��Ӧ���û���Uer����
	 * ʹ��HashMap����ĺô����ڣ����ݳ����������з������֣����û�����
	 * ID��������е�¼ʱ���������ȡ��Ӧ���ID���û�ʱ��ʹ��HashMap�������ڲ��Ҵ��û�
	 * ��ʡȥʹ��List����Ҫ�Լ�д�������ж��߼�������HashMap����߲���Ч�ʣ�
	 */
	private Map<Integer ,User> users=new HashMap<Integer,User>();
	
	private Map<Integer,List<Question>> questions=new HashMap<Integer,List<Question>>();
	/**
	 * key:��������׳̶ȣ�level��
	 * value����Ӧ�Ѷȵ�һ�鿼��List<Question>
	 * 
	 */
	
	
	/**
	 * ���췽�����ã�
	 * ����ȡָ��properties��Config���룬�����Ժ��ṩ��������
	 * ��ȡuesr.txt�ļ������ļ��е�ÿ������ת��Ϊһ��Userʵ��
	 * ��������ЩUserʵ�����������������Ժ��ṩ��������
	 * ��ȡcorejava.txt�ļ������ļ��е�ÿ��������Ϣת��Ϊһ��Qusetionʵ��
	 * ��������ЩQuestionʵ�����������������Ժ��ṩ��������
	 * @param config
	 */
	
	
	public EntityContext(Config config){
		this.config=config;
		//�����û���Ϣ
		loadUser(config.getString("UserFile"));
		//���ؿ���
		loadQuestion(config.getString("QuestionFile"));
		
	}
	/**
	 * ���ݸ������ļ�����ȡ�������û���Ϣ������ÿ��User�������
	 * 1000:������:1234:13810381038:ninglj@tarena.com.cn
	 * ��ǰ����user���HashMap�У�keyΪ��ǰ�û���ID
	 * @param filename
	 */
	private void loadUser(String filename){
		try{
			BufferedReader br=new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filename)));
		String str;
		
		while((str=br.readLine())!=null){
			str=str.trim();
			//���ԣ���ͷ��հ��ַ���������
			if(str.startsWith("#")||str.equals("")){
		      continue;		
			}
			//����һ���û���Ϣת��ΪUser�Ķ���
			User user=parseUser(str);
			users.put(user.getId(),user);
     		}
		br.close();
		}catch(Exception e){
		e.printStackTrace();
		throw new RuntimeException(e);
		}
	
	}
	/**
	 * ��һ���ַ���������û���Ϣת��Ϊһ��Userʵ��
	 * 1000:������:1234:13810381038:ninglj@tarena.com.cn
	 * @param info
	 * @return
	 */
	private User parseUser(String info){
		User user=new User();
		String data[]=info.split(":");
		user.setId(Integer.parseInt(data[0]));
		user.setName(data[1]);
		user.setPassword(data[2]);
		user.setPhone(data[3]);
		user.setEmail(data[4]);
		
		
		return user;
	}
	/**
	 * �������⣬�����questions��HashMap��
	 * @param filename
	 */
	private void loadQuestion(String filename){
		try{
			BufferedReader br=new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filename)));
			String str;
			while((str=br.readLine())!=null){
			str=str.trim();
			if(str.startsWith("#")||str.equals("")){
				continue;
			}
			Question question=parseQuestion(str,br);
			addQuestion(question);//���������map��
			
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��Question�������Map question��
	 * ��������������
	 *   1�����ݵ�ǰ��������׶�level��mao���ҵ���Ӧ�� key������ȡ��Ӧ�ļ���
	 *   ����ôֱ�ӽ���ǰquestion�������ü���
	 *   2�����ݵ�ǰ����question��������׶�level��map��û�д�key
	 *   ��ôӦ�ȴ���һ�����ϣ��Դ����level��Ϊkey����map�У��ٽ��������ü���
	 * @param question
	 */
	private void addQuestion(Question question){
	/**
	 * 1������question��level��map�л�ȡList����
	 * 2���жϸü����Ƿ���ڣ��������ھʹ������ϲ���question��level
	 * ��Ϊkey����map
	 * 3:��question����List������
	 */
	//1
	List<Question> list=questions.get(question.getLevel());
	if(list==null){
		list=new ArrayList<Question>();
		questions.put(question.getLevel(),list);
	}
	list.add(question);
	}
	
	/**
	 * ����һ������
	 * ��������������һ������Ϊ��������ֻ�ļ��еĵ�һ��
	 * �ڶ��������Ƕ�ȡ�������ļ���������������Ŀ���Ǽ�����ȡ�������ݣ�
	 * �������һ������Ľ���
	 * @param info
	 * @param br
	 * @return
	 */
private Question parseQuestion(String info,BufferedReader br)throws IOException{
	Question question =new Question();
	//������һ��
	//@answer=2/3,score=5,level=5
	//����������ʽ���
	String data[]=info.split("[@,][a-z]+=");
	//��data�ĵڶ���Ԫ�ؿ�ʼȡ����һ����Ϊsplit��ֵ������壬����
	question.setAnswers(parseAnswer(data[1]));//������ȷ��
	question.setScore(Integer.parseInt(data[2]));
	question.setLevel(Integer.parseInt(data[3]));
	question.setTitle(br.readLine());//�ٶ�һ������ɣ��ڶ��У�
	List<String> options=new ArrayList<String>();
	options.add(br.readLine());
	options.add(br.readLine());
	options.add(br.readLine());
	options.add(br.readLine());
	question.setOptions(options);//����ѡ��
	if(question.getAnswers().size()==1){
		question.setType(Question.SINGLE_SELECTION);
		
	}else{
		question.setType(Question.MULTI_SELECTION);
	}
	
	
	return question;
}	
/**
 * ����ȷ�𰸵��ַ���ת��ΪList�ļ���
 * ��ȷ�𰸵��ַ�����ʽӦΪ0/1/2
 * @param info
 * @return
 */
	private List<Integer> parseAnswer(String info){
		/**
		 * 1:����һ������
		 * 2������ַ���
		 * 3��ѭ���ַ������飬��ÿ��Ԫ�ط��뼯����
		 * 4�������Ϸ���
		 */
		//1
		List<Integer> answers=new ArrayList<Integer>();
	    //2
		String []data=info.split("/");
		for(String str:data){
			answers.add(Integer.parseInt(str));
		}
		return answers;
	
	}
	/**
	 * ���ݸ������û�ID���Ҳ����ش��û���Userʵ��
	 * @param id
	 * @return
	 */
	public User findUserById(int id){
		return users.get(id);
		
	}
	public List<Question> findQuestionByLevel(int level){
		/**
		 * ���ﲻֱ�ӷ���map�е�ĳ������
		 * ��Ϊ��һ����map�е�ĳ�����⼯�Ϸ��أ���ô������Դ˼���Ԫ�ؽ���ɾ����������ô������Ϣ�Ͳ���ȫ��
		 * ���ԣ����صļ���Ӧʹ�ü��ϵĸ��ƹ���������һ�ݼ��Ϸ���
		 */
		return new ArrayList<Question>(questions.get(level));
	}
	/**
	 * ���������ļ����ؿ���ʱ��
	 * @return
	 */
	public int getTimeLimit(){
		/**
		 * ��ȡclient.properties��Config��
		 * ��������Ҫ��config�л�ȡ��Ӧ��ֵ
		 */
	return 	config.getInt("TimeLimit");
		
	}
	//��ȡ���Կ�Ŀ
	public String getTitle(){
		return config.getString("PaperTitle");
	}
	
	public static void main(String[] args) {
	Config config=new Config("client.properties");
	EntityContext entityContext=new EntityContext(config);
	System.out.println(entityContext.users);
	System.out.println(entityContext.questions);
	
}



}
