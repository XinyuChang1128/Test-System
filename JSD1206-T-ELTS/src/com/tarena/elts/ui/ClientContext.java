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
 * 控制台 MAC中的C 提供界面的流程控制逻辑
 * 
 */
public class ClientContext {
	// 考试的计时器
	private Timer timer;

	// 记录当前考题，以便执行流程
	private QuestionInfo currentQuestion;

	/**
	 * 试图层
	 */
	private LoginFrame loginFrame;

	/**
	 * 视图属性的设置方法 用于让控制器认识视图
	 * 
	 * @param loginFrame
	 */
	/**
	 * 业务逻辑层
	 */
	private ExamService examService;

	private MenuFrame menuFrame;

	private ExamFrame examFrame;

	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	/**
	 * 业务逻辑属性的设置方法 用于让控制器认识业务逻辑类
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
			// 将登录用户User传入，用于在菜单窗口显示欢迎信息

			menuFrame.updateView(user);
			menuFrame.setVisible(true);

		} catch (IdOrPwdException e) {
			loginFrame.showError(e.getMessage());

			e.printStackTrace();
		} catch(NumberFormatException e){
			loginFrame.showError("请输入帐号");
	
		}	
		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void show() {
		loginFrame.setVisible(true);
	}

	/**
	 * 开始考试的流程逻辑 1：调用ExamService的start方法，生成考题和考试信息，并获取 考试信息（ExamInfo）
	 * 2；将第一道考题交给ExamFrame,用于在考试窗口显示 3：从ExamSerivce中获取第一道考题
	 * 4：将第一道考题交给ExamFrame,用于显示考题 5：将菜单窗口关闭MenuFrame 6:将考试窗口显示ExamFrame
	 */
	public void start() {
		try {
			// 1
			// examService的start方法可能会抛出异常
			// 若抛出异常，将异常信息作为弹出框的信息提示给用户

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
			// 开始考试计时
			startTimer(examInfo.getTimeLimit());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());

		}

	}

	/**
	 * 开始即使，需要传入总共倒计时的分钟数
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
		// 此任务用于在规定时间到来时，停止Timer计时器
		timer.schedule(new TimerTask() {
			public void run() {
				gameover();// 强制交卷
				timer.cancel();
			}

		}, new Date(end));
	}

	/**
	 * 下一题 1:获取当前考题的题好，并对当前题号＋1的到下一道题的题号 2:获取当前考题的用户答案，并交给ExamService去保存答案
	 * 3：通过ExamService提供的下一道题的题号获取这道考题 4：将考题交给ExamFrame
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
	 * 交卷流程： 1：询问用户是否真的交卷，使用弹出框的形式 2：若确定交卷，执行真是的交卷流程 此方法需要传入两个参数，第一个参数需要弹出的窗口
	 * 第二个参数是弹出框提示文字 返回值为点击的是确认按钮还算取消按钮
	 */
	public void send() {
		int value = JOptionPane.showConfirmDialog(examFrame, "真的交卷么？");
		if (value == JOptionPane.YES_OPTION) {
			gameover();

		}

	}

	/**
	 * 交卷的实际流程 1:将当前考题的用户答案保存 2：调用ExamService进行判题并返回得分 3：考试计时
	 * 
	 * 
	 * 4：弹个框，提示用户的得分 5：将考试窗口隐藏 6：将菜单窗口显示
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
			 * 弹出一个普通的消息框，这个框不需要用户做交互，只是通知用户一些信息
			 */
			if (timer != null) {
				timer.cancel();// 将考试计时器停止
			}

			JOptionPane.showMessageDialog(examFrame, "您的得分是：" + score);
			examFrame.setVisible(false);
			menuFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 当某个按钮准备关闭时，执行关闭流程
	 * 
	 * @param frame点击关闭窗口的按钮的那个窗口
	 *            1：弹出确认框；是否真的退出程序 2：若用户确定退出，先将当前窗口隐藏， 3：停止虚拟机
	 * 
	 */
	public void exit(JFrame frame) {
		// 1
		int value = JOptionPane.showConfirmDialog(frame, "确定离开吗？");
		// 2
		if (value == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			// 3System.exit(0)的作用是结束java进程
			System.exit(0);
		}

	}

	public void result() {
		try {
			examService.result();
			int score = examService.over();
			JOptionPane.showMessageDialog(menuFrame, "您的得分是：" + score);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());
		}

	}

	public void message(JFrame frame) {
		JOptionPane.showMessageDialog(menuFrame,
				"本次考试为机试，\n考试时间为45分钟，\n不得以任何方式作弊，\n最终解释权归达内科技所有。");
	}
}
