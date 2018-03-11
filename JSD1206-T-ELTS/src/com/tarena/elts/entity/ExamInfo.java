package com.tarena.elts.entity;

import java.io.Serializable;

public class ExamInfo implements Serializable{
	private String title;
	private User user;
	private int timeLimit;
	private int questionCount;
	/**
	 * 考试的所有考题的集合，思想，放在这里好不好
	 *private ArrayList<Question> allQuestion;
	 */
	public ExamInfo(){
		
	}
	public ExamInfo(String title,User user,int timeLimit,int questionCount){
		super();
		this.title=title;
		this.user=user;
		this.timeLimit=timeLimit;
		this.questionCount=questionCount;
	}
	public String toString(){
		if(user==null)
			return "无信息";
	        return "姓名:"+user.getName()+"编号:"+
	        user.getId()+"考试时间:"+
	                timeLimit+"分钟:"+"考试科目:"+title+
	                "题目数量:"+questionCount;
	        
		
	}
	public int getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
