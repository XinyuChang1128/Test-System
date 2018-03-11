package com.tarena.elts.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionInfo implements Serializable {
	private Question question;

	private int questionIndex;

	private List<Integer> userAnswers = new ArrayList<Integer>();
    public QuestionInfo(){
    	
    }

    public QuestionInfo(int questionIndex,Question question){
    	super();
    	this.question=question;
    	this.userAnswers=userAnswers;
    	this.questionIndex=questionIndex;
    }

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public List<Integer> getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(List<Integer> userAnswers) {
		this.userAnswers = userAnswers;
	}

  public String toString(){
	  return question.toString();
  }

}
