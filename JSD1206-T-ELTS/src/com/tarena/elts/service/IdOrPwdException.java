package com.tarena.elts.service;
/**
 * 此异常是自定义异常
 * 不是描述运行过程中出现的错误
 * 而是描述一个逻辑上的错误
 * 此异常描述用户输入的ID活密码错误
 *
 */
public class IdOrPwdException extends Exception{

	public IdOrPwdException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IdOrPwdException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IdOrPwdException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IdOrPwdException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
