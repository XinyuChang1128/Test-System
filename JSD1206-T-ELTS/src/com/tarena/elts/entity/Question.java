package com.tarena.elts.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述考题信息
 *应包含题干，选项，得分，正确答案，考题难度等信息
 */
public class Question {
/**
 * 以常量的形式描述考题的等级
 * 常量作用：
 *     通常在某个方法中需要传递某个值来进行相关操作。
 *     但是此值是比较抽象的，不便于对此方法参数的理解；所以为了解决
 *     这个问题，我们会引人常量，将常量名定义为此比较容易理解的字符
 *     常量值为对应的值这样我们在使用方法是传递相关参数时，
 *     就不需要记忆那些比较抽象的实参值了，二十传递对应的常量
 *     
 * 例如：
 *   int sex=person.getSex();//set值为int值，0是男还是女?
 *
 *      Person 类一般会对这样的值给出对应的常量
 *      Class Person{
 *        public static final int MAN=1;
 *        public static fianl int WOMAN=0;
 *      
 *      }
 *      if(sex==Person.MAN){
 *      System.out.println("男");
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
  
	
	//定义单选还是双选的常量
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
