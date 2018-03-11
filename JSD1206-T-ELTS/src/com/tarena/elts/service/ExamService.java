package com.tarena.elts.service;

import java.util.List;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;

/**
 * ҵ���߼��ӿ�
 * ��������������ĺ��Ĺ���
 * 
 */
public interface ExamService {
    //��¼����
	public User login(int id,String password)throws IdOrPwdException;
		//��ʼ���Է������������ɿ����Լ�������Ϣ
	public ExamInfo start();
	
	public QuestionInfo getQuestion(int questionIndex);

	//������ű�����ڵ��û���
	public void saveUserAnswers(int questionIndex,List<Integer> userAnswers);
	//�������ԣ������з֣����ɼ�
	public int over();
	public void result();
	
	
}
