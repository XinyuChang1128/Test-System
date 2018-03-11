package com.tarena.elts.entity;

/**
 * �û�ʵ�� �����������һ��������������ĳһ���û�����Ϣ
 * 
 */
public class User {
	private int id;// �û�id

	private String name;// �û���ʵ����

	private String password;// �û�����

	private String phone;// �û��绰

	private String email;// ��������

	public User() {

	}

	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public String toString() {
		return name;// ��ӡ���������˶��������û�����ʵ����
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if(obj==this){
			return true;
		}
		if(obj instanceof User){
			User u=(User)obj;
			return this.id==u.id;//�Ƚ����ݣ���IDһ��������������һ��
		}
		return false;
	}
	public int hashCode(){
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
