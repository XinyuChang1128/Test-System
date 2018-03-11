package com.tarena.elts.ui;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.service.ExamServiceImpl;
import com.tarena.elts.util.Config;

/**
 * 程序的启动类
 * 该类的作用在于，将程序的各个层次(MAC)创建出来，并将每个层次之间的关系
 * 建立起来，然后通知控制器开始程序
 */
public class Main {
	public static void main(String[] args) {
		/**
		 * 创建V层的所有对象
		 */
		LoginFrame loginFrame=new LoginFrame();
		MenuFrame menuFrame=new MenuFrame();
		ExamFrame examFrame=new ExamFrame();
		/**
		 * 创建C层（控制层）的对象
		 */
		ClientContext clientContext=new ClientContext();
		/**
		 * 创建M层（模型层）的对象
		 */
		//创建业务逻辑
		ExamServiceImpl examService=new ExamServiceImpl();
		//创建数据访问类
		Config config=new Config("client.properties");
		EntityContext entityContext=new EntityContext(config);
		/**
		 * 建立各层次之间的关系
		**
		*/
		 //视图层与控制层建立
		loginFrame.setClientContext(clientContext);
		menuFrame.setClientContext(clientContext);
		examFrame.setClientContext(clientContext);
		//控制层与试图层的建立
		clientContext.setLoginFrame(loginFrame);
		clientContext.setMenuFrame(menuFrame);
		clientContext.setExamFrame(examFrame);
		//控制层于业务逻辑层建立
		clientContext.setExamService(examService);
		
		//业务逻辑层与数据建立
		examService.setEntityContext(entityContext);
		/**
		 * 初始化工作完毕后，通知控制器开始程序
		 */
	clientContext.show();
	
	
	}

}
