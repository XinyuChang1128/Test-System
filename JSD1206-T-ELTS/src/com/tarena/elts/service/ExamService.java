package com.tarena.elts.service;

import java.util.List;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;

/**
 * 业务逻辑接口
 * 定义了所有软件的核心功能
 * 
 */
public interface ExamService {
    //登录方法
	public User login(int id,String password)throws IdOrPwdException;
		//开始考试方法，用于生成考试以及考试信息
	public ExamInfo start();
	
	public QuestionInfo getQuestion(int questionIndex);

	//根据题号保存对于的用户答案
	public void saveUserAnswers(int questionIndex,List<Integer> userAnswers);
	//结束考试，交卷，判分，出成绩
	public int over();
	public void result();
	
	
}
