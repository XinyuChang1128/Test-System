package com.tarena.elts.ui;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;
import com.tarena.elts.service.ExamService;
import com.tarena.elts.service.IdOrPwdException;

/**
 * ����̨ MAC�е�C �ṩ��������̿����߼�
 * 
 */
public class ClientContext {
	// ���Եļ�ʱ��
	private Timer timer;

	// ��¼��ǰ���⣬�Ա�ִ������
	private QuestionInfo currentQuestion;

	/**
	 * ��ͼ��
	 */
	private LoginFrame loginFrame;

	/**
	 * ��ͼ���Ե����÷��� �����ÿ�������ʶ��ͼ
	 * 
	 * @param loginFrame
	 */
	/**
	 * ҵ���߼���
	 */
	private ExamService examService;

	private MenuFrame menuFrame;

	private ExamFrame examFrame;

	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	/**
	 * ҵ���߼����Ե����÷��� �����ÿ�������ʶҵ���߼���
	 */

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public void setMenuFrame(MenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	public void setExamFrame(ExamFrame examFrame) {
		this.examFrame = examFrame;
	}

	public void login() {
		try {
			int id = loginFrame.getId();
			String password = loginFrame.getPassword();
			User user = examService.login(id, password);
			loginFrame.setVisible(false);
			// ����¼�û�User���룬�����ڲ˵�������ʾ��ӭ��Ϣ

			menuFrame.updateView(user);
			menuFrame.setVisible(true);

		} catch (IdOrPwdException e) {
			loginFrame.showError(e.getMessage());

			e.printStackTrace();
		} catch(NumberFormatException e){
			loginFrame.showError("�������ʺ�");
	
		}	
		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void show() {
		loginFrame.setVisible(true);
	}

	/**
	 * ��ʼ���Ե������߼� 1������ExamService��start���������ɿ���Ϳ�����Ϣ������ȡ ������Ϣ��ExamInfo��
	 * 2������һ�����⽻��ExamFrame,�����ڿ��Դ�����ʾ 3����ExamSerivce�л�ȡ��һ������
	 * 4������һ�����⽻��ExamFrame,������ʾ���� 5�����˵����ڹر�MenuFrame 6:�����Դ�����ʾExamFrame
	 */
	public void start() {
		try {
			// 1
			// examService��start�������ܻ��׳��쳣
			// ���׳��쳣�����쳣��Ϣ��Ϊ���������Ϣ��ʾ���û�

			ExamInfo examInfo = examService.start();

			// 2
			examFrame.updateExamInfo(examInfo);
			// 3
			QuestionInfo question = examService.getQuestion(0);
			currentQuestion = question;
			// 4
			examFrame.updateQuestion(question);
			// 5
			menuFrame.setVisible(false);

			// 6
			examFrame.setVisible(true);
			// ��ʼ���Լ�ʱ
			startTimer(examInfo.getTimeLimit());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());

		}

	}

	/**
	 * ��ʼ��ʹ����Ҫ�����ܹ�����ʱ�ķ�����
	 * 
	 * @param timeLimit
	 */
	private void startTimer(int timeLimit) {
		final long end = System.currentTimeMillis() + timeLimit * 60 * 1000;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				long show = end - System.currentTimeMillis();
				long h = show / 1000 / 60 / 60;
				long m = show / 1000 / 60 % 60;
				long s = show / 1000 % 60;
				examFrame.updateTimer(h, m, s);
			}
		}, 0, 1000);
		// �����������ڹ涨ʱ�䵽��ʱ��ֹͣTimer��ʱ��
		timer.schedule(new TimerTask() {
			public void run() {
				gameover();// ǿ�ƽ���
				timer.cancel();
			}

		}, new Date(end));
	}

	/**
	 * ��һ�� 1:��ȡ��ǰ�������ã����Ե�ǰ��ţ�1�ĵ���һ�������� 2:��ȡ��ǰ������û��𰸣�������ExamServiceȥ�����
	 * 3��ͨ��ExamService�ṩ����һ�������Ż�ȡ������� 4�������⽻��ExamFrame
	 */

	public void prev() {
		try {
			// 1
			int index = currentQuestion.getQuestionIndex();

			// 2

			List<Integer> userAnswers = examFrame.getUserAnswers();

			examService.saveUserAnswers(currentQuestion.getQuestionIndex(),
					userAnswers);

			// 3
			index--;
			QuestionInfo question = examService.getQuestion(index);
			currentQuestion = question;
			// 4
			examFrame.updateQuestion(question);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void next() {
		try {
			// 1
			int index = currentQuestion.getQuestionIndex();

			// 2
			List<Integer> userAnswers = examFrame.getUserAnswers();

			examService.saveUserAnswers(currentQuestion.getQuestionIndex(),
					userAnswers);
			// 3
			index++;
			QuestionInfo question = examService.getQuestion(index);
			currentQuestion = question;
			// 4
			examFrame.updateQuestion(question);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������̣� 1��ѯ���û��Ƿ���Ľ���ʹ�õ��������ʽ 2����ȷ������ִ�����ǵĽ������� �˷�����Ҫ����������������һ��������Ҫ�����Ĵ���
	 * �ڶ��������ǵ�������ʾ���� ����ֵΪ�������ȷ�ϰ�ť����ȡ����ť
	 */
	public void send() {
		int value = JOptionPane.showConfirmDialog(examFrame, "��Ľ���ô��");
		if (value == JOptionPane.YES_OPTION) {
			gameover();

		}

	}

	/**
	 * �����ʵ������ 1:����ǰ������û��𰸱��� 2������ExamService�������Ⲣ���ص÷� 3�����Լ�ʱ
	 * 
	 * 
	 * 4����������ʾ�û��ĵ÷� 5�������Դ������� 6�����˵�������ʾ
	 * 
	 * 
	 */
	private void gameover() {
		try {
			int questionIndex = currentQuestion.getQuestionIndex();
			List<Integer> userAnswers = examFrame.getUserAnswers();
			examService.saveUserAnswers(questionIndex, userAnswers);
			int score = examService.over();
			/**
			 * ����һ����ͨ����Ϣ���������Ҫ�û���������ֻ��֪ͨ�û�һЩ��Ϣ
			 */
			if (timer != null) {
				timer.cancel();// �����Լ�ʱ��ֹͣ
			}

			JOptionPane.showMessageDialog(examFrame, "���ĵ÷��ǣ�" + score);
			examFrame.setVisible(false);
			menuFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ĳ����ť׼���ر�ʱ��ִ�йر�����
	 * 
	 * @param frame����رմ��ڵİ�ť���Ǹ�����
	 *            1������ȷ�Ͽ��Ƿ�����˳����� 2�����û�ȷ���˳����Ƚ���ǰ�������أ� 3��ֹͣ�����
	 * 
	 */
	public void exit(JFrame frame) {
		// 1
		int value = JOptionPane.showConfirmDialog(frame, "ȷ���뿪��");
		// 2
		if (value == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			// 3System.exit(0)�������ǽ���java����
			System.exit(0);
		}

	}

	public void result() {
		try {
			examService.result();
			int score = examService.over();
			JOptionPane.showMessageDialog(menuFrame, "���ĵ÷��ǣ�" + score);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());
		}

	}

	public void message(JFrame frame) {
		JOptionPane.showMessageDialog(menuFrame,
				"���ο���Ϊ���ԣ�\n����ʱ��Ϊ45���ӣ�\n�������κη�ʽ���ף�\n���ս���Ȩ����ڿƼ����С�");
	}
}
