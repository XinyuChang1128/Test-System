package com.tarena.elts.entity;

/**
 * 用户实体 此类的作用是一个对象用于描述某一个用户的信息
 * 
 */
public class User {
	private int id;// 用户id

	private String name;// 用户真实姓名

	private String password;// 用户密码

	private String phone;// 用户电话

	private String email;// 电子邮箱

	public User() {

	}

	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public String toString() {
		return name;// 打印对象就输出此对象代表的用户的真实姓名
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
			return this.id==u.id;//比较内容，若ID一样，两对象内容一致
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
