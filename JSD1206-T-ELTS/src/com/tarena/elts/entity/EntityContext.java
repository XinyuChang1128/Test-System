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
 * 实体数据访问类
 * 此类有两个功能：
 * 1:读取并解析配置文件，将数据以实体类的实例保存
 * 2：为其他类提供数据(提供相应数据的实体类实例)
 * @author soft01
 *
 */
public class EntityContext {
	private Config config;//用于读取properties文件的类
	/**
	 * 使用HashMap保存所有的用户对象
	 * Key：用户的ID
	 * value:对应此用户的Uer对象
	 * 使用HashMap保存的好处在于，根据程序的需求进行分析后发现，当用户输入
	 * ID和密码进行登录时，我们想获取对应这个ID的用户时，使用HashMap更有利于查找此用户
	 * （省去使用List还需要自己写遍历及判断逻辑，而且HashMap可提高查找效率）
	 */
	private Map<Integer ,User> users=new HashMap<Integer,User>();
	
	private Map<Integer,List<Question>> questions=new HashMap<Integer,List<Question>>();
	/**
	 * key:考题的难易程度（level）
	 * value：对应难度的一组考题List<Question>
	 * 
	 */
	
	
	/**
	 * 构造方法作用：
	 * 将读取指定properties的Config传入，用于以后提供给其他类
	 * 读取uesr.txt文件，将文件中的每行数据转化为一个User实例
	 * ，并将这些User实例保存起来，用于以后提供给其他类
	 * 读取corejava.txt文件，将文件中的每个考题信息转化为一个Qusetion实例
	 * ，并将这些Question实例保存起来，用于以后提供给其他类
	 * @param config
	 */
	
	
	public EntityContext(Config config){
		this.config=config;
		//加载用户信息
		loadUser(config.getString("UserFile"));
		//加载考题
		loadQuestion(config.getString("QuestionFile"));
		
	}
	/**
	 * 根据给定的文件名读取并解析用户信息，并将每个User对象放入
	 * 1000:宁丽娟:1234:13810381038:ninglj@tarena.com.cn
	 * 当前属性user这个HashMap中，key为当前用户的ID
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
			//若以＃开头或空白字符串，忽略
			if(str.startsWith("#")||str.equals("")){
		      continue;		
			}
			//解析一个用户信息转换为User的对象
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
	 * 将一个字符串代表的用户信息转化为一个User实例
	 * 1000:宁丽娟:1234:13810381038:ninglj@tarena.com.cn
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
	 * 解析考题，并存放questions的HashMap中
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
			addQuestion(question);//将考题放入map中
			
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将Question对象放入Map question中
	 * 这里存在两种情况
	 *   1：根据当前考题的难易度level在mao中找到对应的 key，并获取对应的集合
	 *   ，那么直接将当前question对象放入该集合
	 *   2：根据当前考题question对象的难易度level在map中没有此key
	 *   那么应先创建一个集合，以此题的level作为key存入map中，再将此题放入该集合
	 * @param question
	 */
	private void addQuestion(Question question){
	/**
	 * 1：根据question的level从map中获取List集合
	 * 2：判断该集合是否存在，若不存在就创建集合并以question的level
	 * 作为key放入map
	 * 3:将question放入List集合中
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
	 * 解析一道考题
	 * 参数有两个，第一个参数为考题在派只文件中的第一行
	 * 第二个参数是读取该配置文件的输入流，传入目的是继续读取五行数据，
	 * 用于完成一道考题的解析
	 * @param info
	 * @param br
	 * @return
	 */
private Question parseQuestion(String info,BufferedReader br)throws IOException{
	Question question =new Question();
	//解析第一行
	//@answer=2/3,score=5,level=5
	//根据正则表达式拆分
	String data[]=info.split("[@,][a-z]+=");
	//从data的第二个元素开始取，第一个因为split拆分的无意义，忽略
	question.setAnswers(parseAnswer(data[1]));//设置正确答案
	question.setScore(Integer.parseInt(data[2]));
	question.setLevel(Integer.parseInt(data[3]));
	question.setTitle(br.readLine());//再读一行是题干（第二行）
	List<String> options=new ArrayList<String>();
	options.add(br.readLine());
	options.add(br.readLine());
	options.add(br.readLine());
	options.add(br.readLine());
	question.setOptions(options);//设置选项
	if(question.getAnswers().size()==1){
		question.setType(Question.SINGLE_SELECTION);
		
	}else{
		question.setType(Question.MULTI_SELECTION);
	}
	
	
	return question;
}	
/**
 * 将正确答案的字符串转换为List的集合
 * 正确答案的字符串格式应为0/1/2
 * @param info
 * @return
 */
	private List<Integer> parseAnswer(String info){
		/**
		 * 1:创建一个集合
		 * 2：拆分字符串
		 * 3：循环字符串数组，将每个元素放入集合中
		 * 4：将集合返回
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
	 * 根据给定的用户ID查找并返回此用户的User实例
	 * @param id
	 * @return
	 */
	public User findUserById(int id){
		return users.get(id);
		
	}
	public List<Question> findQuestionByLevel(int level){
		/**
		 * 这里不直接返回map中的某个集合
		 * 因为，一旦将map中的某个考题集合返回，那么外界若对此集合元素进行删减操作，那么考题信息就不完全了
		 * 所以，返回的集合应使用集合的复制构造器复制一份集合返回
		 */
		return new ArrayList<Question>(questions.get(level));
	}
	/**
	 * 根据配置文件返回考试时间
	 * @return
	 */
	public int getTimeLimit(){
		/**
		 * 读取client.properties的Config类
		 * 所有我们要从config中获取对应的值
		 */
	return 	config.getInt("TimeLimit");
		
	}
	//获取考试科目
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
