package com.tarena.elts.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ����������Ϣ
 *Ӧ������ɣ�ѡ��÷֣���ȷ�𰸣������Ѷȵ���Ϣ
 */
public class Question {
/**
 * �Գ�������ʽ��������ĵȼ�
 * �������ã�
 *     ͨ����ĳ����������Ҫ����ĳ��ֵ��������ز�����
 *     ���Ǵ�ֵ�ǱȽϳ���ģ������ڶԴ˷������������⣻����Ϊ�˽��
 *     ������⣬���ǻ����˳�����������������Ϊ�˱Ƚ�����������ַ�
 *     ����ֵΪ��Ӧ��ֵ����������ʹ�÷����Ǵ�����ز���ʱ��
 *     �Ͳ���Ҫ������Щ�Ƚϳ����ʵ��ֵ�ˣ���ʮ���ݶ�Ӧ�ĳ���
 *     
 * ���磺
 *   int sex=person.getSex();//setֵΪintֵ��0���л���Ů?
 *
 *      Person ��һ����������ֵ������Ӧ�ĳ���
 *      Class Person{
 *        public static final int MAN=1;
 *        public static fianl int WOMAN=0;
 *      
 *      }
 *      if(sex==Person.MAN){
 *      System.out.println("��");
 *      }
 **      
 **/
	public static final int LEVEL1=1;
	public static final int LEVEL2=2;
	public static final int LEVEL3=3;
	public static final int LEVEL4=4;
	public static final int LEVEL5=5;
	public static final int LEVEL6=6;
	public static final int LEVEL7=7;
	public static final int LEVEL8=8;
	public static final int LEVEL9=9;
	public static final int LEVEL10=10;
  
	
	//���嵥ѡ����˫ѡ�ĳ���
	public static final int SINGLE_SELECTION=0;
	public static final int MULTI_SELECTION=1;
	
	private int id;
	private String title;
	private List<String> options=new ArrayList<String>();
    private List<Integer> answers=new ArrayList<Integer>();
    private int score;
    private int level;
    private int type;
    public Question(){
    	
    }
    public String toString(){
		StringBuffer sb=new StringBuffer(title+"\n");
		for(int i=0;i<options.size();i++){
			sb.append((char)(i+'A')+"."+options.get(i)+"\n");
			}
		sb.append("\n");
		return sb.toString();
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<Integer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}

public boolean equals(Object obj){
	if(obj==null){
		return false;
	}if(this==obj){
		return true;
	}
	if(obj instanceof Question){
		Question other=(Question)obj;
		return this.id==other.id;
	}
	return false;
}
public int hashCode(){
	return id;
}



}