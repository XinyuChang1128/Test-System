package com.tarena.elts.ui;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.service.ExamServiceImpl;
import com.tarena.elts.util.Config;

/**
 * �����������
 * ������������ڣ�������ĸ������(MAC)��������������ÿ�����֮��Ĺ�ϵ
 * ����������Ȼ��֪ͨ��������ʼ����
 */
public class Main {
	public static void main(String[] args) {
		/**
		 * ����V������ж���
		 */
		LoginFrame loginFrame=new LoginFrame();
		MenuFrame menuFrame=new MenuFrame();
		ExamFrame examFrame=new ExamFrame();
		/**
		 * ����C�㣨���Ʋ㣩�Ķ���
		 */
		ClientContext clientContext=new ClientContext();
		/**
		 * ����M�㣨ģ�Ͳ㣩�Ķ���
		 */
		//����ҵ���߼�
		ExamServiceImpl examService=new ExamServiceImpl();
		//�������ݷ�����
		Config config=new Config("client.properties");
		EntityContext entityContext=new EntityContext(config);
		/**
		 * ���������֮��Ĺ�ϵ
		**
		*/
		 //��ͼ������Ʋ㽨��
		loginFrame.setClientContext(clientContext);
		menuFrame.setClientContext(clientContext);
		examFrame.setClientContext(clientContext);
		//���Ʋ�����ͼ��Ľ���
		clientContext.setLoginFrame(loginFrame);
		clientContext.setMenuFrame(menuFrame);
		clientContext.setExamFrame(examFrame);
		//���Ʋ���ҵ���߼��㽨��
		clientContext.setExamService(examService);
		
		//ҵ���߼��������ݽ���
		examService.setEntityContext(entityContext);
		/**
		 * ��ʼ��������Ϻ�֪ͨ��������ʼ����
		 */
	clientContext.show();
	
	
	}

}
